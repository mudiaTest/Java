package ll.test;


import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@EnableWebMvc
@Configuration
@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    /*@PostConstruct
    public void init() {
       requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
    }*/
	
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thmViewResolver()); 
    }
    
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(ctx);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        // !!! SUPER HIPER BARDZO WA�NE !!! je�li chcemy miec opcj� reload
        templateResolver.setCacheable(false);
        return templateResolver;
     }    

    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    
    public ThymeleafViewResolver thmViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        return resolver;
    }	
    
    //niezb�dne, aby zadzia�a�o pobieranie styli przez szablon
    public void addResourceHandlers(ResourceHandlerRegistry registry) {    	       
        //zasoby z podkatalogu webapp
        registry.addResourceHandler("/resources/**")
        	.addResourceLocations("/resources/")
        	.setCachePeriod(1) 
        	.resourceChain(true)
        	.addResolver(new PathResourceResolver());      
        registry.addResourceHandler("/static/**")
    	.addResourceLocations("/static/")
    	.setCachePeriod(1) 
    	.resourceChain(true)
    	.addResolver(new PathResourceResolver());        
    }
    
    /* Bean wskazuj�cy na z�d�o komunikat�w. 
     * To tylko cz�c nazwy. Je�li dodamy beana LocaleResolver 
     * to nazwa b�dzie �aczona z sufixem oznaczaj�cym jezyk.
     * Uwaga!! Jest dodawany automatycznie bean, ktory na polski szuka "messages_pl"
     * http://www.baeldung.com/spring-boot-internationalization
     */
    @Bean
    @Description("Spring Message Resolver")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //Nazwa pliku z komunikatami
        messageSource.setBasename("messages");
        return messageSource;
    }
}

