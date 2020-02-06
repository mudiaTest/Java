package hibernate01.id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Person3_3 {
	
	@Id
		/*
		 * SEQUENCE - bazuje na obiekcie sekwencji z bazy danych. 
		 * Jeœli sekwencje nie sa wspierane, to bêdzie u¿ywana opcja 
		 * 
		 * Domyœlna sekwencja to wspó³dzielona hibernate_sequence (u¿ywana te¿ np przez Auto)
		 * @GeneratedValue(strategy = GenerationType.SEQUENCE)
		 * 
		 * W³asna definicja sekwencji wymaga @SequenceGenerator
		 */
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
				/*
				 * nazwa generatora - mo¿e odnosiæ siê do @SequenceGenerator lub @TableGenerator
				 */
			generator="seq33gen")
	@SequenceGenerator(
				/*
				 * Nazwa sekwencji - odpowiada wartoœci pola @GeneratedValue(generator=...) 
				 */
			name="seq33gen", 
				/*
				 * Nazwa obektu sekwencji w DB
				 */
			sequenceName = "seq33DBgen", 
				/*
				 * Pocz¹tkowa wartoœæ sekwencji
				 */
			initialValue = 4)
	public Long key1;
	public String name;		
}
