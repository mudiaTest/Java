package com.my.user;

import com.my.common.*;

public class UseGradleCommons {
	public static void main(String[] args) {
		System.out.println("UseGradleCommons.main -> Start");
		StringCommon sc = new StringCommon();
		System.out.println("--> " + sc.getTestString("test", 10));
		System.out.println("UseGradleCommons.main -> Finish");
	}
}
