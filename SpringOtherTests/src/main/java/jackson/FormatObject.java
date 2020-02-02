package jackson;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Format do serializacji data/time
 * NIE DZIALA przy deserializacji. Gdy dostanie taki string to wywali b³¹d
 *
 * Do parsowania dat wymagane objectMapper.findAndRegisterModules()
 * opcjonalnie compile group: 'com.fasterxml.jackson.datatype', name: 'jackson-datatype-jsr310', version: '2.10.2'
 */
public class FormatObject {
	@JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "dd-MM-yyyy hh:mm:ss")
	public LocalDateTime dt;
}
