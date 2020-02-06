package hibernate02.inheritance.dto;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.Data;

@Data
@Entity
@NamedQueries(
{ @NamedQuery(name="delAll", query="DELETE FROM Worker")
 ,@NamedQuery(name="getById", query="SELECT w FROM Worker w WHERE w.id=:id") 
})
public class Worker extends PersonInWork {
	
	private String position;	

	public Worker(String name, String position) {
		super(name);
		this.position = position;
	}

	public Worker(String name) {
		super(name);
	}
	
	public Worker() {
		super();
	}

}
