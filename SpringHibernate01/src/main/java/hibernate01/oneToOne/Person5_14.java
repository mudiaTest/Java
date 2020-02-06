package hibernate01.oneToOne;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

/*
 * Unidirectional
 */

@Data
@Entity
public class Person5_14 {
	@Id
	@GeneratedValue
	public Integer id;
	public String name;
		/*
		 * dziwna odmiana BIDIRECTIONAL
		 * To podejœcie w ogóle nie przewiduje posiadania podelementu.
		 * W zamian podelement posiada nadelement i maj¹ wspólne id wiêc:
		 *  - OD STRONY INVERSE NIE MAMY DOSTEPU DO OWNERA!!!
		 * 	- Cascade zadzia³a tylko zapisuj¹c ownera
		 *  - zapisuj¹c inverse nie zapewniamy ownera
		 *  - odczytuj¹c inverse nie mamy ¿adnej mo¿lwoœci automatycznego 
		 *    odczytu ownera (co mozna obejœæ robi¹c w³asne JOIN FETCH)
		 *  + PO OBU stronach dzia³a LAZY. Po stronie ownera jest normalne, a po 
		 *    stronie inverse... brak el. do wczytania
		 *  + Owner i inverse maja taki sam id, wiêc ³atwo doczytaæ (zasymulowaæ LAZY)
		 */
	//public Address5_14 address;	
}
