package my.com.pl.srv;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import my.com.pl.config.FilesEnv;
import my.com.pl.config.TrailForksEnv;
import my.com.pl.srv.common.Zip7Srv;

@Service
@Slf4j
public class MapUpdaterSrv {
	@Autowired
	Zip7Srv us;
	@Autowired
	GMTService gs;	
	@Autowired
	FilesEnv fenv;
	@Autowired
	TrailForksEnv tfv;
	
	public void run() {
		boolean goNext = true;
		
		//Badanie poprawnosci 
		if (fenv.getKdDst() < 1 || fenv.getKdDst() > 4) {
			System.out.println("Niezdefiniowana akcja o kodzie '" + fenv.getKdDst() + "'");
			return;
		}	
		//Pomijanie tworzenia mapy
		if (fenv.getKdDst() == 4) {
			System.out.println("Nie zdefiniowano żadnej akcji (kopiowania mapy na kartę lub instalacji w mapSource).");
			return;
		}
		
		
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
		if (fenv.getKdDst() == 1 || fenv.getKdDst() == 3) {
			System.out.println();
			goNext = addMapToMapSource();
			if (!goNext){
				System.out.println("Nie powiodła się akcja instalacji mapy");
				return;	
			}
			System.out.println();
		}
		if (fenv.getKdDst() == 2 || fenv.getKdDst() == 3) {
			System.out.println();
			goNext = addMapToCard();
			if (!goNext){
				System.out.println("Nie powiodła się akcja zapisu mapy na nośniku");
				return;	
			}
			System.out.println();
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
					fenv.getSrcImgs(), 
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
					fenv.getSrcImgs(), 
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
