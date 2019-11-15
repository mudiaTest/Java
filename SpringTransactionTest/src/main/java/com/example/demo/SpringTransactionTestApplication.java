package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.inheritance.dto.Person;

@SpringBootApplication
public class SpringTransactionTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionTestApplication.class, args);
	}

	@Autowired
	PersonService ps;

	@Transactional
	// @PostConstruct
	public void post() {
		System.out.println("START");
		Person p = new Person(2, "p1");

//    //padnie z braku transakcji
//    //ps.insertNoTransactionalPerson(new Person(2, "p1"));
//
//    //Zadziała
//    ps.insertPerson(new Person(3, "p1"));
//
		ps.insertPerson2();

//    int i = 0;
//    ps.insert1(new Person(4, "p11"));
//    i = 0;
//    ps.insert2(new Person(4, "p12"));
//    i = 0;
//    ps.insert1(new Person(4, "p13"));
//    i = 0;
	}
}
