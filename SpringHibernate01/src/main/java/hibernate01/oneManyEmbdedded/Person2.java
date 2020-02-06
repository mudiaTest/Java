package hibernate01.oneManyEmbdedded;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Person2 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
	
		/*
		 * @ElementCollection gdy PersonFeature1 jest @Embeddable, to
		 * - PersonFeature1 nie posziada id
		 * - Zostanie utworzona tabela "person1_features" i w niej pola
		 *   "person1_id" - odnoœnik do obiektu person
		 *   "features_key" - klucz mapy	   
		 *   Pole id zostanie utworzone tego samego typu, wiêc mo¿na u¿yæ stringa etc.
		 */
	@ElementCollection(fetch=FetchType.EAGER)
		/*
		 * key: basic, @Embeddable lub @Entity
		 * value: basic, @Embeddable. Jeœli @Entity to zamiast @ElementCollection u¿yæ @OneToMany/@ManyToMany
		 */
	public Map<Integer, PersonFeature1> features = new HashMap<>();
	
		/*
		 * PersonFeature2 jest @Entity, wiêc zamiast @ElementCollection powinno by¿ uzyte
		 *  @OneToMany, ale wygl¹da na to, ¿e dzia³aj¹ dok³adnie tak samo
		 *  
		 * W odró¿nieniu od @ElementCollectionX@Embeddable, gdzie podobiekty nie mia³y w³asnego id i by³y 
		 * zapisywane automatycznie razem z rodzicem, dla @ElementCollectionXEntity nale¿y wczeœniej zapisaæ 
		 * te podobiekty
		 */
	@ElementCollection(fetch=FetchType.EAGER)
	public Map<Integer, PersonFeature2> features2 = new HashMap<>();
	
		/*
		 * Tworzona jest tabela poœredniczaca przetrzymuj¹ca person1_id, features3_id, features3_key
		 * Tak jak dla @ElementCollectionXEntity nale¿y wczeœniej zapisaæ te podobiekty. 
		 */
	@OneToMany(
			fetch=FetchType.EAGER, 
				/*
				 * Umo¿liwia zautomatyzowanie zapisu/modyfikacji/usuniêcia etc. ustawiaj¹c cascade
				 */
			cascade=CascadeType.ALL
			)
	public Map<Integer, PersonFeature2> features3 = new HashMap<>();
	
//	@ElementCollection(fetch=FetchType.EAGER)
//	Map features4 = new HashMap();
}
