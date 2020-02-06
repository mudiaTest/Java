package hibernate01.inheritance.mappingStrategy.joined;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import hibernate01.inheritance.Address6_2;
import lombok.Data;

@Data
@Entity
public class Person6_4211 extends Person6_421{
	public String callname;
}
