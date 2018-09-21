package com.example.demo.oneToOne;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Address5_14 {
	
		/*
		 * Wartoœci id zostan¹ przepisane z person_id, wiêc //@GeneratedValue jest niepotrzebne
		 */
	@Id
	//@GeneratedValue
	Integer id;

	public String city;
	
		/*
		 * Spowoduje powstanie FK i kolumny CHYBA ¯E U¯YJEMY @MapsId
		 */
	@OneToOne(
				/*
				 * Dla strony parenta LAZY dzia³a
				 */
			fetch=FetchType.LAZY)
		/*
		 * Ustawia, ¿e w SQL nie bedzie ju¿ kolumny id, ale Pk i FK bed¹ u¿ywa³y person_id
		 */
	@MapsId
	public Person5_14 person;
	
	public Address5_14(String city, String street) {
		super();
		this.city = city;
	}

	public Address5_14() {
		super();
	}	
}
