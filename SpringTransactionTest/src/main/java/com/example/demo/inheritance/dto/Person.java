package com.example.demo.inheritance.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
//@NamedQueries(
//{ @NamedQuery(name="delAll", query="DELETE FROM Person")
// ,@NamedQuery(name="getById", query="SELECT p FROM Person w WHERE p.id=:id")
//})
public class Person {//extends Person_gen{

	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	public Person(String name) {
		super();
		this.name = name;
	}

	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Person() {
	}

}
