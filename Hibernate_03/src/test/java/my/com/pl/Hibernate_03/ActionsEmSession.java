package my.com.pl.Hibernate_03;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.Hibernate_03.dao.Test1Dao;
import my.com.pl.Hibernate_03.domain.SubClass1;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionsEmSession {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
	@Autowired
	Test1Dao t1d;
	
	private Test1 getTest1Obj() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		SubClass1 so1 = new SubClass1();
		t1.setSo1(so1);
		so1.setSubIntVal1(11);
		so1.setSubStVal1("a");
		return t1;
	}
	
		
	//Testowanie 
//	@Test
	//@Transactional
	@Commit
	public void mergeTest() {
		try {		
			ntw.inNewTrans(() ->{
				Test1 t1 = getTest1Obj();
				ntw.inTrans(()->
				{
					em.persist(t1);				
					em.flush();
					em.detach(t1);
				});
				t1.setIntVal1(99);
				ntw.inTrans(()->{
					em.merge(t1);
				});
				int t = 0;
			});
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
//	@Test
//	@Transactional
	@Commit
	public void persistErrTest() {
		try {		
//			ntw.inNewTrans(() ->{
				Test1 t1 = getTest1Obj();
				ntw.inNewTrans(()->
				{
					em.persist(t1);				
					em.flush();
					//em.detach(t1);
				});
				t1.setIntVal1(99);
				ntw.inNewTrans(()->{
					Test1 t2 = em.find(Test1.class, t1.getId());
					em.detach(t2);
					t2.setIntVal1(99);				
					em.persist(t2);
					int t = 0;
				});
				int t = 0;
//			});
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	@Test
//	@Transactional
	@Commit
	public void saveTest() {
		try {		
//			ntw.inNewTrans(() ->{
			Test1 t1 = getTest1Obj();
			ntw.inNewTrans(()->
			{
				em.persist(t1);				
				em.flush();
				//em.detach(t1);
			});
			t1.setIntVal1(99);
			ntw.inNewTrans(()->{
				Session s = em.unwrap(Session.class);
				Test1 t2 = em.find(Test1.class, t1.getId());
				em.detach(t2);
				//t2.setIntVal1(88);				
				s.save(t2);
				int t = 0;
			});
			int t = 0;
//			});
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	@Test
//	@Transactional
	@Commit
	public void updateTest() {
		try {		
//			ntw.inNewTrans(() ->{
			Test1 t1 = getTest1Obj();
			ntw.inNewTrans(()->
			{
				em.persist(t1);				
				em.flush();
				//em.detach(t1);
			});
			t1.setIntVal1(99);
			ntw.inNewTrans(()->{
				Session s = em.unwrap(Session.class);
				Test1 t2 = em.find(Test1.class, t1.getId());
				em.detach(t2);
				//t2.setIntVal1(88);				
				s.update(t2);
				int t = 0;
			});
			int t = 0;
//			});
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	

}
