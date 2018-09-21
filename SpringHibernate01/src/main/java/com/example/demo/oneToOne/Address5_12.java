package com.example.demo.oneToOne;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

	/*
	 * Bidirectional będące w rzeczywistości podwójnym unidirectional 
	 */
@Data
@Entity
public class Address5_12 {
	
	@Id
	@GeneratedValue
	Integer id;

	public String city;
	
		/*
		 * Spowoduje powstanie FK i kolumny
		 */
	@OneToOne(
				/*
				 * LAZY zadziała tak jak dla innego unidirectional
				 */
			fetch=FetchType.LAZY)
	public Person5_12 person;
	
	public Address5_12(String city, String street) {
		super();
		this.city = city;
	}

	public Address5_12() {
		super();
	}	
}
