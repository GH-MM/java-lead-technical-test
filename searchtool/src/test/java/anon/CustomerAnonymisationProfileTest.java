/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package anon;

import static org.junit.Assert.*;

import java.util.Map;

import org.dcm4che3.data.Tag;
import org.junit.Test;

public class CustomerAnonymisationProfileTest {

	AnonymisationProfile profile = new CustomerAnonymisationProfile();
	
	@Test
	public void testAnonTags() {
		
		Map<Integer, String> anonInfo = profile.getAnonTagInfo();
		assertEquals(3, anonInfo.size());
		String val = anonInfo.get(Tag.PatientIdentityRemoved);
		String expected = "YES";
		assertEquals(expected, val);
		
		val = anonInfo.get(Tag.DeidentificationMethod);
		expected = "113100";
		assertEquals(expected, val);
		
		val = anonInfo.get(Tag.TimezoneOffsetFromUTC);
		expected = "+0100";
		assertEquals(expected, val);
	}

	@Test
	public void testReplacement() {
		String date = (String) profile.getTagReplacement(Tag.StudyDate, null);
		String expected = "20230101";
		assertEquals(expected, date);
		String time = (String) profile.getTagReplacement(Tag.StudyTime, null);
		expected = "120000.0";
		assertEquals(expected, time);
	}
}
