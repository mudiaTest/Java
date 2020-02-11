package jackson;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Reguluje, kt�re pola s� seializowane
 * Deserializacjia jest jaka� dziwna, wi�c olewamy przy niej wiodoki, 
 * nadmiarowe pola i tak pozostaj� null/domy�lne.
 */
public class ViewObject {
  public String txt1;
  @JsonView(Views.Public.class)
  public String txt2;
  @JsonView(Views.Internal.class)
  public String txt3;
}
