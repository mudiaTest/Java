package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
/*
 * Definiuje jakby logiczne opakowanie, które pozwala na obs³ugê.
 * Oznacza to, ¿e klucz bêdzie przechowywany w tabeli Person4_11
 * , a nie w jakiejœ osobnej strukturze
 */
@IdClass(Person4Id.class)
public class Person4_11 {
	
		/*
		 * Pola oznaczone @Id musz¹ zgadzaæ siê co do iloœci, nazwy i typu z tymi zawartymi w Person4Id		 
		 * 
		 * @GeneratedValue dzia³a tak jak zwykle, ale nalêzy pamiêtac, ¿e Typ z obiektu musi pokrywaæ 
		 * siê z typem pola z klasy klucza 
		 * 
		 * do klucza odwo³ujemy siê poprzez Person4_11.key1
		 */
	@Id
		/*
		 * @GeneratedValue przes³oni wartoœc podstawion¹ do key1
		 */
	@GeneratedValue
	public Integer key1;
	@Id
	public String key2;
	public String name;		
}
