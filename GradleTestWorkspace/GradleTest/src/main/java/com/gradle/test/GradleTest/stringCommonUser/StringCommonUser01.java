package com.gradle.test.GradleTest.stringCommonUser;

import javax.annotation.PostConstruct;

import org.javatuples.Pair;
import org.springframework.stereotype.Service;

@Service
public class StringCommonUser01 {

	public void dummy() {
		Pair p = new Pair("1", 2);
	}
	
	@PostConstruct
    public void postConstruct() {
        System.out.println("Test!");
        dummy();
    }
}
