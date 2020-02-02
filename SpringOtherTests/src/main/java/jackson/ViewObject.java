package jackson;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Reguluje, które pola s¹ seializowane
 * Deserializacjia jest jakaœ dziwna, wiêc olewamy przy niej wiodoki, 
 * nadmiarowe pola i tak pozostaj¹ null/domyœlne.
 */
public class ViewObject {
	public String txt1;
	@JsonView(Views.Public.class)
	public String txt2;
	@JsonView(Views.Internal.class)
	public String txt3;
}
