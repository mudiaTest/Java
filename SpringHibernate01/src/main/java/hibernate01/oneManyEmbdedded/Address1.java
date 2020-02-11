package hibernate01.oneManyEmbdedded;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address1 {

  public String city;
  public String street;
  
  public Address1(String city, String street) {
    super();
    this.city = city;
    this.street = street;
  }

  public Address1() {
    super();
  }  
}
