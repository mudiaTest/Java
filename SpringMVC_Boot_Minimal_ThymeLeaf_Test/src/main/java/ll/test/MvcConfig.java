package ll.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


//@EnableWebMvc
//@Configuration
//@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private ApplicationContext ctx;
	
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
    
    //niezbêdne, aby zadzia³a³o pobieranie styli przez szablon
    /*public void addResourceHandlers(ResourceHandlerRegistry registry) {    	       
        //zasoby z podkatalogu webapp
        registry.addResourceHandler("/resources/**")
        	.addResourceLocations("/resources/")
        	.setCachePeriod(3600) 
        	.resourceChain(true)
        	.addResolver(new PathResourceResolver());        
    }*/
}

