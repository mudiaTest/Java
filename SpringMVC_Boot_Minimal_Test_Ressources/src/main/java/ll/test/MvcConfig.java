package ll.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@EnableWebMvc
@Configuration
@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views2/", ".jsp"); 
    }
	
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	
    		//Pozwala na podpiêcie wielu óde³ do jednego odnoœnika "multiResources" 
            registry.addResourceHandler("/multiResources/**")
                    .addResourceLocations("/resources/", "/WEB-INF/resources3/")
                    //domyœlnie dodawany jest tylko podstawowy resolver czyli
                    //addResolver(new PathResourceResolver())
                    ;
            
            //zasoby z podkatalogu webapp
            registry.addResourceHandler("/resources/**")
            	.addResourceLocations("/resources/"/*, "classpath:/resources/"*/)
            	.setCachePeriod(3600) //czas podawany w sekundach
            	.resourceChain(true)
            	.addResolver(new PathResourceResolver());
            
            //zasoby z podkatalogu resources2 z classpath - nieuda³o mi siê tego uruchomi, wiêc pomijam
            /*registry.addResourceHandler("/resources2/**")
            	.addResourceLocations("classpath:/resources2/")
            	.setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
            */
            
            //zasoby z lokalnego systemu plików
            registry.addResourceHandler("/files/**")
            	.addResourceLocations("file:/Users/Mudia.BIURO/")
            	.setCachePeriod(3600)
            	.resourceChain(true) //niezbêdne, aby zadzia³a³o addResolver, wpp domyœlnie dodawany jest tylko PathResourceResolver 
            	//dodawanie 2 redzajów resolverów            	
            	//ten resolver powoduje odes³anie spakowanej informacji ,ale 
            	//jeœli mu siê to nie uda to uzyty zostanie PathResourceResolver
            	//dodatkowo ustawi w header wartoœc dla “Accept-Encoding” na "gzip"
            	.addResolver(new GzipResourceResolver())
            	.addResolver(new PathResourceResolver());
            
            //zasoby z lokalnego systemu plików
            registry.addResourceHandler("/other-files/**")
            	.addResourceLocations("file:/Users/Mudia.BIURO/")
            	.setCachePeriod(3600).resourceChain(true).addResolver(new GzipResourceResolver());
    }
}

