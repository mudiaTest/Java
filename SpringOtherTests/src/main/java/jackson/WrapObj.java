package jackson;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Adnotacja Wrapuje obiekt w dodatkowy obiekt. 
 * Zamiast {"txt":"test"} bedzie: {"Wrapper":{"txt":"test"}}
 * Do dzia�ania niezb�dne jest: objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
 * Deserializacjia nie dzia� bezpo�rednio bo obiektu WrapObj. Potrzeba Klasy wrapuj�cej "Wrapper"
 */
@JsonRootName("Wrapper")
public class WrapObj {
  public String txt = "test";
}
