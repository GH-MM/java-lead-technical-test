/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package anon;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.junit.AfterClass;
import org.junit.Test;

public class DicomAnonymiserTest {

	private static final String OUTPUT_FOLDER = "src/test/resources/anon/results";
	private static final String SOURCE = "src/test/resources/anon/IM1.dcm";
	
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		clean();
	}
	
	private static final void clean() {
		try {
            Path directory = Paths.get(OUTPUT_FOLDER);
            Files.walk(directory)
                .sorted(Comparator.reverseOrder())  // reverse order to start from deepest files
                .map(Path::toFile)
                .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Test
	public void testInit() {
		AnonymisationProfile profile = new CustomerAnonymisationProfile();
		new DicomAnonymiser(profile, OUTPUT_FOLDER, null);
		File results = new File(OUTPUT_FOLDER);
		assertTrue(results.exists());
		clean();
		assertFalse(results.exists());
	}
	
	@Test
	public void testReadDicomFile() throws IOException {
		AnonymisationProfile profile = new CustomerAnonymisationProfile();
		DicomAnonymiser anonTool = new DicomAnonymiser(profile, OUTPUT_FOLDER, null);
		
		List<Attributes> attribs = anonTool.readDicomFile(SOURCE);
		assertEquals(2, attribs.size());
		Attributes attr = attribs.get(1);
		String expected = "Synthetic";
		assertEquals(expected, attr.getString(Tag.PatientName));
		expected = "Y90_Quantification_Tests";
		assertEquals(expected, attr.getString(Tag.PatientID));
	}
	
	@Test
	public void testAnonymiseDicomFile() throws IOException {
		AnonymisationProfile profile = new CustomerAnonymisationProfile();
		DicomAnonymiser anonTool = new DicomAnonymiser(profile, OUTPUT_FOLDER, null);
		List<Attributes> attribs = anonTool.anonymiseFile(SOURCE);
		assertEquals(2, attribs.size());
		Attributes attr = attribs.get(1);
		String expected = "Anonymised";
		assertEquals(expected, attr.getString(Tag.PatientName));
		expected = "";
		assertNull(attr.getString(Tag.PatientID));
		
	}

}
