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

@Configuration 
public class SecurityWebInitializerCustomLoginLogout extends WebSecurityConfigurerAdapter {

	public PasswordEncoder passwordEncoder_bcrypt() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		PasswordEncoder encoder = passwordEncoder_bcrypt();		
		auth.inMemoryAuthentication()
		.passwordEncoder(encoder)
	    .withUser("g").password(encoder.encode("g")).roles("GOD").authorities("READ");
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
    
    .csrf().disable()
    .authorizeRequests()
        .anyRequest().hasRole("GOD")
    	.antMatchers("/home").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
    	.antMatchers("/user*").hasRole("USER")
        //.antMatchers("/signin*").anonymous()
        //.antMatchers("/customLogin**").permitAll()
        //.anyRequest().authenticated()
    .and()
    .formLogin()
    
        //Customowa strona z formularzem logowania. 
    	//Musi by zdefiniowane @RequestMapping("customLogin")    
        .loginPage("/customLogin")
        
        //Nazwa formularza gdzie zostana przesłane dane pobrane z formularza 
        //z logowania <form action="..."> - czyli gdzie należy je przeją
        //domyślnie jest to "/login". Wtedy w formilarzy ustawiamy "/login" 
        //i pomijamy poniższe loginProcessingUrl
        .loginProcessingUrl("/signin")
        
        //Adres na który zostanie przekierowany uzytkownik po porawnym zalogowaniu
        .defaultSuccessUrl("/home",true)
        
        //Customowa strona z formularzem logowania. 
        //Musi by zdefiniowane @RequestMapping("customFailure")
        //Bez niej wraca na stronę logowania
        .failureForwardUrl("/customFailure")
        
        //konieczne aby każdy miał dostęp do strony logowania - inaczej strona logowania 
        //będzie wymagała zalogowania i dostaniemy bład zapętlenia
        .permitAll()
        
        
    
        //Definiuje nazwy parametru z formularza logowania dla usera i hasła. 
        //Domyślnie jest to "username" i "password"
        //.usernameParameter("username")
  		//.passwordParameter("passwd")
    ;
    }
}