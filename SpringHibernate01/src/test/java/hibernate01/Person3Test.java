package hibernate01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import hibernate01.id.Person3_1;
import hibernate01.id.Person3_1Dao;
import hibernate01.id.Person3_2;
import hibernate01.id.Person3_2Dao;
import hibernate01.id.Person3_3;
import hibernate01.id.Person3_3Dao;
import hibernate01.id.Person3_41;
import hibernate01.id.Person3_41Dao;
import hibernate01.id.Person3_42;
import hibernate01.id.Person3_42Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person3Test {
	
	@Autowired
	private Person3_1Dao repo1;
	@Autowired
	private Person3_2Dao repo2;
	@Autowired
	private Person3_3Dao repo3;
	@Autowired
	private Person3_41Dao repo41;
	@Autowired
	private Person3_42Dao repo42;
	
	//@Test
	@Rollback(false)
	public void person3_1() {
		repo1.deleteAll();
		Person3_1 p = new Person3_1();
		p.name = "os1";		
		repo1.save(p);
		for(Person3_1 p2 : repo1.findAll()){
			int t = 0;
		}
	}
	
	//@Test
	@Rollback(false)
	public void person3_2() {
		repo2.deleteAll();
		Person3_2 p = new Person3_2();		
		p.name = "os2";		
		repo2.save(p);
		for(Person3_2 p2 : repo2.findAll()){
			String h = p2.key1;
		}
	}
	
	//@Test
	@Rollback(false)
	public void person3_3() {
		repo3.deleteAll();
		Person3_3 p = new Person3_3();		
		p.name = "os3";		
		repo3.save(p);
		for(Person3_3 p2 : repo3.findAll()){
			Long h = p2.key1;
		}
	}
	
	@Test
	@Rollback(false)
	public void person3_4() {
		repo41.deleteAll();
		Person3_41 p41 = new Person3_41();		
		p41.name = "os3";		
		repo41.save(p41);
		for(Person3_41 p2 : repo41.findAll()){
			Long h = p2.key1;
		}
		
		repo42.deleteAll();
		Person3_42 p42 = new Person3_42();		
		p42.name = "os3";		
		repo42.save(p42);
		for(Person3_42 p2 : repo42.findAll()){
			Long h = p2.key1;
		}
	}
}
