package jackson;

import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Wskazuje, ¿e wartoœæ bêdzie wstrzykiwana injectorem, podczas deserializacji .
 * @JacksonInject wymaga id i domyœlnie nadaje klasê (np String), wiêc nie mo¿e 
 * byæ 2 domyœlnych adnotacji, bo id musi byæ unikalne.
 * Pierwszeñstwo maj¹ wartoœæi ze string, wiêc mog¹ nadpisywaæ injekcje
 * Wymaga takiego u¿ycia
 * InjectableValues inject = new InjectableValues.Std()
				.addValue(String.class, "testGen")
				.addValue("t2", "test2Injected")
				.addValue("t3", "test3Injected")
				.addValue("t4", "test4Injected");
		InjectObject no = objectMapper.reader(inject).forType(InjectObject.class).readValue("{\"txt\":\"test\"}");
 */
public class InjectObject {

	public String txt;
	@JacksonInject//("t2")
	public String txt2;
	@JacksonInject("t3")
	public String txt3;
	@JacksonInject("t4")
	public String txt4;
	
	//Dla u³atwienia- nie jest konieczne
	InjectObject (){}
	InjectObject (String a, String b, String c, String d){
		txt = a;
		txt2 = b;
		txt3 = c;
		txt4 = d;
	}
}
