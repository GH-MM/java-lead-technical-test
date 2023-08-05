/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package anon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Sequence;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.util.TagUtils;
import org.dcm4che3.util.UIDUtils;

import anon.AnonymisationProfile.AnonRule;
import datasearch.DicomSearchTool;
import datasearch.FileProcessor;
import dicom.DicomUtil;

/**
 * A {@link FileProcessor} implementation that handles 
 * anonymisation of DICOM file based on a specified 
 * {@link AnonymisationProfile}
 * 
 * @author Dez Wright
 *
 */
public class DicomAnonymiser implements FileProcessor {
	
	private static final Logger LOGGER = LogManager.getLogger(DicomAnonymiser.class);
	
	private final AnonymisationProfile anonymisationProfile;
	private final String outputFolder;
	private final Logger anonLogger;
	
	private final Map<String, String> reuids = new HashMap<>();
	
	public DicomAnonymiser(AnonymisationProfile profile, String outputFolder, Logger anonLogger) {
		anonymisationProfile = profile;
		this.outputFolder = outputFolder;
		initialiseOutputLocation(outputFolder);
		this.anonLogger = anonLogger;
	}
	
	private void appendLog(String entry) {
		if (anonLogger != null) {
			anonLogger.log(Level.INFO, entry);
		} else {
			LOGGER.log(Level.INFO, entry);
		}
	}
	
	private void appendLog(String entry, Throwable e) {
		if (anonLogger != null) {
			anonLogger.log(Level.ERROR, entry, e);
		} else {
			LOGGER.log(Level.ERROR, entry, e);
		}
	}
	
	private boolean initialiseOutputLocation(String folder) {
		Path directoryPath = Paths.get(folder);
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
                return true;
            } catch (IOException e) {
            	LOGGER.error(String.format("Failed to initialise output directory : %s", folder), e); //$NON-NLS-1$
            	return false;
            }
        }
        return true;
	}

	@Override
	public void processFile(String filePath) {
		try {
			anonymiseFile(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected final List<Attributes> anonymiseFile(String filePath) throws IOException {
		appendLog("Anonymise input file : " + filePath); //$NON-NLS-1$
		List<Attributes> attribs = readDicomFile(filePath);
		Attributes meta = null;
		Attributes attr = null;
		if (attribs.size() > 1) {
			meta = attribs.get(0);
			attr = attribs.get(1);
		} else {
			attr = attribs.get(0);
		}
		String oStudyuid = attr.getString(Tag.StudyInstanceUID, "");
		String oInstanceuid = attr.getString(Tag.SOPInstanceUID, "");
		appendLog("Anonymise study UID : " + oStudyuid); //$NON-NLS-1$
		appendLog("Anonymise instance UID : " + oInstanceuid); //$NON-NLS-1$
		anonymiseAttributes(attr);
		storeAnonymisedFile(meta, attr);
		return attribs;
	}
	
	protected final List<Attributes> readDicomFile(String filePath) throws IOException {
		List<Attributes> attribs = new ArrayList<>();
		File dicomFile = new File(filePath);
        try (DicomInputStream dis = new DicomInputStream(dicomFile)) {
        	dis.setIncludeBulkData(IncludeBulkData.URI);
        	Attributes fmi = dis.readFileMetaInformation();
        	if (fmi != null) attribs.add(fmi);
        	attribs.add(dis.readDataset());
        }
        return attribs;
	}
	
	private final void anonymiseAttributes(Attributes attrs) {
		for (int tag : attrs.tags()) {
			anonymiseTag(attrs, tag);
			setTag(attrs, tag);
		}
		setAnonInfo(attrs);
	}
	
	private final void anonymiseTag(Attributes attrs, int tag) {
		// TODO Need to extend implementation to allow specified private tags to be kept.
		if (TagUtils.isPrivateGroup(tag) && anonymisationProfile.removePrivateTags()) {
			removeTag(attrs, tag);
		} else {
			Map<Integer, AnonymisationProfile.AnonRule> rules = anonymisationProfile.getTagRules();
			AnonRule rule = rules.getOrDefault(tag, AnonRule.KEEP);
			switch (rule) {
			case REPLACE_ZERO: emptyTag(attrs, tag); break;
			case REMOVE: removeTag(attrs, tag); break;
			case REPLACE_NON_ZERO: replaceTag(attrs, tag); break;
			case REUID: reUid(attrs, tag); break;
			case KEEP:
			case CLEAN: 
			default: if(isSequence(tag)) {anonymiseSequence(attrs, tag);} break;
			}
		}
	}
	
	private final boolean isSequence(int tag) {
		ElementDictionary dict = ElementDictionary.getStandardElementDictionary();
		return VR.SQ.equals(dict.vrOf(tag));
	}
	
	private final void anonymiseSequence(Attributes attrs, int tag) {
		Sequence sequence = attrs.getSequence(tag);
		for (Attributes item : sequence) {
            anonymiseAttributes(item);
        }
	}
	
	private final void setTag(Attributes attrs, int tag) {
		ElementDictionary dict = ElementDictionary.getStandardElementDictionary();
		Object replacement = anonymisationProfile.getTagReplacement(tag, null);
		if (replacement != null) {
			VR vr = dict.vrOf(tag);
			attrs.setString(tag, vr, (String) replacement);
		}
	}
	
	private final void setAnonInfo(Attributes attrs) {
		ElementDictionary dict = ElementDictionary.getStandardElementDictionary();
		for (Entry<Integer, String> entry : anonymisationProfile.getAnonTagInfo().entrySet()) {
			Integer tag = entry.getKey();
	        VR vr = dict.vrOf(tag);
			attrs.setString(tag, vr, entry.getValue());
		}
	}
	
	private final void reUid(Attributes attrs, Integer tag) {
		String uid = getUid(attrs.getString(tag));
		VR vr = attrs.getVR(tag);
		attrs.setBytes(tag, vr, uid.getBytes());
	}

	private final String getUid(String currentUid) {
		// TODO this should use a persistent UID record like a DB.
		return reuids.computeIfAbsent(currentUid, k -> UIDUtils.createUID(DicomUtil.MIRADA_MEDICAL_UID_ROOT));
	}
	
	private final void emptyTag(Attributes attrs, Integer tag) {
		VR vr = attrs.getVR(tag);
		attrs.setBytes(tag, vr, new byte[0]);
	}
	
	private final void removeTag(Attributes attrs, Integer tag) {
		try {
			VR vr = attrs.getVR(tag);
			attrs.setNull(tag, vr);
		} catch (Exception e) {
			LOGGER.error(String.format("Error removing tag %s", tag ), e);
		}
	}
	
	private final void replaceTag(Attributes attrs, Integer tag) {
		Map<Integer, Object> replacements = anonymisationProfile.getTagReplacements();
		VR vr = attrs.getVR(tag);
		if (vr.isIntType()) {
			Object replacement = replacements.getOrDefault(tag, 0);
			setInteger(attrs, tag, vr, replacement);
		} else if (vr.isStringType()) {
			Object replacement = replacements.getOrDefault(tag, "");
			setString(attrs, tag, vr, replacement.toString());
		}
	}
	
	private final void setInteger(Attributes attrs, Integer tag, VR vr, Object replacement) {
		int val = 0;
		if (replacement instanceof Integer) {
			val = (Integer)replacement;
		} else {
			try {
				val = Integer.parseInt(replacement.toString());
			} catch (NumberFormatException e) {
				val = 0;
			}
		}
		attrs.setInt(tag, vr, val);
	}
	
	private final void setString(Attributes attrs, Integer tag, VR vr, Object replacement) {
		String val = "";
		if (replacement instanceof String) {
			val = (String)replacement;
		} else if (replacement instanceof Integer) {
			val = "" + replacement;
		} else {
			val = replacement.toString();
		}
		attrs.setString(tag, vr, val);
	}
	
	private final void storeAnonymisedFile(Attributes meta, Attributes attr) {
		String nStudyuid = attr.getString(Tag.StudyInstanceUID, "");
		String nInstanceuid = attr.getString(Tag.SOPInstanceUID, "");
		String studyOutputRoot = outputFolder + File.separator + nStudyuid;
		String outputFile = studyOutputRoot + File.separator + nInstanceuid + ".dcm";
		appendLog("Anonymisation output file : " + outputFile); //$NON-NLS-1$
		if (initialiseOutputLocation(studyOutputRoot)) {
			writeNewDicomFile(meta, attr, outputFile);
		}
	}
	
	private final void writeNewDicomFile(Attributes metaData, Attributes attrs, String newPath) {
	    try (DicomOutputStream dos = new DicomOutputStream(new File(newPath))) {
	    	if (metaData != null) {
	    		metaData = attrs.createFileMetaInformation(metaData.getString(Tag.TransferSyntaxUID));
			}
	    	dos.writeFileMetaInformation(metaData);
	        dos.writeDataset(null, attrs);
	    } catch (IOException e) {
			appendLog("Error saving anonymised file : " + newPath, e); //$NON-NLS-1$
		}
	}
	
	public static void main(String[] args) {
		
		String outputFileKey = "--output-root";
		String outputFolder = null;
		if (args.length != 2) {
			printUsage();
			return;
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(outputFileKey)) {
				outputFolder = args[++i];
			} else {
				printUsage();
				return;
			}
		}
	
		AnonymisationProfile profile = new CustomerAnonymisationProfile();
		FileProcessor processor = new DicomAnonymiser(profile, outputFolder, null); 
		DicomSearchTool searchTool = new DicomSearchTool(processor);
		searchTool.search();
		
	}
	
	private static void printUsage() {
		LOGGER.error("Usage : --output-root {$output_folder}");
	}

}
