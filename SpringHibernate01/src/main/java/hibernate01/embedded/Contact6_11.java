package hibernate01.embedded;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact6_11 {

	public String envelope;
	
	public Contact6_11(String envelope) {
		super();
		this.envelope = envelope;
	}

	public Contact6_11() {
		super();
	}	
}
