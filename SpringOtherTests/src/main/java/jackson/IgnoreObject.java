package jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Zignorowane zostan� txt1, txt2, txt4
 */
@JsonIgnoreProperties({"txt2", "txt4"})
public class IgnoreObject {
  @JsonIgnore
  public String txt1 = "test1";
  public String txt2 = "test1";
  public String txt3 = "test1";
  public String txt4 = "test1";
}
