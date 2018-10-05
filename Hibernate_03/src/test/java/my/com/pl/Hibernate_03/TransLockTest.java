package my.com.pl.Hibernate_03;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.Hibernate_03.dao.SubClass70Dao;
import my.com.pl.Hibernate_03.dao.Test1Dao;
import my.com.pl.Hibernate_03.dao.Test71Dao;
import my.com.pl.Hibernate_03.dao.Test70Dao;
import my.com.pl.Hibernate_03.domain.SubClass1;
import my.com.pl.Hibernate_03.domain.SubClass7;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.domain.Test70;
import my.com.pl.Hibernate_03.domain.Test71;
import my.com.pl.Hibernate_03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransLockTest {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
	@Autowired
	Test70Dao t7d;
	@Autowired
	Test71Dao t71d;
	@Autowired
	SubClass70Dao sc71d;
	
	private void fillTest7() {
		Test70 t7 = new Test70();
		t7.setId(Long.valueOf(1));
		t7.setVal(11);	
		t7d.save(t7);
	}
	
	private void fillTest71() {
		Test71 t71 = new Test71();
		t71.setId(Long.valueOf(1));
		t71.setVal(11);	
		SubClass7 sc1 = new SubClass7();
		sc1.setId(21);
		sc1.setSubIntVal1(21);
		sc1.setT71(t71);		
		//t71.getSet().add(sc1);
		SubClass7 sc2 = new SubClass7();
		sc2.setId(22);
		sc2.setSubIntVal1(22);
		sc2.setT71(t71);
		//t71.getSet().add(sc2);
		t71d.save(t71);
		List<SubClass7> l = new ArrayList<>();
		l.add(sc1);
		l.add(sc2);
		sc71d.saveAll(l);
		int t = 0;
	}
	
//	@Test
	@Transactional
	@Commit
	public void testVersion() {
		fillTest7();
		Test70 t7 = t7d.findById(Long.valueOf(1)).get();
		/*Tu wstawiæ brake i podmieniæ rêcznie dane w DB a rzuci wyj¹tkiemObjectOptymisticLockingException*/
		t7.setVal(12);
		t7d.save(t7);
		int t = 0;
	}
	
	@Test
	//@Transactional
	@Commit
	/*
	 * Jeœli set jest wczytywany lazy i nie zostanie wczytany, to 
	 */
	public void testVersionOM() {
		fillTest71();
		Test71 t71 = t71d.findById(Long.valueOf(1)).get();
		/*Tu wstawiæ brake i podmieniæ rêcznie dane w DB a rzuci wyj¹tkiemObjectOptymisticLockingException*/		
		t71.setVal(12);
		t71d.save(t71);
		int t = 0;
	}
	
	//@Test
	//@Transactional
	@Commit
	public void testVersionOMFetch() {
		fillTest71();
		Test71 t71 = t71d.getFullById(Long.valueOf(1)).get(0);
		//t71.getSet().size();
		/*Tu wstawiæ brake i podmieniæ rêcznie dane w DB a rzuci wyj¹tkiemObjectOptymisticLockingException*/
		t71.setVal(12);
//		SubClass7 sc = t71.getSet().iterator().next();
//		if (sc.getId() == 21)
//			sc.setSubIntVal1(31);
//		else
//			t71.getSet().iterator().next().setSubIntVal1(31);
		t71d.save(t71);
		int t = 0;
	}
}
