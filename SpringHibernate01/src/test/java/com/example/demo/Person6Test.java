package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.embedded.Address6_1;
import com.example.demo.embedded.HomeData6_1;
import com.example.demo.embedded.Person6_1;
import com.example.demo.embedded.Person6_1Dao;
import com.example.demo.inheritance.Address6_2;
import com.example.demo.inheritance.Contact6_2;
import com.example.demo.inheritance.entityInheritance.Person6_21;
import com.example.demo.inheritance.entityInheritance.Person6_21Dao;
import com.example.demo.inheritance.entityInheritance.Person6_22;
import com.example.demo.inheritance.entityInheritance.Person6_22Dao;
import com.example.demo.inheritance.entityInheritance.Person6_2Dao;
import com.example.demo.inheritance.mappedSuperclass.Person6_231;
import com.example.demo.inheritance.mappedSuperclass.Person6_231Dao;
import com.example.demo.inheritance.mappedSuperclass.Person6_232;
import com.example.demo.inheritance.mappedSuperclass.Person6_232Dao;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_421;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_4211;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_4211Dao;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_4212;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_4212Dao;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_421Dao;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_422;
import com.example.demo.inheritance.mappingStrategy.joined.Person6_422Dao;
import com.example.demo.inheritance.mappingStrategy.singleTable.Person6_411;
import com.example.demo.inheritance.mappingStrategy.singleTable.Person6_411Dao;
import com.example.demo.inheritance.mappingStrategy.singleTable.Person6_412;
import com.example.demo.inheritance.mappingStrategy.singleTable.Person6_412Dao;
import com.example.demo.inheritance.mappingStrategy.tablePerClass.Person6_431;
import com.example.demo.inheritance.mappingStrategy.tablePerClass.Person6_431Dao;
import com.example.demo.inheritance.mappingStrategy.tablePerClass.Person6_432;
import com.example.demo.inheritance.mappingStrategy.tablePerClass.Person6_432Dao;
import com.example.demo.oneToOne.Address5_11;
import com.example.demo.oneToOne.Address5_12Dao;
import com.example.demo.oneToOne.Address5_13Dao;
import com.example.demo.oneToOne.Address5_14Dao;
import com.example.demo.oneToOne.Person5_11;
import com.example.demo.oneToOne.Person5_11Dao;
import com.example.demo.oneToOne.Person5_12Dao;
import com.example.demo.oneToOne.Person5_13Dao;
import com.example.demo.oneToOne.Person5_14Dao;

@RunWith(SpringRunner.class)
@SpringBootTest
	/*
	 * Pozwala Springowi na lepsz¹ kontrolê nad transakcjami lub coœ takiego.
	 * To jest trochê szegó³ów: http://www.baeldung.com/transaction-configuration-with-jpa-and-spring
	 * 
	 * are responsible for registering the necessary Spring components that power annotation-driven 
	 * transaction management, such as the TransactionInterceptor and the proxy- or AspectJ-based 
	 * advice that weave the interceptor into the call stack when JdbcFooRepository's @Transactional 
	 * methods are invoked.
	 */
@EnableTransactionManagement
public class Person6Test {
	
	@Autowired
	private Person6_1Dao repo6_1;
	@Autowired
	private Person6_2Dao repo6_2;
	@Autowired
	private Person6_21Dao repo6_21;
	@Autowired
	private Person6_22Dao repo6_22;


	//@Test
	@Rollback(false) 
	public void person6_1() {
		Integer id = person6_1Init();
		/*
		 * Dla roomMates LAZY
			select
		        person6x0_.id as id1_23_0_,
		        person6x0_.city as city2_23_0_,
		        person6x0_.owner_id as owner_id4_23_0_,
		        person6x0_.name as name3_23_0_,
		        person6x1_.id as id1_23_1_,
		        person6x1_.city as city2_23_1_,
		        person6x1_.owner_id as owner_id4_23_1_,
		        person6x1_.name as name3_23_1_ 
		    from
		        person6 person6x0_ 
		    left outer join
		        person6 person6x1_ 
		            on person6x0_.owner_id=person6x1_.id 
		    where
		        person6x0_.id=?		 
		 
		   Dla roomMates EAGER
		   
 				Wype³nia obiekt i ownera i pobiera idroommates
 				Potem pobiera ka¿dego roommate i owner osobno, bo to te¿ Person6 i sami moga miec owner i roommate
 				Rozwi¹zanie trochê do d..y :(
		    select
		        person6x0_.id as id1_23_0_,
		        person6x0_.city as city2_23_0_,
		        person6x0_.owner_id as owner_id4_23_0_,
		        person6x0_.name as name3_23_0_,
		        person6x1_.id as id1_23_1_,
		        person6x1_.city as city2_23_1_,
		        person6x1_.owner_id as owner_id4_23_1_,
		        person6x1_.name as name3_23_1_,
		        roommates2_.person6_id as person1_24_2_,
		        person6x3_.id as room_mat2_24_2_,
		        person6x3_.id as id1_23_3_,
		        person6x3_.city as city2_23_3_,
		        person6x3_.owner_id as owner_id4_23_3_,
		        person6x3_.name as name3_23_3_ 
		    from
		        person6 person6x0_ 
		    left outer join
		        person6 person6x1_ 
		            on person6x0_.owner_id=person6x1_.id 
		    left outer join
		        person6_room_mates roommates2_ 
		            on person6x1_.id=roommates2_.person6_id 
		    left outer join
		        person6 person6x3_ 
		            on roommates2_.room_mates_id=person6x3_.id 
		    where
		        person6x0_.id=?
		
		    select
		        roommates0_.person6_id as person1_24_0_,
		        roommates0_.room_mates_id as room_mat2_24_0_,
		        person6x1_.id as id1_23_1_,
		        person6x1_.city as city2_23_1_,
		        person6x1_.owner_id as owner_id4_23_1_,
		        person6x1_.name as name3_23_1_,
		        person6x2_.id as id1_23_2_,
		        person6x2_.city as city2_23_2_,
		        person6x2_.owner_id as owner_id4_23_2_,
		        person6x2_.name as name3_23_2_ 
		    from
		        person6_room_mates roommates0_ 
		    inner join
		        person6 person6x1_ 
		            on roommates0_.room_mates_id=person6x1_.id 
		    left outer join
		        person6 person6x2_ 
		            on person6x1_.owner_id=person6x2_.id 
		    where
		        roommates0_.person6_id=?
		 
		    select
		        roommates0_.person6_id as person1_24_0_,
		        roommates0_.room_mates_id as room_mat2_24_0_,
		        person6x1_.id as id1_23_1_,
		        person6x1_.city as city2_23_1_,
		        person6x1_.owner_id as owner_id4_23_1_,
		        person6x1_.name as name3_23_1_,
		        person6x2_.id as id1_23_2_,
		        person6x2_.city as city2_23_2_,
		        person6x2_.owner_id as owner_id4_23_2_,
		        person6x2_.name as name3_23_2_ 
		    from
		        person6_room_mates roommates0_ 
		    inner join
		        person6 person6x1_ 
		            on roommates0_.room_mates_id=person6x1_.id 
		    left outer join
		        person6 person6x2_ 
		            on person6x1_.owner_id=person6x2_.id 
		    where
		        roommates0_.person6_id=?
		 
		    select
		        roommates0_.person6_id as person1_24_0_,
		        roommates0_.room_mates_id as room_mat2_24_0_,
		        person6x1_.id as id1_23_1_,
		        person6x1_.city as city2_23_1_,
		        person6x1_.owner_id as owner_id4_23_1_,
		        person6x1_.name as name3_23_1_,
		        person6x2_.id as id1_23_2_,
		        person6x2_.city as city2_23_2_,
		        person6x2_.owner_id as owner_id4_23_2_,
		        person6x2_.name as name3_23_2_ 
		    from
		        person6_room_mates roommates0_ 
		    inner join
		        person6 person6x1_ 
		            on roommates0_.room_mates_id=person6x1_.id 
		    left outer join
		        person6 person6x2_ 
		            on person6x1_.owner_id=person6x2_.id 
		    where
		        roommates0_.person6_id=?		 
		 */
		Person6_1 p = repo6_1.findById(id).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person6_1Init() {
		repo6_1.deleteAll();
		Person6_1 p = new Person6_1();
		p.name = "os6";	
		
		HomeData6_1 hd = new HomeData6_1();
					
		Address6_1 a = new Address6_1();
		a.city = "city6_1";
		hd.setAddr(a);
		
		Person6_1 owner = new Person6_1();
		owner.name = "owner6";
		hd.setOwner(owner);
		
		Person6_1 rm1 = new Person6_1();
		rm1.name = "room mate 1";
		Person6_1 rm2 = new Person6_1();
		rm2.name = "room mate 2";
		Set<Person6_1> roommates = new HashSet<>();
		roommates.add(rm1);
		roommates.add(rm2);
		hd.setRoomMates(roommates);		
		
		p.setHomeData(hd);
		repo6_1.save(p);
		return p.getId();
	}	
	
// ----------------------------------------------------------------------------------------	
	
	//@Test
	@Rollback(false) 
	public void person6_2() {
		Integer id = person6_21Init();
		Person6_21 p21 = repo6_21.findById(id).get();
		 id = person6_22Init();
		Person6_22 p22 = repo6_22.findById(id).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person6_21Init() {
		repo6_21.deleteAll();
		Person6_21 p = new Person6_21();
		p.name = "os621";	
		
		Address6_2 addr = new Address6_2();
		p.setAddr(addr);
		
		repo6_21.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_22Init() {
		repo6_22.deleteAll();
		Person6_22 p = new Person6_22();
		p.name = "os622";	
		
		Contact6_2 con = new Contact6_2();
		p.setCon(con);
		
		repo6_22.save(p);
		return p.getId();
	}	
	
// ----------------------------------------------------------------------------------------	
	@Autowired
	private Person6_231Dao repo6_31;
	@Autowired
	private Person6_232Dao repo6_32;
	
	@Test
	@Rollback(false) 
	public void person6_3() {
		Integer id = person6_31Init();
		Person6_231 p31 = repo6_31.findById(id).get();
		id = person6_32Init();
		Person6_232 p32 = repo6_32.findById(id).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person6_31Init() {
		repo6_31.deleteAll();
		Person6_231 p = new Person6_231();
		p.name = "os631";	
		
		Address6_2 addr = new Address6_2();
		p.setAddr(addr);
		
		repo6_31.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_32Init() {
		repo6_32.deleteAll();
		Person6_232 p = new Person6_232();
		p.name = "os622";	
		
		Contact6_2 con = new Contact6_2();
		p.setCon(con);
		
		repo6_32.save(p);
		return p.getId();
	}	
	
// ----------------------------------------------------------------------------------------	
	@Autowired
	private Person6_411Dao repo6_411;
	@Autowired
	private Person6_412Dao repo6_412;
	
	//@Test
	@Rollback(false) 
	public void person6_41() {
		Integer id = person6_411Init();
		Person6_411 p31 = repo6_411.findById(id).get();
		id = person6_412Init();
		Person6_412 p32 = repo6_412.findById(id).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person6_411Init() {
		repo6_411.deleteAll();
		Person6_411 p = new Person6_411();
		p.name = "os631";	
		
		Address6_2 addr = new Address6_2();
		p.setAddr(addr);
		
		repo6_411.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_412Init() {
		repo6_412.deleteAll();
		Person6_412 p = new Person6_412();
		p.name = "os622";	
		
		Contact6_2 con = new Contact6_2();
		p.setCon(con);
		
		repo6_412.save(p);
		return p.getId();
	}	
	
// ----------------------------------------------------------------------------------------	
	@Autowired
	private Person6_421Dao repo6_421;
	@Autowired
	private Person6_422Dao repo6_422;
	@Autowired
	private Person6_4211Dao repo6_4211;
	@Autowired
	private Person6_4212Dao repo6_4212;
	
	//@Test
	@Rollback(false) 
	public void person6_42() {
		Integer id = person6_421Init();
		/*
		    select
		        person6_42x0_.id as id1_33_0_,
		        person6_42x0_1_.name as name2_33_0_,
		        person6_42x0_.city as city1_29_0_,
		        person6_42x0_2_.callname as callname1_30_0_,
		        person6_42x0_3_.color as color1_31_0_,
		        case 
		            when person6_42x0_2_.id is not null then 2 
		            when person6_42x0_3_.id is not null then 4 
		            when person6_42x0_.id is not null then 1 
		        end as clazz_0_ 
		    from
		        person6_421 person6_42x0_ 
		    inner join
		        person6_42gen person6_42x0_1_ 
		            on person6_42x0_.id=person6_42x0_1_.id 
		    left outer join
		        person6_4211 person6_42x0_2_ 
		            on person6_42x0_.id=person6_42x0_2_.id 
		    left outer join
		        person6_4212 person6_42x0_3_ 
		            on person6_42x0_.id=person6_42x0_3_.id 
		    where
		        person6_42x0_.id=?		 
		 */
		Person6_421 p41 = repo6_421.findById(id).get();
		
		id = person6_4211Init();
		/*
	        select
	            person6_42x0_.id as id1_33_,
	            person6_42x0_2_.name as name2_33_,
	            person6_42x0_1_.city as city1_29_,
	            person6_42x0_.callname as callname1_30_ 
	        from
	            person6_4211 person6_42x0_ 
	        inner join
	            person6_421 person6_42x0_1_ 
	                on person6_42x0_.id=person6_42x0_1_.id 
	        inner join
	            person6_42gen person6_42x0_2_ 
	                on person6_42x0_.id=person6_42x0_2_.id		 
		 */
		Person6_4211 p411 = repo6_4211.findById(id).get();
		id = person6_4212Init();
		/*
		    select
		        person6_42x0_.id as id1_33_0_,
		        person6_42x0_2_.name as name2_33_0_,
		        person6_42x0_1_.city as city1_29_0_,
		        person6_42x0_.color as color1_31_0_ 
		    from
		        person6_4212 person6_42x0_ 
		    inner join
		        person6_421 person6_42x0_1_ 
		            on person6_42x0_.id=person6_42x0_1_.id 
		    inner join
		        person6_42gen person6_42x0_2_ 
		            on person6_42x0_.id=person6_42x0_2_.id 
		    where
		        person6_42x0_.id=?		 
		 */
		Person6_4212 p412 = repo6_4212.findById(id).get();
		
		id = person6_422Init();
		/*
		    select
		        person6_42x0_.id as id1_33_0_,
		        person6_42x0_1_.name as name2_33_0_,
		        person6_42x0_.envelope as envelope1_32_0_ 
		    from
		        person6_422 person6_42x0_ 
		    inner join
		        person6_42gen person6_42x0_1_ 
		            on person6_42x0_.id=person6_42x0_1_.id 
		    where
		        person6_42x0_.id=?		 
		 */
		Person6_422 p42 = repo6_422.findById(id).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person6_421Init() {
		repo6_421.deleteAll();
		Person6_421 p = new Person6_421();
		p.name = "os6421";	
		
		Address6_2 addr = new Address6_2();
		p.setAddr(addr);
		
		repo6_421.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_4211Init() {
		repo6_4211.deleteAll();
		Person6_4211 p = new Person6_4211();
		p.name = "os64211";	
		p.callname = "terminator";
		
		repo6_4211.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_4212Init() {
		repo6_4212.deleteAll();
		Person6_4212 p = new Person6_4212();
		p.name = "os64212";	
		p.color = "blonde";
		
		repo6_4212.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_422Init() {
		repo6_422.deleteAll();
		Person6_422 p = new Person6_422();
		p.name = "os6422";	
		
		Contact6_2 con = new Contact6_2();
		p.setCon(con);
		
		repo6_422.save(p);
		return p.getId();
	}	
	
// ----------------------------------------------------------------------------------------	
	@Autowired
	private Person6_431Dao repo6_431;
	@Autowired
	private Person6_432Dao repo6_432;
	
	private Integer id6431;
	private Integer id6432;
	
	@Test
	@Rollback(false) 
	public void person6_43() {
		id6431 = person6_431Init();
		/*
		    select
		        person6_43x0_.id as id1_36_0_,
		        person6_43x0_.name as name2_36_0_,
		        person6_43x0_.city as city1_34_0_ 
		    from
		        person6_431 person6_43x0_ 
		    where
		        person6_43x0_.id=?		 
		 */
		Person6_431 p31 = repo6_431.findById(id6431).get();
		id6432 = person6_432Init();
		/*
		    select
		        person6_43x0_.id as id1_36_0_,
		        person6_43x0_.name as name2_36_0_,
		        person6_43x0_.envelope as envelope1_35_0_ 
		    from
		        person6_432 person6_43x0_ 
		    where
		        person6_43x0_.id=?		 
		 */
		Person6_432 p32 = repo6_432.findById(id6432).get();
		int i = 0;
	}
	
	@Transactional
	@Rollback(false)
	public Integer person6_431Init() {
		repo6_431.deleteAll();
		Person6_431 p = new Person6_431();
		p.name = "os6431";	
		
		Address6_2 addr = new Address6_2();
		p.setAddr(addr);
		
		repo6_431.save(p);
		return p.getId();
	}	
	
	@Transactional
	@Rollback(false)
	public Integer person6_432Init() {
		repo6_432.deleteAll();
		Person6_432 p = new Person6_432();
		p.name = "os6432";	
		
		Contact6_2 con = new Contact6_2();
		p.setCon(con);
		
		repo6_432.save(p);
		return p.getId();
	}
	
		/*
		 * Container Managed Persistence Context
		 */
	@PersistenceContext
	EntityManager em2;
		/*
		 * Prawdopodobnie pobiera EM za pomoc¹ @PersistenceContext, bo 
		 * wspó³dziel¹ informacje i mo¿na ich u¿ywaæ zamiennie
		 */
	@Autowired
	private EntityManager em;
	
	@Autowired
	EntityManagerFactory emf;
	
		/*
		 * Application Managed Persistence Context
		 * ka¿de EntityManager bêdzie mia³o inny persistanse context
		 */
	@PersistenceUnit
	EntityManagerFactory emf2;
	
	/*
	 * brak takiego beana
	 */
//	@Resource
//	UserTransaction ut;
	
	@Autowired
	private TransactionWrapper wrapper;
	
	@Test
	//@Transactional
	@Rollback(false)
	public void entityTest() {
		id6431 = person6_431Init();
		Person6_431 p = em.find(Person6_431.class, id6431);
		Person6_431 p2 = em2.find(Person6_431.class, id6431);
		Integer id6431_2 = person6_431EmAdd2();
		EntityManager em3 = emf2.createEntityManager();
		Person6_431 p3 = em2.find(Person6_431.class, id6431_2);
		
		int i = 0;
	}
	
	
	
	@Rollback(false)
	private Integer person6_431EmAdd() {
		repo6_431.deleteAll();
		Person6_431 p = new Person6_431();
		p.name = "os6431_2";
		EntityManager em31 = emf.createEntityManager();	
		//em32.joinTransaction();
		EntityManager em41 = emf2.createEntityManager();
		//em42.joinTransaction();
		
		wrapper.withTransaction(() -> em.persist(p));
        //String result = wrapper.withTransaction(() -> em.coœCoOddajeWynik(p));

			/*
			 * em i em2 pobrane za pomoc¹ @Autowired i @PersistenceContext
			 */
		Person6_431 p1 = em.find(Person6_431.class, p.id);
		Person6_431 p2 = em2.find(Person6_431.class, p.id);
		Person6_431 p31 = em31.find(Person6_431.class, p.id);
		Person6_431 p41 = em41.find(Person6_431.class, p.id);
		wrapper.withTransaction(() -> {
				/*
				 * em i em2 wspó³dziel¹ persistance context 
				 */
			Person6_431 px = em.find(Person6_431.class, p.id); 
			em2.remove(px);});
		em.flush();
			/*
			 * null - bo usun¹³ kontekst zwi¹zany z em
			 */
		Person6_431 p1e = em.find(Person6_431.class, p.id);
			/*
			 * null - bo usun¹³ kontekst zwi¹zany z em2
			 */
		Person6_431 p2e = em2.find(Person6_431.class, p.id);
			/*
			 * !null - bo to inny kontekt
			 */
		Person6_431 p31e = em31.find(Person6_431.class, p.id);
			/*
			 * !null - bo to inny kontekt
			 */
		Person6_431 p41e = em41.find(Person6_431.class, p.id);
		//em.flush();
		return p.id;
	}
	
	@Transactional
	@Rollback(false)
	private Integer person6_431EmAdd2() {
		repo6_431.deleteAll();
		Person6_431 p = new Person6_431();
		p.name = "os6431_2";
			/*
			 * em31 bedzie mia³o 
			 */
		EntityManager em31 = emf.createEntityManager();	
//		Person6_431 p31b = em31.find(Person6_431.class, p.id);
			/*
			 * U¿ycie REQUIRES_NEW spowoduje natychmiastowy zapis do bazy danych 
			 * Nie ma wp³ywy na inne persistance context ni¿ 
			 */
//		wrapper.withNewTransaction(() -> em.persist(p));
		wrapper.withTransaction(() -> repo6_431.save(p));
		Person6_431 p1 = em.find(Person6_431.class, p.id);
		Person6_431 p2 = em2.find(Person6_431.class, p.id);
			/*
			 * To spowowduje pobranie danych istniej¹cych w bazie (withNewTransaction)
			 */
		Person6_431 p31 = em31.find(Person6_431.class, p.id);
			/*
			 * U¿ycie REQUIRES_NEW spowoduje natychmiastowe usuniêcie z bazy danych
			 */
		wrapper.withNewTransaction(() -> {
			Person6_431 px = em.find(Person6_431.class, p.id); 
			em2.remove(px);});
		EntityManager em32 = emf.createEntityManager();	
		Person6_431 p1e = em.find(Person6_431.class, p.id);
		Person6_431 p2e = em2.find(Person6_431.class, p.id);
			/*
			 * Z kontekstu em31 nie zosta³ usuniêty p, wiêc zostanie pobrany z cache, pomimo, ze w bazie ju¿ go nie ma.
			 */
		Person6_431 p31e = em31.find(Person6_431.class, p.id);
			/*
			 * powoduje odœwie¿enie stanu p31e. 
			 * Problem jest gdy tego ju¿ w bazie nie bêdzie, bo dostaniemy 
			 * EntityNotFoundException "No row with the given identifier exists"
			 * Mo¿na opakowaæ to w funjcjê przechwytuj¹c¹ wyj¹tek
			 */
		//em31.refresh(p31e);
				/*
				 * detach spowowduje usuniêcie z PC tego obiektu i zmusi EM do podrania z DB, zamiast z cache
				 */
		em31.detach(p31e);
			/*
			 * Spowoduje detach dla wszystkich obiektów 
			 */
		//em31.clear();
			/*
			 * em31 JU¯ nie ma w cache obiektu p, wiêc p zostanie pobrany z bazy, gdzie ju¿ go nie ma
			 */
		Person6_431 p31e2 = em31.find(Person6_431.class, p.id);		
			/*
			 * em32 JESZCZE nie ma w cache obiektu p, wiêc p zostanie pobrany z bazy, gdzie ju¿ go nie ma
			 */
		Person6_431 p32e = em32.find(Person6_431.class, p.id);
		//em.flush();
		return p.id;
	}
		
}
