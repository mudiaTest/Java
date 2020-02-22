package hibernate02.inheritance.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy=InheritanceType.JOINED)// Każdy poziom ma id oraz własne pola - obiekt jest rozpostarty na wielu tabelach
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)// Jest jedna tabela root "Person_gen", która posiada sumę wszystkich kolumn (z dziedziczenia)
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)// Każdy poziom ma osobną tabelę - zawiera sumę kolumn z dzidziczonych tabel 
public class Person_gen {

  @Id
  @GeneratedValue
  private Integer id;
  
  private String stGen;

  public Person_gen() {
  }  
  
  
}
