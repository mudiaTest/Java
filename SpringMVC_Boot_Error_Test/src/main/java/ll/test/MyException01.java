package ll.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Wystapienie tego wyj¹tku w metodzie Controllera spowoduje 
 * oddanie kodu 404 (HttpStatus.NOT_FOUND) i odpowiedniego opisu
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Wyj¹tek testowy MyException01")
public class MyException01 extends Exception{
	
	public MyException01(String string) {
		super(string);
	}

}
