package ll.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * dodanie compile('org.apache.tomcat.embed:tomcat-embed-jasper') jest wa¿ne!
 */

@Controller
public class MainController {
	
	@Autowired
	private Students students;
	
	private void dodajStudentowJesliNikogoNieMa() {
		if (students.size() == 0) {
			students.add((new Student()).setStudent("Ala", "K", 20));
			students.add((new Student()).setStudent("Jacek", "M", 25));
			students.add((new Student()).setStudent("Iza", "K", 30));
		}
	}
	
	@RequestMapping("/home")
	/*
	 * Model model odnosi siê do widoku, kóty zostanie wyœwietlony
	 */
	public String home(Model model) {
		System.out.println("home(...)");
		
		/* Umo¿liwie przekazanie obieku do formularza. 
		 * Automatycznie wywo³a ToString().
		 */
		model.addAttribute("serverTime", LocalDateTime.now());
		
		dodajStudentowJesliNikogoNieMa();
		
		/* Przekazujemy do formularza liste, po ktorej mozemy iterowac.
		 * NIE MIO¯NA przekazywa do listy boiektów takich jak String czy List<String>. 
		 * Nale¿y stworzy dla nich wrappery.
		 */
		model.addAttribute("students", students);
		
		return "home";
	}
	
	/*
	 * 1) Odpalamy addStudent, który ma formularz wskazuj¹cy na saveStudent.
	 *    Ten formularz spowiduje wygenerowanie nowego obiektu studenta.
	 * 2) Save student pobiera dane z formularza (i potencjalne b³êdy).
	 *    Dodaje obiekt studenta (z parametrów) do listy i przekierowuje na 
	 *    stronê z lista studentów (lub z powrotem do formularza w przypadku b³êdów)
	 */
	
	/*zapis studenta - wersja uproszczona, bez kontroli b³êdów*/
	
	@PostMapping("/saveStudent")
	public String saveStudent(@ModelAttribute Student student, Model model) {
		students.add(student);
        model.addAttribute("students", students);
        /*
         *  linia zapobiegaj¹ca ponownemu wys³aniu danych POST. 
         *  Gdyby u¿yc return "/home" to œciezka wywo³añ by³a by nastêpuj¹ca
         *  	8080:/addStudent -> 8080:/saveStudent (to defacto wyœwietla home, ale nazwa   
         *      w linii adresu jest save i odœwierzaj¹c tê stronê ponownie przekazujemy dane POST)
         *  U¿ywaj¹c return "redirect:/home" œciezka wywo³añ jest nastêpuj¹ca  
         *      8080:/addStudent -> 8080:/home baz pokazywania 8080:/saveStudent w linii adresu 
         *      i odœwierzaj¹c stronê lub cofaj¹c siê w histotrii nie ma mo¿liwoœci na ponowne 
         *      przekazania danych POST
         *      
         *  Wartoœci wyœwietlane przez home s¹ poprawne, bo w samym @RequestMapping("/home") 
         *  dodajemy odpowiednie wartoœci. saveStudent nie przekazuje ¿adnych wartoœci do home
         *  Odpowiednie przyk³ady na tak¹ akcjê s¹ w projekcie SpringMVC_Boot_Error_Test
         */
        return "redirect:/home"; 
	}
	
	@GetMapping("/addStudent")
	public String dodajStudenta(@ModelAttribute Student student) {
		return "addStudent";
	}
	
	/*zapis studenta - wersja roszerzona o kontrolê b³êdów*/
	/*
	 * Samo dodanie obs³ugi b³êdów powoduje przylepienie odpowiedniej informacji do 
	 * obiektu (nie samego oczywiœcie bo to POJO).
	 * BindingResult errors daje po prostu dostêp do tych danych w kodzie.
	 * Dostêp do b³êdów w formularzu jest niezalezny, co wida na przyk³adzie 
	 * addStudentCheck.html 
	 */
	@PostMapping("/saveStudentCheck")
	public String seveStudentCheck(@Valid @ModelAttribute Student student, BindingResult errors, Model model) {
		if (!errors.hasErrors()) {
            students.add(student);
            model.addAttribute("students", students);
        }
        return ((errors.hasErrors()) ? "addStudentCheck" : "home"); 
	}
	
	/*
	 * adnotacja @ModelAttribute Student student jest niezbedna, aby 
	 * w formularzu miec dostêp do pó³ obiektu
	 */
	@GetMapping("/addStudentCheck")
	public String dodajStudentaCheck(@ModelAttribute Student student) {
		return "addStudentCheck";
	}
}
