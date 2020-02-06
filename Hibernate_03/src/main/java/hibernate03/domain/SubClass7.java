package my.com.pl.Hibernate_03.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SubClass7 {
	@Id
	Integer id;
	private int subIntVal1;
	private String subStVal1;
	@Version
	private int ver;
	@ManyToOne
	@JoinColumn(name="t71id")
	private Test71 t71;
}
