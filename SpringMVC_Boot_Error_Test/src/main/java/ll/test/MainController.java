package ll.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {
	
	private List<String> lst = new ArrayList<>();

	@RequestMapping("/hej2")
	public String hej2() {
		System.out.println("hej2(...)");
		return "hej2";
	}
	
	//Brak dodatkowej obs³ugi b³êdów - odda HTTP error 500
	@RequestMapping("/err11")
	public String err11() throws Exception {
		System.out.println("err11(...)");
		throw new Exception("Exception 'Err11'"); 
	}
	
	//Brak dodatkowej obs³ugi b³êdów - odda HTTP error 405
	@RequestMapping("/err12")
	public String err12() throws Exception {
		System.out.println("err12(...)");
		throw new HttpRequestMethodNotSupportedException("HttpRequestMethodNotSupportedException 'Err12'"); 
	}
	
	//-------------------------------------------------------------
	
	//MyException01 ma w³asn¹ obs³ugê kodów
	@RequestMapping("/err21")
	public String err21() throws Exception {
		System.out.println("err21(...)");
		throw new MyException01("MyException01 'err21'"); 
	}
	
	//-------------------------------------------------------------
	
	//MyException02 zostnie obs³u¿ony przez handleDuplicateSpittle
	@RequestMapping("/err31")
	public String err31() throws Exception {
		System.out.println("err31(...)");
		throw new MyException02("MyException02 'err31'"); 
	}
	
	/*
	 * Ta metoda bêdzie obs³ugiwa wyst¹piania wyj¹tku MyException02 w dowolnej 
	 * metodzie w TYM kontrolerze. Dla podobnej obs³ugi w innym kontrolerze nale¿y
	 * wstawic tam podobny kod LUB u¿y kodu porady
	 */
	
	@ExceptionHandler(MyException02.class)
	public String handleDuplicateSpittle() {
		return "errDup";
	}
	
	//-------------------------------------------------------------
	
	@RequestMapping("/err41")
	public String err41() throws Exception {
		System.out.println("err41(...)");
		throw new MyException03("MyException03 'err41'"); 
	}	
}
