package com.example.demo.oneToOne;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

/*
 * Unidirectional
 */

@Data
@Entity
public class Person5_11 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
		/*
		 * UNIDIRECTIONAL
		 * Spowowduje powstanie dodatkowej kolumny z kluczem adresu
		 * Spowoduje powstanie FK
		 */
	@OneToOne(
				/*
				 * Ustala customow¹ nazwe kolumny z kluczem FK
				 */
			cascade=CascadeType.ALL,
				/*
				 * Fetch Lazy zadzia³a dla UNIDIRECTIONAL
				 */
			fetch=FetchType.LAZY)
	@JoinColumn(name = "addId")
	public Address5_11 address;
	
}
