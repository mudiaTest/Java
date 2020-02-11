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
     * To podej�cie w og�le nie przewiduje posiadania podelementu.
     * W zamian podelement posiada nadelement i maj� wsp�lne id wi�c:
     *  - OD STRONY INVERSE NIE MAMY DOSTEPU DO OWNERA!!!
     *   - Cascade zadzia�a tylko zapisuj�c ownera
     *  - zapisuj�c inverse nie zapewniamy ownera
     *  - odczytuj�c inverse nie mamy �adnej mo�lwo�ci automatycznego 
     *    odczytu ownera (co mozna obej�� robi�c w�asne JOIN FETCH)
     *  + PO OBU stronach dzia�a LAZY. Po stronie ownera jest normalne, a po 
     *    stronie inverse... brak el. do wczytania
     *  + Owner i inverse maja taki sam id, wi�c �atwo doczyta� (zasymulowa� LAZY)
     */
  //public Address5_14 address;  
}
