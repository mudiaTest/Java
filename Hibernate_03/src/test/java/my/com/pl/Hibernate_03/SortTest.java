package my.com.pl.Hibernate_03;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import my.com.pl.Hibernate_03.dao.Test1CustomDao;
import my.com.pl.Hibernate_03.dao.CustomDao;
import my.com.pl.Hibernate_03.dao.Test1Dao;
import my.com.pl.Hibernate_03.domain.SubClass1;
import my.com.pl.Hibernate_03.domain.SubObj;
import my.com.pl.Hibernate_03.domain.Test1;
import my.com.pl.Hibernate_03.domain.Test2;
import my.com.pl.Hibernate_03.domain.Test3;
import my.com.pl.Hibernate_03.domain.Test4;
import my.com.pl.Hibernate_03.utils.NewTransactionWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SortTest {

	//@Test
	public void contextLoads() {
	}
	
	@Autowired
	EntityManager em;
	@Autowired
	NewTransactionWrapper ntw;
	
	//Zwrot pojedynczej wartoœci prostej 
	//@Test
	public void nativeToObj1() {
		try {
			/* Jako drugi parametr mo¿e byæ podana tylko nazwa klasy oznaczonej @Entity
			 * W przypadku pojedynczej wartoœci integer, string... mozna upomin¹æ ten parameter
			 * i castowaæ bez problemu 
			 */
			Query q = em.createNativeQuery("SELECT 1 FROM test2"/*, Integer*/);
			Integer res = (Integer) q.getSingleResult();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
		
		int t = 0;
	}	
	
	private void fillTest1() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		SubClass1 so1 = new SubClass1();
		t1.setSo1(so1);
		so1.setSubIntVal1(11);
		so1.setSubStVal1("a");
		em.persist(t1);
	}
	
	private void fillTest1UsingRepo() {
		Test1 t1 = new Test1();
		t1.setId(1);
		t1.setIntVal1(11);
		SubClass1 so1 = new SubClass1();
		t1.setSo1(so1);
		so1.setSubIntVal1(11);
		so1.setSubStVal1("a");
		t1d.save(t1);
	}
	
	//Zwrot obiektu p³askiego dla pojo i sql
	//@Test
	public void nativeToObj2() {
		try {
			ntw.inNewTrans(()->fillTest1());
			/* W przypadku, gdy zwracamy obiekt inny ni¿ typy prostego, to nale¿y podaæ klasê, 
			 * bo istnienie tego parametru rzutuje na budowê obiektu res i mo¿e uniemo¿liwiæ
			 * pózniejsze castowanie.  
			 */
			Query q = em.createNativeQuery("SELECT * FROM test2 b WHERE b.id = 1", Test2.class);
			Object o = q.getSingleResult();
			Test2 res = (Test2) o;
			/* Mo¿na te¿ od razu rzutowaæ
			 * Test2 res = (Test2) q.getSingleResult();
			 */
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
		
		int t = 0;
	}
	
	private void fillTest3() {
		Test3 t31 = new Test3();
		t31.setId(1);
		t31.setIntVal1(11);
		Test2 t21 = new Test2();
		t31.setT2(t21);
		t21.setId(2);
		t21.setIntVal(21);
		t21.setStrVal("b");
		em.persist(t31);
		
		Test3 t32 = new Test3();
		t32.setId(3);
		t32.setIntVal1(12);
		Test2 t22 = new Test2();
		t32.setT2(t22);
		t22.setId(4);
		t22.setIntVal(22);
		t22.setStrVal("c");
		em.persist(t32);
		
		Test3 t33 = new Test3();
		t33.setId(5);
		t33.setIntVal1(13);
		Test2 t23 = new Test2();
		t33.setT2(t23);
		t23.setId(6);
		t23.setIntVal(23);
		t23.setStrVal("d");
		em.persist(t33);
	}
	
	//Zwot obiektu wg³¹b dla pojo i p³askiego dla sql
	//@Test
	public void nativeToObj3() {
		try {
			//ntw.inNewTrans(()->fillTest3());			
			Query q = em.createNativeQuery("SELECT * FROM test3 a", Test3.class);
			/* Skutkiem ubocznym parametru "Test3.class" i tego ¿e OO jet EAGER (default) jest to, ¿e
			 * doczytane zostaj¹ podobiekty Test2. Dla ka¿dego Test3 osobny SQL pobieraj¹cy Test2.
			 * Doczytanie bêdzie mia³o miejsce podczas wykonywania getResultList.
			 */
			List<Object> o = q.getResultList();			
			Test3 res = (Test3) o.get(0);
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}		
		int t = 0;
	}
	
	//Zwot obiektu wg³¹b dla pojo i p³askiego dla sql
	//@Test
	public void nativeToObj4() {
		try {
			//ntw.inNewTrans(()->fillTest3());			
			Query q = em.createNativeQuery("SELECT * FROM test3 a LEFT JOIN test2 b ON a.t2_id = b.id", Test3.class);
			/* Skutkiem ubocznym parametru "Test3.class" i tego ¿e OO jest EAGER (default) jest to, ¿e
			 * doczytane zostaj¹ podobiekty Test2. Dla ka¿dego Test3 osobny SQL pobieraj¹cy Test2.
			 * Doczytanie bêdzie mia³o miejsce podczas wykonywania getResultList.
			 */
			List<Object> o = q.getResultList();			
			Test3 res = (Test3) o.get(0);
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}		
		int t = 0;
	}
	
	@Autowired
	Test1Dao t1d;
	
	//Testowanie projekcji
//	@Test
	public void projectionTest2() {
		ntw.inNewTrans(()->fillTest3());	
		List<SubObj> lst = t1d.getSubObj();		
		SubObj so = lst.get(0);
		int t = so.getV2();
		t = 0;
	}
	
	//Testowanie "Sort"
	//@Test
		public void sortTest() {
			try {
				ntw.inNewTrans(()->fillTest3());
				List<Integer> res = t1d.getSorted(new Sort(Sort.Direction.DESC, "id"));			
				Iterator<Integer> it = res.iterator();
				int t = it.next();
				t = it.next();			
				t = 0;
			}
			catch (Exception e) {
				int t = 0;
			}
		}
	
	//Testowanie "Page"
	//@Test
	public void pageableTest() {
		try {
			ntw.inNewTrans(()->fillTest3());
			/*
			 * Tworzenie obiektu pagable, który pozwloli na pobranie 1 strony w rozmiarze 2 elementów
			 * Co sprowadza siê do "limit 2" 
			 */
			Pageable pageable = PageRequest.of(0, 2, 
					new Sort(Sort.Direction.DESC, "id")
					.and( new Sort(Sort.Direction.ASC, "t2.id") )
					);
			/*
			 * Tworzenie obiektu pagable, który pozwloli na pobranie 5 strony w rozmiarze 2 elementów (9 i 10 element)
			 * Co sprowadza siê do "limit 2 offset 8" 
			 */
			//Pageable pageable = PageRequest.of(4, 2);
			Page<Integer> res = t1d.getPaged(pageable);	
			List<Integer> lst = res.getContent();			
			Iterator<Integer> it = res.iterator();
			int t = it.next();
			t = it.next();			
			t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	//Testowanie "Slice"
	//Zsadniczo dzia³a tak jak stronicowanie, ale zwraca inny obiekt, pozwalaj¹cy na pobranie kolejnej partii wyników
	//@Test
	public void slicedTest() {
		try {
			ntw.inNewTrans(()->fillTest3());			
			Pageable pageable = PageRequest.of(0, 2);
			Slice<Integer> res = t1d.getSliced(pageable);	
			List<Integer> lst = res.getContent();			
			res = t1d.getSliced(res.nextPageable());		
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void fillTest4() {
		Test4 t31 = new Test4();
		t31.setId(1);
		t31.setIntVal1(11);
		t31.setT2(new ArrayList<Test2>());
		Test2 t21 = new Test2();
		t31.getT2().add(t21);
			t21.setId(11);
			t21.setIntVal(21);
			t21.setStrVal("1a");
		Test2 t22 = new Test2();
		t31.getT2().add(t22);
			t22.setId(12);
			t22.setIntVal(22);
			t22.setStrVal("1b");
		em.persist(t31);
		
		Test4 t32 = new Test4();
		t32.setId(3);
		t32.setIntVal1(12);
		t32.setT2(new ArrayList<Test2>());
		Test2 t23 = new Test2();
		t32.getT2().add(t23);
			t23.setId(13);
			t23.setIntVal(23);
			t23.setStrVal("1c");
		Test2 t24 = new Test2();
		t32.getT2().add(t24);
			t24.setId(14);
			t24.setIntVal(24);
			t24.setStrVal("1d");	
		em.persist(t32);
		
		Test4 t33 = new Test4();
		t33.setId(5);
		t33.setIntVal1(13);
		t33.setT2(new ArrayList<Test2>());
		Test2 t25 = new Test2();
		t33.getT2().add(t25);
			t25.setId(15);
			t25.setIntVal(25);
			t25.setStrVal("1e");
		Test2 t26 = new Test2();
		t33.getT2().add(t26);
			t26.setId(16);
			t26.setIntVal(26);
			t26.setStrVal("1f");	
		em.persist(t33);
	}
	
	//Testowanie "Page" dla JOIN FETCH
	//@Test
	public void pageableFetchTest() {
		try {
			ntw.inNewTrans(()->fillTest4());			
			Pageable pageable = PageRequest.of(0, 2);
			Page<Test4> res = t1d.getPagedFetch(pageable);	
			int w = res.getContent().get(0).getT2().get(0).getIntVal();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	@Autowired
	Test1CustomDao t1cd; 
	
	//Testowanie "Page" dla JOIN FETCH
	//@Test
	public void repoInheritanceTest() {
		try {
			ntw.inNewTrans(()->fillTest1());			
			Iterable<Test1> lst = t1cd.findByIntVal1(11);
			Test1 obj = lst.iterator().next();
			int t = 0;
		}
		catch (Exception e) {
			int t = 0;
		}
	}
	
	private void streamQuery() {
		Stream<Test1> stream = t1cd.getAllStream();
		String result = stream
				.map(t->Integer.valueOf(t.getIntVal1()).toString())
				.collect(Collectors.joining(","));			
		int t = 0;
	}
	
	//Testowanie "Page" dla JOIN FETCH
	//@Test
	public void repoStreamTest() {
		try {
			int c = t1d.getCustomImplRef();
			ntw.inNewTrans(()->fillTest1());			
			String res;
			ntw.inNewTrans(()->streamQuery());	
		}
		catch (Exception e) {
			int t = 0;
		}
	}

}
