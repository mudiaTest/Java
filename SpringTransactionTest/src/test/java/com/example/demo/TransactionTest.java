package com.example.demo;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.demo.inheritance.doa.Person_01Doa;
import com.example.demo.inheritance.dto.Person;

@EnableTransactionManagement
@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class TransactionTest {
	@Autowired
	Person_01Doa repo01;
	@Autowired
	EntityManager em;
	@Autowired
	PersonService ps;

	//bez znaczenia, bo wywo�ujemy to nie przez beana
	//@Transactional//(propagation=Propagation.REQUIRES_NEW)
	@Rollback(false)
	public Integer initPerson_01() {
//		Query q = em.createNamedQuery("delAll");
//		//q.executeUpdate();
//		Worker w1 = new Worker("p01", "tech");
//		//repo01.save(w1);
//		//ntw.inTrans(() -> em.persist(w1));
//		em.persist(w1);
//		return w1.getId();
		return null;
	}

	//bez znaczenia, bo wywo�ujemy to nie przez beana
	//@Transactional
	@Rollback(false)
	private void getPerson_01(Integer id) {
	}

	//@Test
	//@Rollback(false)
	@Transactional //bez znaczenia, bo wywo�ujemy to nie przez beana
	public void testNoEntityManager() {
		Person p = new Person(1, "p1");
		em.merge(p);
		//em.persist(p); //padnie z błędem "No EntityManager with actual transaction available for current thread"
	}

	//@Test
	//@Rollback(false)
	public void insertByServiceNoTransactional() {
		Person p = new Person(1, "p1");
		ps.insertNoTransactionalPerson(p); //padnie z błędem "No EntityManager with actual transaction available for current thread"

	}

	//@Test
	//@Rollback(false)
	public void insertByServiceWithTransactional() {
		Person p = new Person(1, "p1");
		ps.insertPerson(p);
	}

	@Autowired
	TransactionTemplate tt;

	//@Test
	public void testTransactionManager() {
		Person p = new Person(1, "p2");
		tt.execute(
			status ->{
				em.merge(p);
				return null;
			}
		);
	}





	private void resetPerson() {
		Person p = new Person(1, "p1");
		tt.execute(
				status ->{
					em.merge(p);
					return null;
				}
				);
	}

	@Transactional
	private void insertPersonInCurrentTrans(Person p) {
		em.merge(p);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void insertPersonInNewTrans(Person p) {
		em.merge(p);
	}

	@Autowired
	private PlatformTransactionManager tm;

	private void insertPersonInNewTrans2(Person p) {
		TransactionTemplate localTT = new TransactionTemplate();
		localTT.setTransactionManager(tm);
		localTT.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		localTT.execute(
				status ->{
					Person p2 = em.find(Person.class, 1);
		em.merge(p);
		return null;
				});
	}


	//Testowanie nowej transakcji wewnątrz innej transakcji
	//Ogólnie można mieszać @Transactional z TransactionTemplate pod warunkiem, że PlatformTransactionManager będzie to samo
	//Nawet przymieszaniu propagacja bedzie działać tak, jakby było wykorzystanie tylko jednego mechanizmu
	//@Test
	public void testTransactionTemplate2() {
		try {

			resetPerson();
			tt.execute(
					status ->{
			int i = 0;
			//@Transactional - zmiana nie będzie widoczna w DB managerze
			insertPersonInCurrentTrans(new Person(1, "p2"));
			i = 0;
			//@Transactional(propagation = Propagation.REQUIRES_NEW) - zmiana BĘDZIE widzoczna w DB managerze
			ps.insert2(new Person(1, "p3"));
			i = 0;
			//TransactionTemplate PROPAGATION_REQUIRES_NEW - zmiana BĘDZIE widzoczna w DB managerze
			//Nowa transakcja powoduje zawieszenie aktualnej i powrów po zakończeniu nowej
			//! Ważne, że wewnętrzny TransactionTemplate jest utworzony nad tym samym PlatformTransactionManager
			//!! WAŻNE - ten nowy stan nie będzie widoczny dla zewnętrznej transakcji i na odwrót !!
			//  Wewnętrzna transakcja na stan z przed rozpoczęcia zewnętrznej!
			//  Może się więc zdarzyć, że wewnęttrzna spowoduje błedy i rollbacj zewnętrznej lub odczyt "złych" (nieintuicyjnych) danych.
			insertPersonInNewTrans2(new Person(1, "p4"));
			i = 0;
			// ! p.name = "p3", bo zmiana na "p4" zostałą wykonana poza aktualną transakcją !
			Person p = em.find(Person.class, 1);
			i = 0;
			//@Transactional - zmiana nie będzie widoczna w DB managerze
			insertPersonInCurrentTrans(new Person(1, "p5"));
			i = 0;
			//Wycofa wszelkie zmiany PO ostatnim skutecznym commit transakcji wewnętrznej
			status.setRollbackOnly();
			return null;});
			int i = 0;
		}
		catch(Exception e) {
			int t = 0;
		}
	}
}
