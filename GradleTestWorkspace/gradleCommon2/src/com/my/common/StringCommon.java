package com.my.common;

import org.apache.commons.lang3.StringUtils;

public class StringCommon {

	public String getTestString(String a, Integer number) {
		return StringUtils.leftPad(a, number, "0");
	}
}
