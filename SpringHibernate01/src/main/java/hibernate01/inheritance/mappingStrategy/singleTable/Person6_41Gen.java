package hibernate01.inheritance.mappingStrategy.singleTable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Data
@Entity
	/*
	 * InheritanceType.SINGLE_TABLE - ca³a hierarchia mapiwana do jednej tabeli zawieraj¹cej sumê pól
	 * 
	 * Tabela ma discriminator column, która wskazuje na klasê na koñcy ³ancuszka
	 * 
	 * SINGLE_TABLE to watroœc domyœlna
	 */
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
	/*
	 * Opisuje kolumnê discriminator
	 */
@DiscriminatorColumn(
			/*
			 * Nazwa kolumny
			 */
		name="discCol", 
			/*
			 * Typ kolumny
			 * Wartoœc pobierana jest z @DiscriminatorValue z koñcowej klasy
			 */
		discriminatorType=DiscriminatorType.INTEGER,
			/*
			 * Liczba znakow kolumny - bedzie exception jeœli bêdzie za malo znaków
			 * Dzia³a tylko dla STRING, pomijana dla reszty 
			 * Domyœlnie 31
			 */
		length=5)
public abstract class Person6_41Gen {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
}
