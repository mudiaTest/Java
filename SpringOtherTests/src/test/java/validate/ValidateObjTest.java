package validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import eventOldApproach.MyEvent;
import eventOldApproach.MyEvent2;

@SpringBootTest
//@RunWith(SpringRunner.class)

//@TestPropertySource
//@Category(DatabaseTest.class)
//@ComponentScan(basePackageClasses = { DszApplication.class, BaseDszDatabaseTest.class })
public class ValidateObjTest {

	@Test
	public void testValidate() {
		ValidatedObj vo1 = new ValidatedObj();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		vo1.s = "1";
		vo1.i = 5;
		Set<ConstraintViolation<ValidatedObj>> res = validator.validate(vo1, ValGroup1.class, ValGroup2.class);

		if (res.size() > 0) {
			for (ConstraintViolation<ValidatedObj> violation : res) {
				violation.getExecutableParameters();
			}
		}

		int t = 0;
	}
	
	@Autowired
  private ApplicationEventPublisher aep;
	
	/*
	 * Event zostanie obs³u¿ony synchronicznie, tzn, jeœli wy³apie go wiele listenerów, to bêd¹ 
	 * wykonywa³y siê jeden po drugim (a nie jednoczeœnie)
	 */
	@Test
	public void testSynchronousEvent() {		
		// Tworzymy Event
		MyEvent myEvent11 = new MyEvent(this, "To tylko test old 11.");
		MyEvent myEvent12 = new MyEvent(this, "To tylko test old 12.");
		MyEvent2 myEvent21 = new MyEvent2(this, "To tylko test old 21.");
		// Rzucamy event "w powietrze" aby zosta³ obs³u¿ony przez klasy "implements ApplicationListener<MyEvent>"
    aep.publishEvent(myEvent11);
    aep.publishEvent(myEvent12);
    aep.publishEvent(myEvent21);
	}
}
