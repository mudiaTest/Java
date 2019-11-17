package ja1;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DummyTest {

	@Test
	public void dummy() {

		Date t = Date.from(Instant.now());
		LocalDateTime ldt1 = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDateTime ldt2 = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("+00:00"));
		LocalDateTime ldt3 = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("+01:00"));
		LocalDateTime ldt4 = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("+02:00"));
		//System.out.println(t);
		System.out.println(ldt1.toString());
		System.out.println(ldt2.toString());
		System.out.println(ldt3.toString());
		System.out.println(ldt4.toString());
		System.out.println( ldt1.format(DateTimeFormatter.ISO_DATE_TIME));
		System.out.println( ldt1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		System.out.println( ldt1.format(DateTimeFormatter.ISO_OFFSET_TIME));
		int i = 0;
	}
}
