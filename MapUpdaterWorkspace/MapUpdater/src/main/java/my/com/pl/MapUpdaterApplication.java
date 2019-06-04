package my.com.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import my.com.pl.srv.MapUpdaterSrv;

@SpringBootApplication
public class MapUpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapUpdaterApplication.class, args);
	}	
	
//	@Autowired
//	MainService ms;	
//	@Autowired
//	private ApplicationContext ac;
//	
//	@Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//        	try {
//	        	System.out.println("-----------------------------------\n\n\n");
//	        	ms.run();
//	        	System.out.println("\n\n\n-----------------------------------");
//        	}
//    		finally {	
//    			SpringApplication.exit(ac, () -> 0);
//    		}
//        };
//    }
}
