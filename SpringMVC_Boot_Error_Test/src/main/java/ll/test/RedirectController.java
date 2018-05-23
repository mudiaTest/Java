package ll.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Controller
public class RedirectController {

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
	public String save2(@ModelAttribute StringWrapper sw, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("save2()");
		lst.add(sw);
		String txt = new String("Oto lista:");
		redirectAttributes.addFlashAttribute(lst);
		//poni¿sze
		/* 
		 * model.addAttribute("title", txt);
		 * return "redirect:/lst2/"+txt;
		 */	
		//jest równowa¿ne z 
		redirectAttributes.addAttribute("title", txt);
		return "redirect:/lst2/{title}";
		
	}
	
	@RequestMapping("/lst2/{txt}")
	public String lst2(@PathVariable("txt") String tekst, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("lst2()");	
		model.addAttribute("title", tekst);
		return "lst2";
	}
}
