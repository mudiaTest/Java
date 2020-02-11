package hibernate01.inheritance.mappedSuperclass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import hibernate01.inheritance.Contact6_2;
import lombok.Data;

@Data
@Entity
public class Person6_232 extends Person6_3Gen{
  public Contact6_2 con;
}
