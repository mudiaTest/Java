package hibernate06.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Value;

//@Entity
@Value
public class Address {
  @Id
  public Integer id;

  public String street;
}
