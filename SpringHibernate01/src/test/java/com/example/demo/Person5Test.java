package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.oneToOne.Address5_11;
import com.example.demo.oneToOne.Address5_12;
import com.example.demo.oneToOne.Address5_12Dao;
import com.example.demo.oneToOne.Address5_13;
import com.example.demo.oneToOne.Address5_13Dao;
import com.example.demo.oneToOne.Address5_14;
import com.example.demo.oneToOne.Address5_14Dao;
import com.example.demo.oneToOne.Person5_11;
import com.example.demo.oneToOne.Person5_11Dao;
import com.example.demo.oneToOne.Person5_12;
import com.example.demo.oneToOne.Person5_12Dao;
import com.example.demo.oneToOne.Person5_13;
import com.example.demo.oneToOne.Person5_13Dao;
import com.example.demo.oneToOne.Person5_14;
import com.example.demo.oneToOne.Person5_14Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person5Test {
	
	@Autowired
	private Person5_11Dao repo51;
	@Autowired
	private Person5_12Dao repo52;
	@Autowired
	private Address5_12Dao arepo52;
	@Autowired
	private Person5_13Dao repo53;
	@Autowired
	private Address5_13Dao arepo53;
	@Autowired
	private Person5_14Dao repo54;
	@Autowired
	private Address5_14Dao arepo54;
	
	//@Test
	@Rollback(false) 
	public void person5_11() {
		Integer id = person5_11_Init();
			/* Dla EAGER
			  select
			        person5_11x0_.id as id1_18_0_,
			        person5_11x0_.add_id as add_id3_18_0_,
			        person5_11x0_.name as name2_18_0_,
			        address5_1x1_.id as id1_0_1_,
			        address5_1x1_.city as city2_0_1_ 
			    from
			        person5_11 person5_11x0_ 
			    left outer join
			        address5_11 address5_1x1_ 
			            on person5_11x0_.add_id=address5_1x1_.id 
			    where
			        person5_11x0_.id=?
			        
			  Dla LAZY      
			  select
			        person5_11x0_.id as id1_19_0_,
			        person5_11x0_.address_id as address_3_19_0_,
			        person5_11x0_.name as name2_19_0_ 
			    from
			        person5_11 person5_11x0_ 
			    where
			        person5_11x0_.id=?
			 */
		Person5_11 p = repo51.findById(id).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person5_11_Init() {
		repo51.deleteAll();
		Person5_11 p = new Person5_11();
		p.name = "os511";	
		Address5_11 a = new Address5_11();
		a.city = "city5_11";
		p.address = a;
		repo51.save(p);
		return p.getId();
	}
	
// --------------------------------------------------------------------------	
	
	//@Test
	@Rollback(false)
	public void person5_12() {
		Pair<Integer, Integer> ids = person5_12_Init();
			/* Dziwnie somplikowane zapytanie dla EAGER. £aczy 2 razy do tabeli z której 
			 * wyszed³, bo ten rodzaj bidirectional to w³aœciwie podwójny unidirectional
			 * select
		        person5_12x0_.id as id1_19_0_,
		        person5_12x0_.address_id as address_3_19_0_,
		        person5_12x0_.name as name2_19_0_,
		        address5_1x1_.id as id1_1_1_,
		        address5_1x1_.city as city2_1_1_,
		        address5_1x1_.person_id as person_i3_1_1_,
		        person5_12x2_.id as id1_19_2_,
		        person5_12x2_.address_id as address_3_19_2_,
		        person5_12x2_.name as name2_19_2_ 
		    from
		        person5_12 person5_12x0_ 
		    left outer join
		        address5_12 address5_1x1_ 
		            on person5_12x0_.address_id=address5_1x1_.id 
		    left outer join
		        person5_12 person5_12x2_ 
		            on address5_1x1_.person_id=person5_12x2_.id 
		    where
		        person5_12x0_.id=
		        
		    Dla LAZY
		    select
		        person5_12x0_.id as id1_19_0_,
		        person5_12x0_.address_id as address_3_19_0_,
		        person5_12x0_.name as name2_19_0_ 
		    from
		        person5_12 person5_12x0_ 
		    where
		        person5_12x0_.id=?    
		        */
		Person5_12 p = repo52.findById(ids.getFirst()).get();
			/*
			 * Zapytanie analigoczn do poprzedniego
			 */
		Address5_12 a = arepo52.findById(ids.getSecond()).get();
		int i = 0;		
	}
	
		@Transactional
		@Rollback(false)
		public Pair<Integer,Integer> person5_12_Init() {
			repo52.deleteAll();
			Person5_12 p = new Person5_12();
			p.name = "os512";	
			Address5_12 a = new Address5_12();
			a.city = "city5_12";
			a.person = p;
			p.address = a;
			repo52.save(p);
			return Pair.of(p.getId(), a.getId());
		}
		
// --------------------------------------------------------------------------		
		
		@Test
		@Rollback(false)
		public void person5_13() {
			Pair<Integer,Integer> ids = person5_13_Init();
				/*
				 * Poprawnie zbudowane zapytanie dla EAGER
				    select
				        person5_13x0_.id as id1_20_0_,
				        person5_13x0_.name as name2_20_0_,
				        address5_1x1_.id as id1_2_1_,
				        address5_1x1_.city as city2_2_1_,
				        address5_1x1_.cola as cola3_2_1_ 
				    from
				        person5_13 person5_13x0_ 
				    left outer join
				        address5_13 address5_1x1_ 
				            on person5_13x0_.id=address5_1x1_.cola 
				    where
				        person5_13x0_.id=?
				       
				   Dla LAZY wykonuje 2 zapytania
					select
					        person5_13x0_.id as id1_20_0_,
					        person5_13x0_.name as name2_20_0_ 
					    from
					        person5_13 person5_13x0_ 
					    where
					        person5_13x0_.id=?
					
					 select
					        address5_1x0_.id as id1_2_0_,
					        address5_1x0_.city as city2_2_0_,
					        address5_1x0_.person_id as person_i3_2_0_ 
					    from
					        address5_13 address5_1x0_ 
					    where
					        address5_1x0_.person_id=?
					 */
			Person5_13 p = repo53.findById(ids.getFirst()).get();
				/*
				 * Dla EAGER Zapytanie analogiczne do poprzedniego
				 * Dla LAZY wykonuje tylko jedno zapytanie
				    select
				        address5_1x0_.id as id1_2_0_,
				        address5_1x0_.city as city2_2_0_,
				        address5_1x0_.person_id as person_i3_2_0_ 
				    from
				        address5_13 address5_1x0_ 
				    where
				        address5_1x0_.id=?				 
				 */
			Address5_13 a = arepo53.findById(ids.getSecond()).get();
			int i = 0;		
		}
		
		@Transactional
		@Rollback(false)
		public  Pair<Integer,Integer> person5_13_Init() {
			repo53.deleteAll();
			Person5_13 p = new Person5_13();
			p.name = "os513";	
			Address5_13 a = new Address5_13();
			a.city = "city5_13";
			a.person = p;
			p.address = a;
			repo53.save(p);
			return Pair.of(p.getId(), a.getId());
		}
	
		
// --------------------------------------------------------------------------		
		
		//@Test
		@Rollback(false)
		public void person5_14() {
			Integer id = person5_14_Init();
				/*
				    select
				        person5_14x0_.id as id1_22_0_,
				        person5_14x0_.name as name2_22_0_ 
				    from
				        person5_14 person5_14x0_ 
				    where
				        person5_14x0_.id=?
				 */
			Person5_14 p = repo54.findById(id).get();
				/*
				 * Dla LAZY 
				    select
				        address5_1x0_.person_id as person_i2_3_0_,
				        address5_1x0_.city as city1_3_0_ 
				    from
				        address5_14 address5_1x0_ 
				    where
				        address5_1x0_.person_id=?
				        
				   Dla EAGER 
					    select
					        address5_1x0_.person_id as person_i2_3_0_,
					        address5_1x0_.city as city1_3_0_ 
					    from
					        address5_14 address5_1x0_ 
					    where
					        address5_1x0_.person_id=?
 
					    select
					        person5_14x0_.id as id1_22_0_,
					        person5_14x0_.name as name2_22_0_ 
					    from
					        person5_14 person5_14x0_ 
					    where
					        person5_14x0_.id=?
				 */
			Address5_14 a = arepo54.findById(id).get();
			int i = 0;		
		}
		
		@Transactional
		@Rollback(false)
		public Integer person5_14_Init() {
			repo54.deleteAll();
			Person5_14 p = new Person5_14();
			p.name = "os514";	
			Address5_14 a = new Address5_14();
			a.city = "city5_14";
			a.person = p;
			arepo54.save(a);
			return p.getId();
		}
		
	
}
