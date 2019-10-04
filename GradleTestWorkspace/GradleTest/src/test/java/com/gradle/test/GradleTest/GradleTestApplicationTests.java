package com.gradle.test.GradleTest;

import org.javatuples.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradleTestApplicationTests {

	@Test
	public void contextLoads() {
		Pair p = new Pair("1", 2);
	}

}
