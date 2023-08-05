/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package anon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.dcm4che3.data.Tag;

public class DefaultAnonymisationProfile implements AnonymisationProfile {
	
	private static final Map<Integer, AnonRule> TAG_RULES = new HashMap<>();
	static {
		TAG_RULES.put(Tag.AccessionNumber, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.AcquisitionComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AcquisitionContextSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AcquisitionDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AcquisitionDateTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AcquisitionDeviceProcessingDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AcquisitionProtocolDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AcquisitionTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ActualHumanPerformersSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AdditionalPatientHistory, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AdmissionID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AdmittingDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AdmittingDiagnosesCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AdmittingDiagnosesDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AdmittingTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AffectedSOPInstanceUID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.Allergies, AnonRule.REMOVE);
		TAG_RULES.put(Tag.Arbitrary, AnonRule.REMOVE);
		TAG_RULES.put(Tag.AuthorObserverSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.BranchOfService, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CommentsOnThePerformedProcedureStep, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ConcatenationUID, AnonRule.REUID);
		TAG_RULES.put(Tag.ConfidentialityConstraintOnPatientDataDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.Arbitrary, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ContentCreatorName, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.ContentCreatorIdentificationCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ContentDate, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.ContentSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ContextGroupExtensionCreatorUID, AnonRule.REUID);
		TAG_RULES.put(Tag.ContrastBolusAgent, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.ContributionDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CountryOfResidence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CreatorVersionUID, AnonRule.REUID);
		TAG_RULES.put(Tag.CurrentPatientLocation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CurveData, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CurveDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CurveTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.CustodialOrganizationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DataSetTrailingPadding, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DerivationDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DetectorID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DeviceSerialNumber, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DeviceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.DigitalSignatureUID, AnonRule.REUID);
		TAG_RULES.put(Tag.DigitalSignaturesSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DimensionOrganizationUID, AnonRule.REUID);
		TAG_RULES.put(Tag.DischargeDiagnosisDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DistributionAddress, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DistributionName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.DoseReferenceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.EthnicGroup, AnonRule.REMOVE);
		TAG_RULES.put(Tag.FailedSOPInstanceUIDList, AnonRule.REUID);
		TAG_RULES.put(Tag.FiducialUID, AnonRule.REUID);
		TAG_RULES.put(Tag.FillerOrderNumberImagingServiceRequest, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.FrameComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.FrameOfReferenceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.GantryID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.GeneratorID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.GraphicAnnotationSequence, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.HumanPerformerName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.HumanPerformerOrganization, AnonRule.REMOVE);
		TAG_RULES.put(Tag.IconImageSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.IdentifyingComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ImageComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ImagePresentationComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ImagingServiceRequestComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.Impressions, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InstanceCreatorUID, AnonRule.REUID);
		TAG_RULES.put(Tag.InstitutionAddress, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InstitutionCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InstitutionName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InstitutionalDepartmentName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InsurancePlanIdentification, AnonRule.REMOVE);
		TAG_RULES.put(Tag.IntendedRecipientsOfResultsIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationApproverSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationAuthor, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationDiagnosisCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationIDIssuer, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationRecorder, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationText, AnonRule.REMOVE);
		TAG_RULES.put(Tag.InterpretationTranscriber, AnonRule.REMOVE);
		TAG_RULES.put(Tag.IrradiationEventUID, AnonRule.REUID);
		TAG_RULES.put(Tag.IssuerOfAdmissionID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.IssuerOfPatientID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.IssuerOfServiceEpisodeID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.LargePaletteColorLookupTableUID, AnonRule.REUID);
		TAG_RULES.put(Tag.LastMenstrualDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.MAC, AnonRule.REMOVE);
		TAG_RULES.put(Tag.MediaStorageSOPInstanceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.MedicalAlerts, AnonRule.REMOVE);
		TAG_RULES.put(Tag.MedicalRecordLocator, AnonRule.REMOVE);
		TAG_RULES.put(Tag.MilitaryRank, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ModifiedAttributesSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ModifiedImageDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ModifyingDeviceID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ModifyingDeviceManufacturer, AnonRule.REMOVE);
		TAG_RULES.put(Tag.NamesOfIntendedRecipientsOfResults, AnonRule.REMOVE);
		TAG_RULES.put(Tag.Occupation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OperatorIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OperatorsName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OriginalAttributesSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OrderCallbackPhoneNumber, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OrderEnteredBy, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OrderEntererLocation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OtherPatientIDs, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OtherPatientIDsSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OtherPatientNames, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OverlayComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OverlayData, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OverlayDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.OverlayTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PaletteColorLookupTableUID, AnonRule.REUID);
		TAG_RULES.put(Tag.ParticipantSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientAddress, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientID, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.PatientSexNeutered, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientState, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientTransportArrangements, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientAge, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientBirthDate, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.PatientBirthName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientBirthTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientInstitutionResidence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientInsurancePlanCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientMotherBirthName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientName, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.PatientPrimaryLanguageCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientPrimaryLanguageModifierCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientReligiousPreference, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientSex, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.PatientSize, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientTelephoneNumbers, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PatientWeight, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedLocation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedProcedureStepDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedProcedureStepID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedProcedureStepStartDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedProcedureStepStartTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedStationAETitle, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedStationGeographicLocationCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedStationName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformedStationNameCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformingPhysicianIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PerformingPhysicianName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PersonAddress, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PersonIdentificationCodeSequence, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.PersonName, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.PersonTelephoneNumbers, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PhysicianApprovingInterpretation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PhysiciansReadingStudyIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PhysiciansOfRecord, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PhysiciansOfRecordIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PlacerOrderNumberImagingServiceRequest, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.PlateID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PreMedication, AnonRule.REMOVE);
		TAG_RULES.put(Tag.PregnancyStatus, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ProtocolName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReasonForTheImagingServiceRequest, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReasonForStudy, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferencedDigitalSignatureSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferencedFrameOfReferenceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.ReferencedGeneralPurposeScheduledProcedureStepTransactionUID, AnonRule.REUID);
		TAG_RULES.put(Tag.ReferencedImageSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferencedPatientAliasSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferencedPerformedProcedureStepSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferencedSOPInstanceMACSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferencedSOPInstanceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.ReferencedSOPInstanceUIDInFile, AnonRule.REUID);
		TAG_RULES.put(Tag.ReferringPhysicianAddress, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferringPhysicianIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferringPhysicianName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReferringPhysicianTelephoneNumbers, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RegionOfResidence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RelatedFrameOfReferenceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.RequestAttributesSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestedContrastAgent, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestedProcedureComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestedProcedureDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestedProcedureID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestedProcedureLocation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestedSOPInstanceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.RequestingPhysician, AnonRule.REMOVE);
		TAG_RULES.put(Tag.RequestingService, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ResponsibleOrganization, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ResponsiblePerson, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ResultsComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledPatientInstitutionResidence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ResultsDistributionListSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ResultsIDIssuer, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ReviewerName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledHumanPerformersSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledPerformingPhysicianIdentificationSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledPerformingPhysicianName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledProcedureStepEndDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledProcedureStepEndTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledProcedureStepDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledProcedureStepLocation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledProcedureStepStartDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledProcedureStepStartTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledStationAETitle, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledStationGeographicLocationCodeSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledStationName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledStationName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledStudyLocation, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ScheduledStudyLocationAETitle, AnonRule.REMOVE);
		TAG_RULES.put(Tag.SeriesDate, AnonRule.REMOVE);
		TAG_RULES.put(Tag.SeriesDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.SeriesInstanceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.SeriesTime, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ServiceEpisodeDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.ServiceEpisodeID, AnonRule.REMOVE);
		TAG_RULES.put(Tag.SmokingStatus, AnonRule.REMOVE);
		TAG_RULES.put(Tag.SOPInstanceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.SourceImageSequence, AnonRule.REMOVE);
		TAG_RULES.put(Tag.SpecialNeeds, AnonRule.REMOVE);
		TAG_RULES.put(Tag.StationName, AnonRule.REMOVE);
		TAG_RULES.put(Tag.StorageMediaFileSetID, AnonRule.REUID);
		TAG_RULES.put(Tag.StudyComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.StudyDate, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.StudyDescription, AnonRule.REMOVE);
		TAG_RULES.put(Tag.StudyID, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.StudyIDIssuer, AnonRule.REMOVE);
		TAG_RULES.put(Tag.StudyInstanceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.StudyTime, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.SynchronizationFrameOfReferenceUID, AnonRule.REUID);
		TAG_RULES.put(Tag.TemplateExtensionCreatorUID, AnonRule.REUID);
		TAG_RULES.put(Tag.TemplateExtensionOrganizationUID, AnonRule.REUID);
		TAG_RULES.put(Tag.TextComments, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TextString, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TimezoneOffsetFromUTC, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TopicAuthor, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TopicKeywords, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TopicSubject, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TopicTitle, AnonRule.REMOVE);
		TAG_RULES.put(Tag.TransactionUID, AnonRule.REUID);
		TAG_RULES.put(Tag.UID, AnonRule.REUID);
		TAG_RULES.put(Tag.VerifyingObserverIdentificationCodeSequence, AnonRule.REPLACE_ZERO);
		TAG_RULES.put(Tag.VerifyingObserverName, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.VerifyingObserverSequence, AnonRule.REPLACE_NON_ZERO);
		TAG_RULES.put(Tag.VerifyingOrganization, AnonRule.REMOVE);
		TAG_RULES.put(Tag.VisitComments, AnonRule.REMOVE);
	}
	
	private static final Map<Integer, Object> TAG_REPLACEMENTS = new HashMap<>();
	static {
		TAG_REPLACEMENTS.put(Tag.PatientName, "Anonymised");
	}
	
	protected static final Map<Integer, String> ANON_INFO = new HashMap<>();
	static {
		ANON_INFO.put(Tag.PatientIdentityRemoved, "YES");
		ANON_INFO.put(Tag.DeidentificationMethod, "113100");
	}

	@Override
	public Map<Integer, AnonRule> getTagRules() {
		return Collections.unmodifiableMap(new HashMap<>(TAG_RULES));
	}

	@Override
	public Map<Integer, Object> getTagReplacements() {
		return Collections.unmodifiableMap(new HashMap<>(TAG_REPLACEMENTS));
	}

	@Override
	public Map<Integer, String> getAnonTagInfo() {
		return Collections.unmodifiableMap(new HashMap<>(ANON_INFO));
	}

	@Override
	public Object getTagReplacement(Integer tag, Object defaultVal) {
		return TAG_REPLACEMENTS.getOrDefault(tag, defaultVal);
	}


}
