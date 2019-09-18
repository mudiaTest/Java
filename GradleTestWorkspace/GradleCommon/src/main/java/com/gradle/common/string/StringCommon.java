package com.gradle.common.string;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class StringCommon {

	public String getTestString() {
		return StringUtils.leftPad(Integer.toHexString(Integer.valueOf("Test")), 10, "0");
	}
}
