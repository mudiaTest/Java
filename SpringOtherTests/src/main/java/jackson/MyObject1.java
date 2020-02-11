package jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MyObject1 {
  public String txt1;
  public String txt2;
  // Spowoduje ignorowanie pola
  @JsonIgnore
  public String txt3;
}
