package my.com.pl.srv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class GMTService {
	
//	private String testCommand = "gmt -S -f 6324,1 -o C:\\Garmin\\t -m \"test\" C:\\Garmin\\t\\src\\6*.img";
	private String stReg = "HKLM\\SOFTWARE\\Wow6432Node\\Garmin\\mapSource\\Families\\FAMILY_";
	private String reg = "C:\\Windows\\system32\\reg.exe";
	
	private String getRegPath(String dstMapFid, String outputName) {
		return stReg + "_" + dstMapFid + "_" + outputName;
	}
	
	private String concatCommand(String mapSetNumber, String outputName, String sourceImgs, String dstTypFilePath) {
		String result = "";
		result = result + " -f " + mapSetNumber + ",1";
		result = result + " -m \"" + outputName + "\"";
		result = result + " " + sourceImgs;		
		result = result + " " + dstTypFilePath;
		return result;
	}
	
	private String conacatMapSourceCommand(String mapSetNumber, String outputDir, String outputName, String sourceImgs, String dstTypFilePath) {
		String result = "gmt";
		result = result + " -S -o " + outputDir;
		result = result + concatCommand(mapSetNumber, outputName, sourceImgs, dstTypFilePath);
		return result;
	}
	
	private String conacatGPSCommand(String mapSetNumber, String outputDir, String outputName, String sourceImgs, String dstTypFilePath) {
		String result = "gmt";
		result = result + " -j -o " + concatOutputFilenamePath(outputDir, outputName);
		result = result + concatCommand(mapSetNumber, outputName, sourceImgs, dstTypFilePath);
		return result;
	}
	
	private String concatOutputFilenamePath(String outputDir, String outputName) {
		return outputDir+"\\"+outputName+".img";
	}
	
	private String concatMapUninstallCommand(String dstMapFid, String outputName) {
		return reg + " DELETE " + getRegPath(dstMapFid, outputName) + " /f";
	}
	
	private String concatMapInstallCommand0(String dstMapFid, String outputDir, String outputName) {
		String dstMapFidAsFourDigitHex = StringUtils.leftPad(Integer.toHexString(Integer.valueOf(dstMapFid)), 4, "0");
		/*
		 * Garmin (albo rejestr) wymaga, zeby zamiast 1234 pisać 3412 - nie wiem o co chodzi, ale muszę się dostosować
		 */
		String garminIdioticOrder = dstMapFidAsFourDigitHex.substring(2, 4)+dstMapFidAsFourDigitHex.substring(0, 2);
		return reg + " ADD " + getRegPath(dstMapFid, outputName) + " /v ID /t REG_BINARY /d " + garminIdioticOrder + " /f";
	}
	private String concatMapInstallCommand1(String dstMapFid, String outputDir, String outputName, String srcMapSetTypPath) {
		return reg + " ADD " + getRegPath(dstMapFid, outputName) + " /v TYP /t REG_SZ /d \"" + outputDir + "\\" + Paths.get(srcMapSetTypPath).getFileName().toString() + "\" /f";
	}
	private String concatMapInstallCommand2(String dstMapFid, String outputDir, String outputName) {
		return reg + " ADD " + getRegPath(dstMapFid, outputName) + "\\1 /v Loc /t REG_SZ /d \"" + outputDir + "\" /f";
	}
	private String concatMapInstallCommand3(String dstMapFid, String outputDir, String outputName) {
		return reg + " ADD " + getRegPath(dstMapFid, outputName) + "\\1 /v Bmap /t REG_SZ /d \"" + outputDir + "\\mapset.img\" /f";
	}
	private String concatMapInstallCommand4(String dstMapFid, String outputDir, String outputName) {
		return reg + " ADD " + getRegPath(dstMapFid, outputName) + "\\1 /v Tdb /t REG_SZ /d \"" + outputDir + "\\mapset.tdb\" /f	";	
	}
	
	private String getInput(InputStreamReader isr) {
		return new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
	}
	
	public void createMapsetImg(String inOutDir, String mpFileName, String imgFileName) throws Exception {
		String mpFilePath = inOutDir + "\\" + mpFileName;
		if (!(new File(mpFilePath)).exists())
			throw new Exception("" + mpFilePath + " does not exist.");
		Runtime rt = Runtime.getRuntime();
		String command = "cgpsmapper " + mpFilePath + " -o " + inOutDir + "\\" + imgFileName;
		System.out.println("-> " + command);
		Process pr1 = rt.exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr1.getInputStream()));
		
		BufferedReader stdError = new BufferedReader(new InputStreamReader(pr1.getErrorStream()));
		String line;
		System.out.print("cgpsmapper ");
		while(pr1.isAlive())
		{
			System.out.print(".");
			//Opróżnianie input, aby nie zablokować działania procesu
			while ((line = stdInput.readLine()) != null) {
//				System.out.println(" input -> " + line);
			}
			while ((line = stdError.readLine()) != null) {
				System.out.println(" error -> " + line);
			}
			Thread.sleep(1000);		
		}
		System.out.println("");
		String imgFilePath = inOutDir + "\\" + imgFileName;
		if (!(new File(imgFilePath)).exists())
			throw new Exception("" + imgFilePath + " does not exist.");
	}

	public void addToMapSource(
			String dstMapFid, 
			String outputDir, 
			String outputName, 
			String sourceImgs, 
			String srcMapSetTypPath, 
			int priority,
			boolean transparency) throws Exception {
		try {
			/*
			 * Usuwnie starego katalogu
			 */
			File f = new File(outputDir);
			if (f.exists())
				FileUtils.deleteDirectory(f);
			
			/*
			 * Zakładanie nowego katalogu
			 */
			FileUtils.forceMkdir(f);
		}
		catch (Exception e) {
			System.out.println("Directory error:"+"\n"+e.getMessage());
		}
		
		String command;
		Runtime rt = Runtime.getRuntime();;
		/*
		 * Tworzenie pliku mapset.img - istotne na pewno dla OSM, ale czy dla innych
		 */
		if (!"".equals(sourceImgs)){
			String mapsetFileName = "mapset";
			String dstTypFilePath = outputDir+"\\"+mapsetFileName+".img";			
			command = conacatMapSourceCommand(dstMapFid, outputDir, outputName, sourceImgs, srcMapSetTypPath);
			System.out.println("-> " + command);
			Process pr1 = rt.exec(command);
			//pr1.waitFor();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr1.getInputStream()));
			
			BufferedReader stdError = new BufferedReader(new InputStreamReader(pr1.getErrorStream()));
			String line;
			System.out.print("Creating map files ");
			while(pr1.isAlive())
			{
				System.out.print(".");
				//Opróżnianie input, aby nie zablokować działania procesu
				while ((line = stdInput.readLine()) != null) {
	//				System.out.println(" input -> " + line);
				}
				while ((line = stdError.readLine()) != null) {
					System.out.println(" error -> " + line);
				}
				Thread.sleep(1000);		
			}
			System.out.println("");
			InputStreamReader isr1 = new InputStreamReader(pr1.getInputStream());
			System.out.println("Map files created.");							
		
			/*
			 * Tworzenie mapset.img
			 */
			createMapsetImg(outputDir, mapsetFileName+".mp", mapsetFileName+".img");
			System.out.println("File 'mapset.img created using cgpsmapper.");
		}
		
		/*
		 * Deinstalacja starej mapy - nie da błędu jeśli stara mapa nie istnieje
		 */
		try {
			command = concatMapUninstallCommand(dstMapFid, outputName);
			System.out.println("-> " + command);
			Process pr2 = rt.exec(command);
			//pr2.waitFor();
			System.out.println("---> " + getInput(new InputStreamReader(pr2.getInputStream())));
			System.out.println("Odainstalowano mapę z rejestru.");
		}
		catch (Exception e) {
			System.out.println("Odinstalowywanie mapy: "+"\n"+e.getMessage());
		}

		/*
		 * Instalacja mapy
		 */
		try {
			command = concatMapInstallCommand0(dstMapFid, outputDir, outputName); 
			System.out.println("-> " + command);
			Process pr3 = rt.exec(command);
			pr3.waitFor();
			command = concatMapInstallCommand1(dstMapFid, outputDir, outputName, srcMapSetTypPath); 
			//System.out.println("-> " + getInput(new InputStreamReader(pr3.getInputStream())));
			System.out.println("-> " + command);
			pr3 = rt.exec(command);
			pr3.waitFor();
//			System.out.println("-> " + getInput(new InputStreamReader(pr3.getInputStream())));
			command = concatMapInstallCommand2(dstMapFid, outputDir, outputName); 
			System.out.println("-> " + command);
			pr3 = rt.exec(command);
			pr3.waitFor();
//			System.out.println("-> " + getInput(new InputStreamReader(pr3.getInputStream())));
			command = concatMapInstallCommand3(dstMapFid, outputDir, outputName); 
			System.out.println("-> " + command);
			pr3 = rt.exec(command);
			pr3.waitFor();
//			System.out.println("-> " + getInput(new InputStreamReader(pr3.getInputStream())));
			command = concatMapInstallCommand4(dstMapFid, outputDir, outputName); 
			System.out.println("-> " + command);
			pr3 = rt.exec(command);
			pr3.waitFor();
//			System.out.println("-> " + getInput(new InputStreamReader(pr3.getInputStream())));
			System.out.println("Map installed in registry.");
		}
		catch (Exception e) {
			System.out.println("Instalacja mapy: "+"\n"+e.getMessage());
		}
		/*
		 * Zmiana priorytetu mapy
		 */
		setMapProperties(concatOutputFilenamePath(outputDir, "*"), priority, transparency);
	}
	
	public void addToCard(
			String mapSetNumber, 
			String outputDir, 
			String outputName, 
			String sourceImgs, 
			String scrMapSetTypPath,
			int priority,
			boolean transparency) throws IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		System.out.println("Creating map file. This may take about a minute. Please wait.");
		String command = conacatGPSCommand(mapSetNumber, outputDir, outputName, sourceImgs, scrMapSetTypPath); 
		System.out.println("-> " + command);
		Process pr1 = rt.exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr1.getInputStream()));		
		BufferedReader stdError = new BufferedReader(new InputStreamReader(pr1.getErrorStream()));
		String line;
		System.out.print("Card ");
		while(pr1.isAlive())
		{
			System.out.print(".");
			while ((line = stdInput.readLine()) != null) {
				System.out.println(" input -> " + line);
			}
			while ((line = stdError.readLine()) != null) {
				System.out.println(" error -> " + line);
			}
			Thread.sleep(1000);	
		}
		System.err.println("");
		System.out.println("Map file created as '"+outputDir+"\\"+outputName+"'.");	
		/*
		 * Zmiana priorytetu mapy
		 */
		setMapProperties(concatOutputFilenamePath(outputDir, outputName), priority, transparency);
	}
	
	public void setMapProperties(String sourceImgs, int priority, boolean transparency) throws IOException, InterruptedException {
		if (priority<0 || priority>30)
			throw new IllegalArgumentException("Invalid map priority value: '" + priority + "'");
		String transp = transparency ? " -t" : " -n";			
		String command = "gmt -w" + transp + " -p " + priority + " " + sourceImgs;		
		Runtime rt = Runtime.getRuntime();
		System.out.println("Setting map priority to " + priority + " for '" + sourceImgs + "'");
		System.out.println("-> " + command);
		Process pr1 = rt.exec(command);
		pr1.waitFor();
		System.out.println("Map priority set.");
	}
}
