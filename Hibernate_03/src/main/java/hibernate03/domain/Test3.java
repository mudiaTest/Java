package my.com.pl.Hibernate_03.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Test3 {
	@Id
	private long id;
	private int intVal1;	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Test2 t2;
}
