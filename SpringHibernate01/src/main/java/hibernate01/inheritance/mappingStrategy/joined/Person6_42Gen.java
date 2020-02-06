package hibernate01.inheritance.mappingStrategy.joined;

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
	 * InheritanceType.JOINED - wspólne pola trzymane s¹ w jednej tabeli, a szczególne w innej
	 * Mo¿liwe s¹ ca³e ³añcuszki, czyli ka¿da klasa wskazyhje na inn¹ tabele a te ³acz¹ siê po FK
	 * Wartoœæ klucza w rzêdach (w ró¿nych tabelach) reprezentuj¹cych jeden obiekt jest oczywiœcie taka sama.
	 * 
	 * Dobra rezrezentacja polimorfizu, ale potrzeba du¿o joinów aby wyci¹gn¹æ ca³y obiekt
	 * 
	 * SINGLE_TABLE to watroœc domyœlna
	 */
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person6_42Gen {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
}
