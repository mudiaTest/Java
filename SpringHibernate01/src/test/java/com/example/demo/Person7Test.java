package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;

import org.javatuples.Pair;
import org.javatuples.Tuple;
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
import com.example.demo.select.Address7_1;
import com.example.demo.select.Person7_1;
import com.example.demo.select.Person7_1Dao;
import com.example.demo.select.Person7_2;
import com.example.demo.select.Person7_2Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person7Test {
	
	@Autowired
	private Person7_1Dao repo71;
	
	@Transactional
	private Pair<Integer, Integer> initPerson7_1() {
		repo71.deleteAll();			
		Person7_1 p1 = new Person7_1();
		p1.name ="os7_1_1";
		p1.setAddress(new Address7_1("addr_7_1_1"));
		p1.addContact("envel7_1_1_1");
		p1.addContact("envel7_1_1_2");
		p1.addJobApplication("comp7_1_1_1");		
		p1.addJobApplication("comp7_1_1_2");		
		repo71.save(p1);
		
		Person7_1 p2 = new Person7_1();
		p2.name ="os7_1_2";
		p2.setAddress(new Address7_1("addr_7_1_2"));
		p2.addContact("envel7_1_2_1");
		p2.addContact("envel7_1_2_2");
		p2.addJobApplication("comp7_1_2_1");		
		p2.addJobApplication("comp7_1_2_2");	
		repo71.save(p2);
		
		return Pair.with(p1.getId(), p2.getId());
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void person1Init() {
		Pair<Integer, Integer> peopleIds = initPerson7_1();
		Person7_1 p1 = repo71.findById(peopleIds.getValue0()).get();		
		List<List<String>> lst1 = repo71.caseTest_Proper1("os7_1_1");
		List<List<String>> lst2 = repo71.caseTest_Proper2("os7_1_1");
		int t = 0;
	}
	
	// --------------------------------------------------------------
	
	@Autowired
	private Person7_2Dao repo72;
	@Autowired
	private EntityManager em;
	
	@Transactional
	private void initPerson7_2() {
		repo72.deleteAll();			
		Person7_2 p1 = new Person7_2();
		p1.name ="os7_21";	
		p1.setAge1(1);
		p1.setAge2(2);
		p1.setAge3(Integer.valueOf(3));
		repo72.save(p1);
				
		Person7_2 p2 = new Person7_2();
		p2.name ="os7_22";	
		p2.setAge1(1);
		p2.setAge2(21);
		p2.setAge3(Integer.valueOf(31));
		repo72.save(p2);
	}
	
	@Test
	@Transactional
	@Rollback(false)	
	public void test72() {
		initPerson7_2();
		List<Person7_2> p1 = repo72.getByAge1(1);		
		Person7_2 p2 = repo72.getByAge2(2);		
		Person7_2 p3 = repo72.getByAge3(Integer.valueOf(3));	
		
		List<Person7_2> p41 = (List<Person7_2>) em.createNamedQuery("test1")
				.setParameter("age", 1)
				.getResultList();
		List<Person7_2> p42 = (List<Person7_2>) em.createNamedQuery("test11")
				.setParameter(1, 1)
				.getResultList();
		Person7_2 p43 = (Person7_2) em.createNamedQuery("test2")
				.setParameter("age", 2)
				.getSingleResult();
		Person7_2 p44 = (Person7_2) em.createNamedQuery("test3")
				.setParameter("age", Integer.valueOf(3))
				.getSingleResult();
		
			/*
			 * p, p.name odda Object[] dla ka¿dego wiersza.
			 * Uwaga, przy ustalonym Person7_2 znajdzie wszystkie pola i odda wynik okrojony do obiektu Pperson7_2
			 */
		List<Object[]> p51 = repo72.selectObjAndField();
			/*
			 * @NamerQuery jest bardziej wymagaj¹ce i nie chcia³ rzutowac. Przyjmuje tylko Object[] 
			 */
		List<Object[]> p52 = (List<Object[]>)em.createNamedQuery("selectObjAndField").getResultList();
		
		Integer age2_11 = repo72.selectMax();
		Person7_2 age2_12 = repo72.selectMaxWhereOk();
//		Integer age2_2 = repo72.selectMaxHavingTrue();
//		Integer age2_3 = repo72.selectMaxHavingFalse();
		
		int t = 0;
	}
	
}
