package my.com.pl.srv;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class UnzipService {
	private int setUnzipedSize(String fileNamePath) throws Exception {
		Runtime rt = Runtime.getRuntime();
		String command = "7z.exe l -slt " + fileNamePath;
		Process pr1 = rt.exec(command);			
		BufferedReader reader = new BufferedReader(new InputStreamReader(pr1.getInputStream()));
		String line;
		List<String> lines = new ArrayList<>();
		List<String> pathLines = new ArrayList<>();
		List<String> sizeLines = new ArrayList<>();
		boolean prvIsPath = false;
		while ((line = reader.readLine()) != null) {
//		System.out.println("-> "+line);	
		   if (line.startsWith("Path = ") && !line.equals("Path = "+fileNamePath)) {
			   pathLines.add(line);
			   prvIsPath = true;
		   }			   
		   else if (line.startsWith("Size = ") && prvIsPath) {
			   line = line.equals("Size = ") ? "Size = 0" : line;
			   sizeLines.add(line);	
			   prvIsPath = false;
		   }
		   else
			   prvIsPath = false;
		}
		pr1.waitFor();		
		Integer result = 0;
		if (pathLines.size() == sizeLines.size()) {
			result = sizeLines.stream().map((s)->Integer.parseInt(s.split(" = ")[1])).mapToInt(i->i).sum();
		}
		else
			throw new Exception("Błąd obliczania rozmiaru archiwum: " + pathLines.size() + " / " + sizeLines.size());
		return result;
	}
	
	public synchronized void unzip7Zip(String fileNamePath, Path dstDirPath) throws Exception {
		FileUtils.deleteDirectory(new File(dstDirPath.toString()));
		Runtime rt = Runtime.getRuntime();

		Integer unzippedSize = setUnzipedSize(fileNamePath);
		String command = "7z.exe e -o" + dstDirPath + " " + fileNamePath;
		System.out.println("Unzip using command: " + command);
		Process pr2 = rt.exec(command);		
		while(pr2.isAlive()) {
			wait(1000);				
			File folder = new File(dstDirPath.toString());
		    long actSize = FileUtils.sizeOfDirectory(folder);	
		    if (unzippedSize == 0 || actSize > unzippedSize)
		    	System.out.println("Decompression in progress. Unable to compute percentage.");
		    else
				System.out.println("Done " + Math.floor(actSize*100/unzippedSize) + "%");
		}
		System.out.println();
	}
}
