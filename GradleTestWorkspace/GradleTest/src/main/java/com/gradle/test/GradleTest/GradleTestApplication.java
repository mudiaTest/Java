package com.gradle.test.GradleTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gradle.test.GradleTest.stringCommonUser.StringCommonUser01;

@SpringBootApplication
public class GradleTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradleTestApplication.class, args);
		StringCommonUser01 suo01 = new StringCommonUser01();
	}

}
