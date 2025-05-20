package com.ssm.cmn.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

public class CmnUtils extends NumberUtils {
	public static boolean isEmpty(Object obj) {
		if(obj == null) {
			return true;
		}
		if((obj instanceof String) && StringUtils.isEmpty(null == obj ? "" : String.valueOf(obj))) {
			return true;
		}
		if(obj instanceof Map) {
			return ((Map<?,?>)obj).isEmpty();
		}
		if(obj instanceof List) {
			return ((List<?>)obj).isEmpty();
		}
		if(obj instanceof Object[]) {
			return (((Object[])obj).length == 0);
		}
		return false;
	}
}
