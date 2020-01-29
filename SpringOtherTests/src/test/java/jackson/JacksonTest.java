package jackson;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
//@RunWith(SpringRunner.class)

//@TestPropertySource
//@Category(DatabaseTest.class)
//@ComponentScan(basePackageClasses = { DszApplication.class, BaseDszDatabaseTest.class })
public class JacksonTest {

	@Test
	public void testValidate() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		MyObject1 mo1 = new MyObject1();
		mo1.txt1 = "test1";
		mo1.txt2 = "test2";
		
		// Serializacja 
		
		System.out.println("->0");
		// objectMapper.configure(Feature / MapperFeature. pozwala na zmianê ustawiñ mappera, np ignorowanie adnotacji
		objectMapper.configure(Feature.AUTO_CLOSE_TARGET, false); // Zapobiega zamkniêciu targetu - poni¿ej System.out
		objectMapper.writeValue(System.out, mo1); // Wypisuje wynik na output stream 
		
		System.out.println("->1");
//		objectMapper.writeValue(new File("target/mo.json"), mo1); // Wypisuje wynik do pliku
		
		System.out.println("writeValueAsString: " + objectMapper.writeValueAsString(mo1));
		System.out.println("->2");
		
		// Deserializacjia
		
		String stMo1 = objectMapper.writeValueAsString(mo1);
		// Stringa J mo¿na te¿ useska pisz¹c go bezpoœrednio.
		stMo1 = "{ \"txt1\" : \"stMo2_1\", \"txt2\" : \"stMo2_2\" }";
		
		MyObject1 newMo1 = objectMapper.readValue(stMo1, MyObject1.class); // Zapis do obiektu tej samej klasy
		
		// Zapis jest mo¿liwy do obiektu dowolnej klasy, o ile posiada posiada wszystkie wymagane settery
		// W przypadku braku settera UnrecognizedPropertyException 
		//MyObject2 newMo2 = objectMapper.readValue(stMo1, MyObject2.class); // Zapis do obiektu innej klasy
				
		// Zród³a: plik, input stream, URL, string 
		//objectMapper.readValue(new File("target/mo.json"), MyObject1.class);
		//objectMapper.readValue(new URL("file:src/test/resources/mo.json"), MyObject1.class);
		
		// Stringa J mo¿na przerobi na drzewo J i potem je parsowac
		JsonNode jsonNode = objectMapper.readTree(stMo1);
		String txt1 = jsonNode.get("txt1").asText();
		
		
		
		System.out.println("END");
	}	
	
}
