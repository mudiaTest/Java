package hibernate01.inheritance.entityInheritance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import hibernate01.inheritance.Address6_2;
import lombok.Data;

@Data
@Entity
public class Person6_21 extends Person6_2Gen{
	public Address6_2 addr;
}
