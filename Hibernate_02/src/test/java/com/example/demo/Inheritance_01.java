package com.example.demo;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.inheritance.doa.Person_01Doa;
import com.example.demo.inheritance.doa.WorkerDoa;
import com.example.demo.inheritance.dto.Person;
import com.example.demo.inheritance.dto.Worker;

@EnableTransactionManagement
@RunWith(SpringRunner.class)
@SpringBootTest
public class Inheritance_01 {
	@Autowired
	Person_01Doa repo01;
	@Autowired
	WorkerDoa repoWrk;
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
	//bez znaczenia, bo wywo³ujemy to nie przez beana
	//@Transactional//(propagation=Propagation.REQUIRES_NEW)
	@Rollback(false)
	public Integer initPerson_01() {
		Query q = em.createNamedQuery("delAll");
		//q.executeUpdate();
		Worker w1 = new Worker("p01", "tech");
		//repo01.save(w1);
		//ntw.inTrans(() -> em.persist(w1));
		em.persist(w1);
		return w1.getId();
	}
	
	//bez znaczenia, bo wywo³ujemy to nie przez beana
	//@Transactional
	@Rollback(false)
	private void getPerson_01(Integer id) {
		Worker w1 = repoWrk.findById(id).get();		
//		Worker w2 =  ntw.inTrans(() -> em.find(Worker.class, id)); 
		
		Query q = em.createNamedQuery("getById").setParameter("id", id);
		Worker w3 = (Worker)q.getSingleResult();
		
		int t=0;		
	}
	
	
	@Test
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//	@Transactional
	@Rollback(false)
	public void test01() {
//		Integer id = ntw.inTrans(()-> initPerson_01()); 
		Integer id = initPerson_01();
		if (id != null)
//			ntw.inTrans(()->getPerson_01(id));
			getPerson_01(id);
	}
}
