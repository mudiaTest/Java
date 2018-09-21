package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.db1.dao.Test1Dao;
import com.example.demo.db1.domain.Test1;
import com.example.demo.db2.dao.Test2Dao;
import com.example.demo.db2.domain.Test2;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { ConfigDB1.class, ConfigDB2.class })
public class Hibernate04ApplicationTests {
	
	@Autowired
	Test1Dao t1d;
	
	@Autowired
	Test2Dao t2d;
	
	@Test
	public void contextLoads() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		
		Test2 t2 = new Test2();
		t2.setId(2);
		t2.setStVal1("a");
		
		t1d.save(t1);
		t2d.save(t2);
	}

}
