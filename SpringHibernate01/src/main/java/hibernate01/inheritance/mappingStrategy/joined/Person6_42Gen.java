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
   * InheritanceType.JOINED - wsp�lne pola trzymane s� w jednej tabeli, a szczeg�lne w innej
   * Mo�liwe s� ca�e �a�cuszki, czyli ka�da klasa wskazyhje na inn� tabele a te �acz� si� po FK
   * Warto�� klucza w rz�dach (w r�nych tabelach) reprezentuj�cych jeden obiekt jest oczywi�cie taka sama.
   * 
   * Dobra rezrezentacja polimorfizu, ale potrzeba du�o join�w aby wyci�gn�� ca�y obiekt
   * 
   * SINGLE_TABLE to watro�c domy�lna
   */
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person6_42Gen {
  @Id
  @GeneratedValue
  public Integer id;
  public String name;
}
