/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package anon;

import java.util.Map;

public interface AnonymisationProfile {
	
	/**
	 * Possible anonymisation actions for tags.
	 * 
	 * @author Dez Wright
	 */
	public enum AnonRule {
		REPLACE_NON_ZERO,
		REPLACE_ZERO,
		REMOVE,
		KEEP,
		CLEAN,
		REUID
	}

	/**
	 * Get the tag rules associated with an anonymisation
	 * profile.
	 * 
	 * @return A lookup of anonymisation actions for tags
	 * associated with an anonymisation profile.
	 */
	Map<Integer, AnonRule> getTagRules();
	
	/**
	 * Get a replacement value for a DICOM tag
	 * 
	 * @param tag the DICOM tag to be edited
	 * @param defaultVal a default value for the tag 
	 * @return the replacement value
	 */
	Object getTagReplacement(Integer tag, Object defaultVal);
	
	/**
	 * A lookup of tag replacements set as part of anon profile
	 * for tags that need a dummy value.
	 * 
	 * @return tag lookup.
	 */
	Map<Integer, Object> getTagReplacements();
	
	/**
	 * Get the anonymisation tags associated with the anonymisation
	 * profile. e.g., the de-id flag and type
	 * 
	 * @return A lookup of tags and values to add to the header being
	 * edited by the profile.
	 */
	Map<Integer, String> getAnonTagInfo();
	
	/**
	 * The remove privates  specification for the anonymisation 
	 * profile.
	 * 
	 * @return true if the private tags in the header should be 
	 * removed, false otherwise.  Default true.
	 */
	default boolean removePrivateTags() {
		return true;
	}
	
	/**
	 * The re-UID specification for the anonymisation profile.
	 * 
	 * @return true if the UIDs in the header should be 
	 * replaced with new ones, false otherwise.  Default true.
	 */
	default boolean reUid() {
		return true;
	}
	
}
