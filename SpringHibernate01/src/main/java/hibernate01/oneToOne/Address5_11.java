package hibernate01.oneToOne;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Address5_11 {
	/*
	 * Nie ma ¿adnego foreign key
	 */
	
	@Id
	@GeneratedValue
	Long id;

	public String city;
	
	public Address5_11(String city, String street) {
		super();
		this.city = city;
	}

	public Address5_11() {
		super();
	}	
}
