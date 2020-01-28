package my.com.pl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "files")
@Configuration
public class FilesEnv {
	String tmpLocation;
	String downloadedFile;
	String srcMapSetImgFile;
	String srcImgs;
	Boolean usePreparedTmpLocation;
	String srcTypFilePath;
	
	String dstCardPath;
	String dstMapSourceDir;
	//String dstMapFid;
	String dstMapName;
	Integer priority;
	Boolean transparency;
	Integer kdDst;
	Boolean recreateTrailforks;
	
	String typFid;
}
