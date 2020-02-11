package jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * @JsonProperty ustawia setter.getted dla danego klucza. Dzia�a podobnie do @JsonSetter i @JsonGetter   
 */
public class PropertyObject {
  private String txt = "test";
  
  @JsonProperty("txt")
  public void setTxtStr(String val) {
    txt = val;
  }
  
  @JsonProperty("txt")
  public String getTxtStr() {
    return txt;
  }
}
