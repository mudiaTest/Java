package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class PrefixStringDeserializer extends StdDeserializer<String> {

		// WYMAGANE !
		public PrefixStringDeserializer() { 
        this(null); 
    } 
 
		// WYMAGANE !
    public PrefixStringDeserializer(Class<String> t) {
        super(t); 
    }
 
    @Override
		public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			// Ta linia pobiera stringow¹ reprezentacjê z JStringa
			String value = p.getText();
			// Oddajemy po obciêciu prefixa - substring(1) oznacza pominiêcie znaku [0]
			return value.substring(1);
		}
}
