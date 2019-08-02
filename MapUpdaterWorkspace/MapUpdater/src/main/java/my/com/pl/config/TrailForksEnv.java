package my.com.pl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

//prefix - wymaga małych liter
@Data
@ConfigurationProperties(prefix = "trailforks")
@Configuration
public class TrailForksEnv {
	Integer loopstep;
	String region;
	String difficulty;
	String mpFile;
	String imgFile;
	boolean deleteMpFile;
	
	String[] difficultyLvl;
	String[] poiType;
	String[] lineType;
}
