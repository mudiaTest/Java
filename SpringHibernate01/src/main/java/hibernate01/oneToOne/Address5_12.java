package hibernate01.oneToOne;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

	/*
	 * Bidirectional bêd¹ce w rzeczywistoœci podwójnym unidirectional 
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
				 * LAZY zadzia³a tak jak dla innego unidirectional
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
