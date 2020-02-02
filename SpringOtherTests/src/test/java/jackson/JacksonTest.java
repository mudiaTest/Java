package jackson;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
//@RunWith(SpringRunner.class)

//@TestPropertySource
//@Category(DatabaseTest.class)
//@ComponentScan(basePackageClasses = { DszApplication.class, BaseDszDatabaseTest.class })
public class JacksonTest {

	//@Test
	public void testBasic() throws Exception {
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
		
		// Stringa J mo¿na przerobic na drzewo J i potem je parsowac
		JsonNode jsonNode = objectMapper.readTree(stMo1);
		String txt1 = jsonNode.get("txt1").asText();
						
		System.out.println("END");
	}	
	
	//@Test
	public void testSubObject() throws Exception {
		MyObject3 mo3 = new MyObject3();
		MyObject32 o = new MyObject32();
		o.txt32 = "test32";
		mo3.m32.put(1L, o);
		mo3.l32.add(new MyObject32());
		o = new MyObject32();
		o.txt32 = "test32";
		mo3.l32.add(new MyObject32());
		mo3.m32.put(2L, o);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String stMo3 = objectMapper.writeValueAsString(mo3);
		System.out.println("-> " + stMo3);
		
		MyObject3 newMo3 = objectMapper.readValue(stMo3, MyObject3.class); 
		System.out.println("END");
	}
	
	//@Test
	public void testAnnotation() throws Exception {
		MyObject4 mo4 = new MyObject4();
		ObjectMapper objectMapper = new ObjectMapper();
		String stMo4 = objectMapper.writeValueAsString(mo4);
		System.out.println("-> " + stMo4);
		
		System.out.println("<-");
		
		MyObject4 newMo4 = objectMapper.readValue(stMo4, MyObject4.class);
		System.out.println("END");
	}
	
	//@Test
	public void enumTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonEnum e = JsonEnum.TYPE2;
		String stE = objectMapper.writeValueAsString(e);
		JsonEnum newE = objectMapper.readValue(stE, JsonEnum.class);
		System.out.println("END");
	}
	
	//@Test
	public void wrapTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		// Nezbêdne do dzioa³ania @JsonRootName
		objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		WrapObj w = new WrapObj();
		String stW = objectMapper.writeValueAsString(w);
		// Deserializacjia nie dzia³a bezpoœrednio bo obiektu WrapObj. 
		// Potrzeba Klasy wrapuj¹cej "Wrapper"
		//WrapObj newW = objectMapper.readValue(stW, WrapObj.class);
		System.out.println("END");
	}
	
	//@Test
	public void customSerializerTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		MyObject5 mo5 = new MyObject5();
		// stmo5 zawiera preix w txt2
		String stmo5 = objectMapper.writeValueAsString(mo5);
		// Bez ustalenia deserializatora obiekt zosta³by odtworzony z prefixem, ale deserializer jest
		MyObject5 newW = objectMapper.readValue(stmo5, MyObject5.class);
		System.out.println("END");
	}
	
	//@Test
	public void customConstructorTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		CustomConstructor newCc = objectMapper.readValue("{\"txt\":\"test\",\"txt3\":\"test2\"}", CustomConstructor.class);
		System.out.println("END");
	}
	
  //@Test
	public void injectTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		InjectObject o = new InjectObject("test", "test2", "test3", "test4");
		String st = objectMapper.writeValueAsString(o);
		// Bez ustalenia deserializatora obiekt zostanie odtworzony z prefixem
		InjectableValues inject = new InjectableValues.Std()
				.addValue(String.class, "testGen")
				.addValue("t2", "test2Injected")
				.addValue("t3", "test3Injected")
				.addValue("t4", "test4Injected");
		InjectObject no = objectMapper.reader(inject).forType(InjectObject.class).readValue("{\"txt\":\"test\"}");
		System.out.println("END");
	}
	
	//@Test
	public void aliasTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		AliasObject o1 = objectMapper.readValue("{\"txt\":\"dummy\"} ", AliasObject.class);
		AliasObject o2 = objectMapper.readValue("{\"_txt\":\"dummy\"} ", AliasObject.class);
		AliasObject o3 = objectMapper.readValue("{\"f_txt\":\"dummy\"} ", AliasObject.class);
		System.out.println("END");
	}
	
	//@Test
	public void ignoreTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		IgnoreObject o = new IgnoreObject();
		String s = objectMapper.writeValueAsString(o);
		System.out.println("END");
	}
	
	//@Test
	public void includeTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		IncludeObject o = new IncludeObject();
		String s = objectMapper.writeValueAsString(o);
		System.out.println("END");
	}
	
	//@Test
	public void propertyTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		PropertyObject o = new PropertyObject();
		String s = objectMapper.writeValueAsString(o);
		PropertyObject no = objectMapper.readValue(s, PropertyObject.class);
		System.out.println("END");
	}
	
	/**
	 * 
	 * objectMapper.findAndRegisterModules();
	 */
	//@Test
	public void formatTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		// Niezbêdne dla parsowania dat - przynajmniej tych z Java 8
		objectMapper.findAndRegisterModules();
		FormatObject o = new FormatObject();
		o.dt = LocalDateTime.now();
		String s = objectMapper.writeValueAsString(o);
		// Dzia³a tylko do serializacji. Przy deserializacji bêd¹ b³êdy
		// FormatObject no = objectMapper.readValue(s, FormatObject.class);
		System.out.println("END");
	}
	
	//@Test
	public void viewTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		ViewObject o = new ViewObject();
		o.txt1 = "test1";
		o.txt2 = "test2";
		o.txt3 = "test3";
		
		String s1 = objectMapper.writerWithView(Views.Public.class).writeValueAsString(o);
		String s2 = objectMapper.writerWithView(Views.Internal.class).writeValueAsString(o);
		String s3 = objectMapper.writerWithView(Views.Private.class).writeValueAsString(o);
		String s4 = objectMapper.writeValueAsString(o);
		
		// Deserializacjê wynikujemy bez modyfikatora, bo nadmiarowe pola bêd¹ null/domyœlne
		ViewObject no = objectMapper.readValue(s3, ViewObject.class);
		System.out.println("END");
	}
	
	//@Test
	public void chieldParentTest() throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		ParentLoopObject p = new ParentLoopObject();
		ChildLoopObject c = new ChildLoopObject();
		//p.child = c;
		p.child = new ArrayList<>();
		p.child.add(c);
		c.parent = p;
		
	  // {"txt":"parent"} - tracimy info o dzieciach
		String sp = objectMapper.writeValueAsString(p);
		// {"txt":"child","parent":{"txt":"parent"}}
		String sc = objectMapper.writeValueAsString(c);
		
		System.out.println("END");
	}
		
	
}
