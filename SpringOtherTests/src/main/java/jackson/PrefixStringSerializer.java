package jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PrefixStringSerializer extends StdSerializer<String> {

		// WYMAGANE !
		public PrefixStringSerializer() { 
        this(null); 
    } 
 
		// WYMAGANE !
    public PrefixStringSerializer(Class<String> t) {
        super(t); 
    }
 
    // J¹dro customowej serializacji
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
      gen.writeString("_" + value);
    }
}
