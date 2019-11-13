package validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
		
		Set<ConstraintViolation<ValidatedObj>> res = validator.validate(vo1);
		
		int t = 0;
	}
}
