package com.example.demo.oneToOne;

import javax.persistence.CascadeType;
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
public class Person5_12 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
		/*
		 * BIDIRECTIONAL
		 * Spowowduje powstanie dodatkowej kolumny z kluczem adresu  
		 */
	@OneToOne(cascade=CascadeType.ALL,
				/*
				 * LAZY zadziała tak jak dla innego unidirectional
				 */
			fetch=FetchType.LAZY)
	public Address5_12 address;
	
}
