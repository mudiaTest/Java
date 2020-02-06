package hibernate01.select;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address7_1 {

	public String city;
	
	public Address7_1(String city) {
		super();
		this.city = city;
	}

	public Address7_1() {
		super();
	}	
}
