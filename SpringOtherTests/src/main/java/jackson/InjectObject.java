package jackson;

import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Wskazuje, �e warto�� b�dzie wstrzykiwana injectorem, podczas deserializacji .
 * @JacksonInject wymaga id i domy�lnie nadaje klas� (np String), wi�c nie mo�e 
 * by� 2 domy�lnych adnotacji, bo id musi by� unikalne.
 * Pierwsze�stwo maj� warto��i ze string, wi�c mog� nadpisywa� injekcje
 * Wymaga takiego u�ycia
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
  
  //Dla u�atwienia- nie jest konieczne
  InjectObject (){}
  InjectObject (String a, String b, String c, String d){
    txt = a;
    txt2 = b;
    txt3 = c;
    txt4 = d;
  }
}
