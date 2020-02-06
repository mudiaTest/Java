package hibernate01.inheritance.mappingStrategy.singleTable;

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

import hibernate01.inheritance.Contact6_2;
import lombok.Data;

@Data
@Entity
	/*
	 * Dyscrminator column zadzia³a tylko dla najbardziej generycznej klasy
	 */
//@DiscriminatorColumn(name="discCol12", discriminatorType=DiscriminatorType.STRING, length=20)
	/*
	 * Wartoœæ wpisywana do discriminatorColumn
	 * Domyœlnie dla STRING jest to nazwa tabeli a dla INTEGER jakieœ liczby
	 * 
	 * Wartoœæ musi byæ zgodna z typem discriminatorType inaczej da exception
	 */
@DiscriminatorValue(value="22")
public class Person6_412 extends Person6_41Gen{
	public Contact6_2 con;
}
