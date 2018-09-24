package my.com.pl.Hibernate_03.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import lombok.Data;

@Data
@Entity

@NamedStoredProcedureQueries(value = {
@NamedStoredProcedureQuery(
		name = "t61", 
		procedureName = "p61", 
		parameters = {
			@StoredProcedureParameter(name = "i", mode = ParameterMode.IN, type = Integer.class) 
		}
)
,
@NamedStoredProcedureQuery(
		name = "t62", 
		procedureName = "p62", 
		parameters = {
			@StoredProcedureParameter(name = "i", mode = ParameterMode.IN, type = Integer.class), 
			@StoredProcedureParameter(name = "o", mode = ParameterMode.OUT, type = Integer.class) 
		}
)
})

public class Test1 {
	@Id
	private long id;
	private int intVal1;
	@Embedded
	private SubClass1 so1;
	
	@DomainEvents
	Collection<Object> domainEvents() {
	    List<Object> result = new ArrayList<Object>();
	    System.out.println("---> domainEvents");
	    return result;
	}
	
	@AfterDomainEventPublication 
	Collection<Object> afterDomainEvents() {
		List<Object> result = new ArrayList<Object>();
		System.out.println("---> afterDomainEvents");
		return result;
	}

}
