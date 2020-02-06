package hibernate01.inheritance;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact6_2 {

	public String envelope;
	
	public Contact6_2(String envelope) {
		super();
		this.envelope = envelope;
	}

	public Contact6_2() {
		super();
	}	
}
