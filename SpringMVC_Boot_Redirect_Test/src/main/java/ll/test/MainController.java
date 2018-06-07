package ll.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {
	
	//wyœwietlanie stanu list
	
	private StrList lst = new StrList();
	@RequestMapping("/lst")
	public String lst(Model model) {
		System.out.println("lst()");	
		model.addAttribute("lst", lst);
		return "lst";
	}
	
	// prosta rejestracja danych bez przekazywania danych - korzysta z wyœwietlaniu stanu list
	
	@RequestMapping("/add")
	public String add(@ModelAttribute StringWrapper sw) {
		System.out.println("add()");
		return "add";		
	}
	
	@RequestMapping("/save")
	public String save(@ModelAttribute StringWrapper sw, Model model) {
		System.out.println("save()");
		lst.add(sw);
		return "redirect:/lst";
	}
	
	// rejestracja danych z przekazywaniem danych
	
	@RequestMapping("/add2")
	public String add2(@ModelAttribute StringWrapper sw) {
		System.out.println("add2()");
		return "add2";		
	}
	
	@RequestMapping("/save2")
	public String save2(@ModelAttribute StringWrapper sw, Model model, RedirectAttributes ra) {
		System.out.println("save2()");
		lst.add(sw);
		String txt1 = new String("txt1");
		String txt2 = new String("txt2");
		/* poni¿sze
		 * 
		 * model.addAttribute("title", txt);
		 * return "redirect:/lst2/"+txt;
		 *	
		 * jest równowa¿ne z 
		 */
		//(1) tworzyny atrybut tekst1 z watoœci¹ zmiannej 'txt1'
		ra.addAttribute("tekst1", txt1);
		ra.addAttribute("tekst2", txt2);
		ra.addAttribute(lst);
		//(2) atrybut przekazujemy przez url
		return "redirect:/lst2/{tekst1}";
		
	}
	
	//(3) atrybut odbieramy przez url
	@RequestMapping("/lst2/{texty1}")
	//(4) zawartoœ atrybutu ³aczymy ze zmienn¹ 'tekst'
	public String lst2(@PathVariable("texty1") String texty1, 
					   @RequestParam("tekst1") String text1,
					   @RequestParam("tekst2") String text2,
                       @ModelAttribute("flashAttr") String flashAttr,
					   Model model) {
		System.out.println("lst2()");
		
		//(5) tworzymy atrybut dla widoku, który bêdzie wyœwietlany - wype³niamy go zawartoœci¹ zmiennej 'tekst'
		model.addAttribute("myTxt1", texty1);
		return "lst2";
	}
}
