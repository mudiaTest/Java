package hibernate01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import hibernate01.id.Person4Id;
import hibernate01.id.Person4_11;
import hibernate01.id.Person4_11Dao;
import hibernate01.id.Person4_12;
import hibernate01.id.Person4_12Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person4Test {
	
	@Autowired
	private Person4_11Dao repo41;
	@Autowired
	private Person4_12Dao repo42;
	
	@Test
	@Transactional
	@Rollback(false)
	public void person4_11() {
		repo41.deleteAll();
		Person4_11 p = new Person4_11();
			/*
			 * @GeneratedValue przes³oni wartoœc podstawion¹ do key1
			 */
		p.key1 = Integer.valueOf(111);
		p.key2 = "o";
		p.name = "os411";		
		repo41.save(p);
		int t =0;
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void person4_12() {
		repo42.deleteAll();
		Person4_12 p = new Person4_12();		
		p.key = new Person4Id(2, "y");
		p.name = "os42";		
		repo42.save(p);
		int t =0;
	}
}
