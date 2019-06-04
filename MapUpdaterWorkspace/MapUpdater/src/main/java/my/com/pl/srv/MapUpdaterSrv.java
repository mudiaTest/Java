package my.com.pl.srv;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import my.com.pl.config.FilesEnv;
import my.com.pl.srv.common.Zip7Srv;

@Service
public class MapUpdaterSrv {
	@Autowired
	Zip7Srv us;
	@Autowired
	GMTService gs;	
	@Autowired
	FilesEnv fenv;
	
	public void run() {
		boolean goNext = true;
		if(!fenv.getTmpLocation().equals("")){
			/*
			 * Sprawdzanie istnienia pliku ze zródłową mapą
			 */
			File f = new File(fenv.getDownloadedFile());
			goNext = f.exists();	
			if (!goNext){
				System.out.println("Brak pliku '"+fenv.getDownloadedFile()+"'");
				return;
			}
			/*
			 * unzip
			 */
			goNext = unzip(Paths.get(fenv.getDownloadedFile()), Paths.get(fenv.getTmpLocation()));
			if (!goNext){
				System.out.println("Nie powiodła się akcja rozpakowywania.");
				return;	
			}
		}
		
		/*
		 * Tworzenie mapy
		 */
		if (fenv.getKdDst() > 3) {
			System.out.println("Niezdefiniowana akcja o kodzie '" + fenv.getKdDst() + "'");
			return;
		}		
		if (fenv.getKdDst() == 1 || fenv.getKdDst() == 3) {
			goNext = addMapToMapSource();
			if (!goNext){
				System.out.println("Nie powiodła się akcja instalacji mapy");
				return;	
			}
		}
		if (fenv.getKdDst() == 2 || fenv.getKdDst() == 3) {
			goNext = addMapToCard();
			if (!goNext){
				System.out.println("Nie powiodła się akcja utworzenia mapy");
				return;	
			}
		}
	}	
	
	private boolean unzip(Path tmpFilePath, Path tmpTmpDirPath) {
		try {			
			us.unzip7Zip(tmpFilePath.toString(), tmpTmpDirPath);
//			ds.unzipArchive(tmpFilePath.toString(), tmpTmpDirPath.toString());
//			ds.unzipZip4J(tmpFilePath.toString(), tmpTmpDirPath.toString());
		}
		catch (Exception e) {
			System.out.println("Unzip error: "+e.getMessage());
			return false;
		}
		System.out.println(" -- 'unzip' done --");
		return true;
	}
	
	private boolean addMapToMapSource() {
		try {			
			gs.addToMapSource(
					fenv.getTypFid(), 
					fenv.getDstMapSourceDir(), 
					fenv.getDstMapName(), 
					fenv.getScrImgs(), 
					fenv.getSrcTypFilePath(),
					fenv.getPriority(),
					fenv.getTransparency()
					);
			return true;
		}
		catch (Exception e) {
			System.out.println("GMTSrv error: "+e.getMessage());
			return false;
		}
	}
	
	private boolean addMapToCard() {
		try {			
			gs.addToCard(
					fenv.getTypFid(), 
					fenv.getDstCardPath(), 
					fenv.getDstMapName(), 
					fenv.getScrImgs(), 
					fenv.getSrcTypFilePath(),
					fenv.getPriority(),
					fenv.getTransparency()
					);
			return true;
		}
		catch (Exception e) {
			System.out.println("GMTSrv error: "+e.getMessage());
			return false;
		}
	}
}
