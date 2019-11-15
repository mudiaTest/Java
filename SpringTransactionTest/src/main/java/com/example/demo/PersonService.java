package com.example.demo;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.inheritance.dto.Person;

@Service
public class PersonService {
	@Autowired
	EntityManager em;

	public void insertNoTransactionalPerson(Person p) {
		// em.persist(p);
		em.merge(p);
	}

	@Transactional
	public void insertPerson(Person p) {
		// em.persist(p);
		em.merge(p);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void insert1(Person p) {
		em.merge(p);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insert2(Person p) {
		em.merge(p);
	}

	@Autowired
	PersonService2 ps2;

	@Transactional
	public void insertPerson2() {
		int i = 0;
		ps2.insert1(new Person(4, "p21"));
		i = 0;
		ps2.insert2(new Person(4, "p22"));
		i = 0;
		ps2.insert1(new Person(4, "p23"));
		i = 0;
	}
}
