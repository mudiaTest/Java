package hibernate01.inheritance.mappedSuperclass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class Person6_3Gen {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
}
