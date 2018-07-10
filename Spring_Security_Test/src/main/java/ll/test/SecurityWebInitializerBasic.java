package ll.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

//włącza ustawienia bezpieczeństwa aplikacji internetowej, ale bez poniższej konfiguracji jest bezuzyteczna
//@Configuration 
//@EnableWebSecurity - jest właczone wraz z dependency spring-security-config
public class SecurityWebInitializerBasic extends WebSecurityConfigurerAdapter {

	/*
	 * #1 inMemoryAuthentication wymaga podania decyptera. Bez tej linii dostajemy
	 * There is no PasswordEncoder mapped for the id "null"
	 * Było to spowodawanie tym, ze przed SS 5 domyślnym enkyptorem haseł był NoOpPasswordEncoder, czyli 
	 * plainText, ale to nie jest bezpieczne
	 * @1 podejście (a): OBSOLETE
	 * @Bean
	 * public NoOpPasswordEncoder passwordEncoder() {
	 *     return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	 * }
	 */
			
	//Tworzymy instancję encodera do kodowania hasła
	//Można to wstawi do Beana i tutaj tylko Autowireowac
	
	/* @1 podejście (b)
	 * Jeśli wstawimy gdzieś beana zwracającego PasswordEncoder, to nie musimy wtedy stosowac (d)
	 */
	// @Bean
	public PasswordEncoder passwordEncoder_bcrypt() {
	    return new BCryptPasswordEncoder();
	}
	
	//Definiujemy jaką rolę otrzyma uzytkownik po poprawnym zalogowaniu 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		/* @1 podejście (c): OBSOLETE
		 * auth.inMemoryAuthentication()
		 * .passwordEncoder(NoOpPasswordEncoder.getInstance())
		 * .withUser("c").password("c").roles("USER")
		 * .and()
		 * .withUser("b").password("b").roles("ADMIN");
		 */		
		PasswordEncoder encoder = passwordEncoder_bcrypt();
		
		auth.inMemoryAuthentication()//inMemoryAuthentication powinno by używane tylko do testów
		// @1 podejście (d)
		.passwordEncoder(encoder)//dodaje encoder, który potem jest używany podczas logowanie	
		/*
		 * enkodujemy hasło przy pomocy enkodera - konieczne nawet pomimoistnienia 
		 * beana oddającego PasswordEncoder. Hasło nie jest automatycznie enkodowane
		 */
		
	    .withUser("c").password(encoder.encode("c")).roles("USER") 
	    .and()
	    //przypisywanie użytkownikowi wielu ról
	    .withUser("b").password(encoder.encode("b")).roles("ADMIN","USER")
	    .and()
	    .withUser("d").password(encoder.encode("d")).roles("DEMIGOD")
	    .and()
	    .withUser("g").password(encoder.encode("g")).roles("GOD").authorities("READ")
	    
	    //poniższe cechy oddadzą na stronie podczas próby zalogowania odpowiednie 
	    //komunikaty (logowanie oczywiście nie powiedzie się)
	    .and()
	    .withUser("11").password(encoder.encode("11")).accountExpired(true).roles("11")
	    .and()
	    .withUser("12").password(encoder.encode("12")).accountLocked(true).roles("12")
	    .and()
	    .withUser("13").password(encoder.encode("13")).disabled(true).roles("13")
	    .and()
	    .withUser("14").password(encoder.encode("14")).accountLocked(true).roles("14")
	    
	    ;
	}
	
	//Definiujemy do jakich stron ma dostęp używkownik z zadaną rolą
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
		// --- Obejście zabezpieczeń na czas testowania ---
		    /* 
		     * Poniższy kod wyłącza sprawdzanie dla wszystkich stron i podstron. 
		     * Nie udało mi się znalec sposobu na wyłaczenie SS gdy w dependency jest spring-security-config
		     * Nie wymaga też żadnego logowania.
		     * Liczy się kolejnoś deklaracji antMatchers, więc antMatchers("/*").permitAll() musi by pierwsze
		     */
		    //.authorizeRequests().antMatchers("/*").permitAll()
		    //.and()
	    
	    
	    // --- ustawienia wymagań dla poszczególnych adresów ---
		    //Dopuszcza wszystich, nie wymaga logowania
		    .authorizeRequests()
		    	.antMatchers("/home").permitAll()
		    .and()
		    //dopuści tylko uzytkowników z rolą USER 
		    .authorizeRequests()
		    	.antMatchers("/user*").hasRole("USER")
		    .and()
		    //z ** trzeba uważac. Dopiszczalne są "/**", "**", ".../**", ale "...**" zadziała jak zwykła "...*"  
		    .authorizeRequests()
		    	.antMatchers("/adm**").hasRole("ADMIN")
		    .and()
		    //Dostąp do wszystkich adresów jak /adm/1 /adm/ggg/testowy itp
		    .authorizeRequests()
		    	.antMatchers("/adm/**").hasRole("ADMIN")
		    .and()
		    //wiele patternów dla matchera
		    .authorizeRequests()
		    	.antMatchers("/admin","/adm/**").hasRole("DEMIGOD")
		    .and()
		    //każdy request
		    .authorizeRequests()
		    	.anyRequest().hasRole("GOD")
		    .and()
	    
		    
	    // --- testowanie przywileju zamiast roli ---
		    .authorizeRequests()
		    	.antMatchers("bleble").hasAuthority("TESTAUTH")
		    .and()
	    
		    
	    // --- podaje stronę do błędu logowania ---
	    //Musi by podane @RequestMapping("Access_Denied") w kontrolerze 
		    .exceptionHandling()
		    	.accessDeniedPage("/Access_Denied")
		    .and()	    
	    // --- dodaje domyślną stronę logowania ---
		    .formLogin()
		    .and() 	    
	    	       	    
	    // --- dodaje adres dla wylogowania ---
		    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
