package com.example.demo.embedded;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address6_1 {

	public String city;
	
	public Address6_1(String city) {
		super();
		this.city = city;
	}

	public Address6_1() {
		super();
	}	
}
