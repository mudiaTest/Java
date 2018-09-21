package com.example.demo.inheritance;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address6_2 {

	public String city;
	
	public Address6_2(String city) {
		super();
		this.city = city;
	}

	public Address6_2() {
		super();
	}	
}
