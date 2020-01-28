package my.com.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import my.com.pl.config.FilesEnv;
import my.com.pl.srv.MapUpdaterSrv;
import my.com.pl.srv.TrailsSrv;

@SpringBootApplication
public class MapUpdaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MapUpdaterApplication.class, args);
	}	
	
	@Autowired
	private MapUpdaterSrv ms;	
	@Autowired
	private ApplicationContext ac;
	@Autowired
	private TrailsSrv ts;
	@Autowired
	private FilesEnv fe;
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
        	try {        		
        		System.out.println("-----------------------------------\n\n\n");
        		System.out.println(" 11:25");
        		System.out.println("->"+fe.getSrcImgs());
	        	System.out.println("-----------------------------------\n\n\n");
	        	if (fe.getRecreateTrailforks()) {
	        		System.out.println("Tworzenie mapy TrailForks");
	        		ts.recreateImg();
	        	}
	        	System.out.println("Zapis mapy w miejsce docelowe");
	        	ms.run();
	        	System.out.println("\n\n\n-----------------------------------");
        	}
    		finally {	
    			SpringApplication.exit(ac, () -> 0);
    		}
        };
    }
}
