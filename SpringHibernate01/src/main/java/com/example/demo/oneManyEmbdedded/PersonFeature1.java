package com.example.demo.oneManyEmbdedded;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
//@Entity
@Embeddable
public class PersonFeature1 {
	//@Id
	//@GeneratedValue
	//Integer id;
	String name;
	String value;
	
	public PersonFeature1(String name, String value){
		this.name = name;
		this.value = value;
	}

	public PersonFeature1() {
		super();
	}
	
}
