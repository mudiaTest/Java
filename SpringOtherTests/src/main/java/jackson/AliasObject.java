package jackson;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * Spowoduje �e zadzia�aj� poni�sze
 * {\"txt\":\"dummy\"} 
 * {\"_txt\":\"dummy\"} 
 * {\"f_txt\":\"dummy\"} 
 */
public class AliasObject {
  @JsonAlias({ "txt", "_txt", "f_txt" })
  public String txt = "test";
}
