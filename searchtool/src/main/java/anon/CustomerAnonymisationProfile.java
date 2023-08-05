/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package anon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.dcm4che3.data.ElementDictionary;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;

public class CustomerAnonymisationProfile extends DefaultAnonymisationProfile {

	private static final Map<Integer, String> CUSTOM_ANON_INFO = new HashMap<>(ANON_INFO);
	static {
		CUSTOM_ANON_INFO.put(Tag.TimezoneOffsetFromUTC, "+0100");
	}
	
	@Override
	public Object getTagReplacement(Integer tag, Object defaultVal) {
		ElementDictionary dict = ElementDictionary.getStandardElementDictionary();
        VR vr = dict.vrOf(tag);
        if (vr.equals(VR.DA)) {
        	return "20230101";
        } else if (vr.equals(VR.TM)) {
        	return "120000.0";
        } else {
        	return super.getTagReplacement(tag, defaultVal);
        }
	}
	
	@Override
	public Map<Integer, String> getAnonTagInfo() {
		return Collections.unmodifiableMap(new HashMap<>(CUSTOM_ANON_INFO));
	}

}
