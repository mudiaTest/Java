package my.com.pl.Hibernate_03;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.Hibernate_03.dao.Test1Dao;
import my.com.pl.Hibernate_03.domain.SubClass1;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
	@Autowired
	Test1Dao t1d;
	
	private void fillTest1UsingRepo() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		SubClass1 so1 = new SubClass1();
		t1.setSo1(so1);
		so1.setSubIntVal1(11);
		so1.setSubStVal1("a");
		
		/*
		 * DomainEvents dzia³aja tylko gdy korzystany z Repozytoriów. 
		 * Jeœli zapis jest wykonywany bezpoœrednio przez EM, to nie beda uruchamiane
		 */
		t1d.save(t1);
		//em.persist(t1);
	}
	
	/* Standardowy delete mo¿e nie zostaæ wykonany, ale pobranie obiektu bêdzie 
	 * pamiêtaæ, ¿e jest on przeznaczony do usuniêcia (lub ju¿ usuniêty) i odda null	  
	 */
	private void delete() {
		try {
			Test1 t1 = t1d.findById((long)1).get();
			t1d.delete(t1);
			t1 = t1d.findById((long)1).get();
			int t = 0;
		}
		catch (Exception e)
		{
			int t = 0;
		}			
	}
	
	/* BulkDelete wykona siê, ale em nie bêdzie o tym powiadomiony i odda obiekt z pamiêci (cache), który defacto ine istnieje
	 */
	private void bulkDelete() {
		try {
			Test1 t1 = t1d.findById((long)1).get();
			t1d.bulkTest1Delete((long)1);
			t1 = t1d.findById((long)1).get();
			int t = 0;
		}
		catch (Exception e)
		{
			int t = 0;
		}
	}
	
	//Testowanie 
	@Test
	public void deleteTest() {
		try {
			ntw.inNewTrans(()->fillTest1UsingRepo());	
			ntw.inNewTrans(()->delete());	
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	//Testowanie 
	//@Test
	public void bulkDeleteTest() {
		try {
			ntw.inNewTrans(()->fillTest1UsingRepo());	
			ntw.inNewTrans(()->bulkDelete());	
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}

}
