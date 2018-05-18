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
    	
    		//Pozwala na podpi�cie wielu �de� do jednego odno�nika "multiResources" 
            registry.addResourceHandler("/multiResources/**")
                    .addResourceLocations("/resources/", "/WEB-INF/resources3/")
                    //domy�lnie dodawany jest tylko podstawowy resolver czyli
                    //addResolver(new PathResourceResolver())
                    ;
            
            //zasoby z podkatalogu webapp
            registry.addResourceHandler("/resources/**")
            	.addResourceLocations("/resources/"/*, "classpath:/resources/"*/)
            	.setCachePeriod(3600) //czas podawany w sekundach
            	.resourceChain(true)
            	.addResolver(new PathResourceResolver());
            
            //zasoby z podkatalogu resources2 z classpath - nieuda�o mi si� tego uruchomi, wi�c pomijam
            /*registry.addResourceHandler("/resources2/**")
            	.addResourceLocations("classpath:/resources2/")
            	.setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
            */
            
            //zasoby z lokalnego systemu plik�w
            registry.addResourceHandler("/files/**")
            	.addResourceLocations("file:/Users/Mudia.BIURO/")
            	.setCachePeriod(3600)
            	.resourceChain(true) //niezb�dne, aby zadzia�a�o addResolver, wpp domy�lnie dodawany jest tylko PathResourceResolver 
            	//dodawanie 2 redzaj�w resolver�w            	
            	//ten resolver powoduje odes�anie spakowanej informacji ,ale 
            	//je�li mu si� to nie uda to uzyty zostanie PathResourceResolver
            	//dodatkowo ustawi w header warto�c dla �Accept-Encoding� na "gzip"
            	.addResolver(new GzipResourceResolver())
            	.addResolver(new PathResourceResolver());
            
            //zasoby z lokalnego systemu plik�w
            registry.addResourceHandler("/other-files/**")
            	.addResourceLocations("file:/Users/Mudia.BIURO/")
            	.setCachePeriod(3600).resourceChain(true).addResolver(new GzipResourceResolver());
    }
}

