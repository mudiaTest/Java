package ll.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionAdvice {
	
	@ExceptionHandler(MyException02.class)
	public String handleDuplicateSpittle2() {
		return "errDup2";
	}
	
	@ExceptionHandler(MyException03.class)
	public String handleDuplicateSpittle3() {
		return "errDup3";
	}
}
