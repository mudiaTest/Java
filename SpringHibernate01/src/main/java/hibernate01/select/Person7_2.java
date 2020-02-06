package hibernate01.select;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lombok.Data;

@Data
@Entity
@NamedQueries({
	@NamedQuery(name="test1",
				query = "SELECT p \n"
						+ "FROM Person7_2 p \n"
						+ "WHERE p.age1 = :age"),
	
	@NamedQuery(name="test11",	
	query = "SELECT p \n"
			+ "FROM Person7_2 p \n"
			+ "WHERE p.age1 = ?1"),
	
	@NamedQuery(name="test2",
				query = "SELECT p \n"
						+ "FROM Person7_2 p \n"
						+ "WHERE p.age2 = :age"),
	
	@NamedQuery(name="test3",
	query = "SELECT p \n"
			+ "FROM Person7_2 p \n"
			+ "WHERE p.age3 = :age"),
	
	@NamedQuery(name="selectObjAndField",
	query = "SELECT p, p.name \n"
			+ "FROM Person7_2 p \n")
})

public class Person7_2 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
	public int age1;
	public Integer age2;
	public Integer age3;	
}
