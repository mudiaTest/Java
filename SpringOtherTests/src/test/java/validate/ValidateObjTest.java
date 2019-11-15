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
}
