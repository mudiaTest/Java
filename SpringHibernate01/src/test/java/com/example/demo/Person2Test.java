package com.example.demo;

import java.util.Arrays;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.oneManyEmbdedded.Person2;
import com.example.demo.oneManyEmbdedded.Person2Dao;
import com.example.demo.oneManyEmbdedded.PersonFeature1;
import com.example.demo.oneManyEmbdedded.PersonFeature2;
import com.example.demo.oneManyEmbdedded.PersonFeature2Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person2Test {

	@Autowired
	private Person2Dao repo2;
	@Autowired
	private PersonFeature2Dao repoFeature2;
	
	@Transactional
	private Integer person2Init(){
		repo2.deleteAll();
		Person2 p = new Person2();
		p.setName("os1");
		p.getFeatures().put(11, new PersonFeature1("rasa", "kaukaska"));
		p.getFeatures().put(12, new PersonFeature1("w³osy", "brunet"));	
		
		PersonFeature2 pf1 = new PersonFeature2("rasa2", "kaukaska2");
		PersonFeature2 pf2 = new PersonFeature2("w³osy2", "brunet2");
		PersonFeature2 pf3 = new PersonFeature2("rasa3", "kaukaska3");
		PersonFeature2 pf4 = new PersonFeature2("w³osy3", "brunet3");
		p.getFeatures2().put(21, pf1);
		p.getFeatures2().put(22, pf2);		
		p.getFeatures3().put(31, pf3);
		p.getFeatures3().put(32, pf4);
		/*
		 * Koniecznoœæ zapisu pf1 i pf2, bo s¹ to Entity u¿yte jako @ElementCollection. 
		 * Z tego powodu nie zostaj zapisane kaskadowo. @ElementCollection nie ma opcji cascase
		 * pf3 i pf4 s¹ w colekcji oznaczonej @OneToMany, która ma cascade i to cascade zosta³o 
		 * w³¹czone. BEZ CASCADE te¿ nale¿a³o by je wpierw zapisaæ !
		 */
		repoFeature2.saveAll(Arrays.asList(new PersonFeature2[]{pf1, pf2}));		
		repo2.save(p);
		return p.getId();
	}	
	
	@Transactional
	private Person2 getPersonById(Integer id){
		return repo2.findById(id).get();
	}
	
	//@Test
	@Rollback(false)
	public void person2Test() {
		Integer id = person2Init();
		Person2 p = getPersonById(id);
		int t = 0;
	}

}
