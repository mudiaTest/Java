package jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Include.NON_EMPTY - pomija null lub puste np "" dla String
 * Include.NON_NULL - pomija null
 * Poni�sze odda {"txt1":"test"}
 */
@JsonInclude(value = Include.NON_EMPTY)
public class IncludeObject {
  public String txt1 = "test";
  public String txt2;
  public String txt3 = "";
}
