package jackson;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Adnotacja Wrapuje obiekt w dodatkowy obiekt. 
 * Zamiast {"txt":"test"} bedzie: {"Wrapper":{"txt":"test"}}
 * Do dzia³ania niezbêdne jest: objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
 * Deserializacjia nie dzia³ bezpoœrednio bo obiektu WrapObj. Potrzeba Klasy wrapuj¹cej "Wrapper"
 */
@JsonRootName("Wrapper")
public class WrapObj {
	public String txt = "test";
}
