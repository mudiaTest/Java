package my.com.pl.srv;

import static my.com.pl.srv.common.AssertionSrv.Assert;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.pl.config.TrailForksEnv;

@Service
public class Converter {

	@Autowired
	TrailForksEnv tfv;
	
	//Konwerter difficulty level na Line Type
	private Map<String, String> difficultyToLineTypeMap;
	private Map<String, String> getDifficultyToLineTypeMap() {
		if (difficultyToLineTypeMap == null) {
			difficultyToLineTypeMap = new HashMap<>();
			for(int i = 0; i < tfv.getDifficultyLvl().length; i++)
				difficultyToLineTypeMap.put(tfv.getDifficultyLvl()[i], tfv.getLineType()[i]);
		}
		return difficultyToLineTypeMap;
	}	
	public String DifficultyToLineType(String diffLvl) {
		Map<String, String> conv = getDifficultyToLineTypeMap();
		Assert(conv.containsKey(diffLvl), "Brak klucza " + diffLvl + " w konweterze difficultyToLineTypeMap."); 
		return conv.get(diffLvl);		
	}
	
	//Konwerter difficulty level na Poi Type
	private Map<String, String> difficultyToPoiTypeMap;
	private Map<String, String> getDifficultyToPoiTypeMap() {
		if (difficultyToPoiTypeMap == null) {
			difficultyToPoiTypeMap = new HashMap<>();
			for(int i = 0; i < tfv.getDifficultyLvl().length; i++)
				difficultyToPoiTypeMap.put(tfv.getDifficultyLvl()[i], tfv.getPoiType()[i]);
		}
		return difficultyToPoiTypeMap;
	}	
	public String DifficultyToPoiType(String diffLvl) {
		Map<String, String> conv = getDifficultyToPoiTypeMap();
		Assert(conv.containsKey(diffLvl), "Brak klucza " + diffLvl + " w konweterze difficultyToPoiTypeMap."); 
		return conv.get(diffLvl);	
		
	}
}
