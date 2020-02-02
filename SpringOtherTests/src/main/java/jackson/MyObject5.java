package jackson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Ustalamy customowy serializator
 * Bez ustalenia deserializatora obiekt zostanie odtworzony z prefixem
 */
public class MyObject5 {
	public String txt1 = "test1";
	@JsonSerialize(using = PrefixStringSerializer.class)
	@JsonDeserialize(using = PrefixStringDeserializer.class)
	public String txt2 = "test2";
}
