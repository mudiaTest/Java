package com.my.pl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.my.pl.dao.Test1CustomDao;
import com.my.pl.dao.Test1Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Hibernate05ApplicationTests {
	
	@Autowired
	Test1Dao t1d;

	@Test
	public void contextLoads() {
		int t = 0;
		
	}

}
