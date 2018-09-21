package com.example.demo.inheritance.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import lombok.Getter;

@Entity
@Getter
//@Inheritance(strategy=InheritanceType.JOINED)
public class Person_gen {

	@Id
	@GeneratedValue
	private Integer id;

	public Person_gen() {
	}	
	
	
}
