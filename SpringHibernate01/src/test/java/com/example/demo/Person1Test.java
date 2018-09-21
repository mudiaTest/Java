package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.oneManyEmbdedded.Address1;
import com.example.demo.oneManyEmbdedded.Person1;
import com.example.demo.oneManyEmbdedded.Person1Dao;
import com.example.demo.oneManyEmbdedded.PersonFeature1;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person1Test {
	
	@Autowired
	private Person1Dao repo1;
	
	@Test
	@Transactional
	@Rollback(false)
	public void person1Init() {
		repo1.deleteAll();
		Person1 p = new Person1();
		p.setName("os1");
		p.setAddress(new Address1("city1", "street1"));
		p.getFeatures().add(new PersonFeature1("rasa", "kaukaska"));
		p.getFeatures().add(new PersonFeature1("w³osy", "brunet"));	
		p.getStrings().add("test1");
		p.getStrings().add("test2");
		p.getAnnoStrings().add(new String("test3"));
		p.getAnnoStrings().add(new String("test4"));
		repo1.save(p);
	}
}
