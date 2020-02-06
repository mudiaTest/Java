package hibernate01.embedded;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contact6_12 {

	public String envelope;
	
	public Contact6_12(String envelope) {
		super();
		this.envelope = envelope;
	}

	public Contact6_12() {
		super();
	}	
}
