package jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json wymaga istnienia pustego konstruktora ALE zamiast tego mo¿na zrobiæ konstruktor wskazuj¹cy, jak deserializowaæ.
 * Taki konstruktor mo¿e mieæ wiêcej parametrów, ale przynajmniej czeœæ z nich musi pokrywaæ wszystkie pola z deserializowanego stringa
 * Mo¿e byæ TYLKO JEDEN konstruktor z @JsonProperty inacej daje b³¹d
 * Wszystkier parametry MUSZA posiadaæ adnotacje, nie mo¿na mieszaæ w jednym konstruktorze, ale mog¹ byæ konstruktory bez adnotacji
 * {"txt":"test","txt3":"test2"}
 */
public class CustomConstructor {

	public String txt = "t1";
	public String txt2 = "t2";
	
	// OK, bo choæ s¹ dodatkowe pola, to s¹ te¿ wszystkie zniebêdne	
//	CustomConstructor (@JsonProperty("txt2") String atxt, @JsonProperty("txt4")String atxt4, @JsonProperty("txt3")String atxt3)
//	{
//		this.txt = atxt;
//		this.txt2 = atxt3;
//	}
	
	// BLAD zadzia³, bo brak pola "txt3"
//	CustomConstructor (@JsonProperty("txt2") String atxt, @JsonProperty("txt4")String atxt4)
//	{
//		this.txt = atxt;
//		this.txt2 = atxt4;
//	}
	
	CustomConstructor (@JsonProperty("txt2") String atxt, @JsonProperty("txt3")String atxt3)
	{
		this.txt = atxt;
		this.txt2 = atxt3;
	}
	
	CustomConstructor (String atxt, String atxt3, Long r)
	{
		this.txt = atxt;
		this.txt2 = atxt3;
	}
	
}
