package hibernate03.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SubClass6 {
	@Id
	private Long id;
	private int subIntVal1;
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "parent")
	private Set<SubClass61> lista = new HashSet<>();
	
	public SubClass6() {}
	
	public SubClass6(long id, int subIntVal1) {
		super();
		this.id = id;
		this.subIntVal1 = subIntVal1;
	}
	private String subStVal1;
}
