package my.com.pl.srv;

import static my.com.pl.srv.common.StringSrv.Concat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.com.pl.component.TrackData;
import my.com.pl.component.TrailData;

@Service
public class MPSrv {
	private void AddHeader(List<String> lines, String id) {
		lines.add("[IMG ID]");
		lines.add("CodePage=1250");
		lines.add("LblCoding=9");
		lines.add("ID="+id);
		lines.add("Name=Trails");
		lines.add("Elevation=M");
		lines.add("Preprocess=F");
		lines.add("TreSize=511");
		lines.add("TreMargin=0.00000");
		lines.add("RgnLimit=127");
		lines.add("POIIndex=Y");
		//lines.add("Copyright=Mapcenter version - map cannot be sold|lshmctm0lcdl8*j2lk2");
		lines.add("Levels=8");
		lines.add("Level0=24");
		lines.add("Level1=23");
		lines.add("Level2=22");
		lines.add("Level3=21");
		lines.add("Level4=19");
		lines.add("Level5=15");
		lines.add("Level6=14");
		lines.add("Level7=13");
		lines.add("Zoom0=0");
		lines.add("Zoom1=1");
		lines.add("Zoom2=2");
		lines.add("Zoom3=3");
		lines.add("Zoom4=4");
		lines.add("Zoom5=5");
		lines.add("Zoom6=6");
		lines.add("Zoom7=7");
		lines.add("[END-IMG ID]");
		lines.add("[Countries]");
		lines.add("Country1=POLSKA~[0x1d]PL");
		lines.add("[END-Countries]");
	}
	
	@Autowired
	Converter conv;
	
	private String DifficultyToLineType(Integer difficulty) {
		return conv.DifficultyToLineType(difficulty.toString());
//		
//		switch (difficulty) {
//		case 1:	return "0x11";	//Access Road/Trail
//		case 2:	return "0x12";  //white - Easiest: Grade 1
//		case 3:	return "0x13";	//green - Easy: Grade 2
//		case 4:	return "0x14";  //blue - Intermediate: Grade 3
//		case 5:	return "0x15";  //double black- Expert: Grade 5		
//		case 6:	return "0x16";  //double orange - Extreme: Grade 6
//		case 7: return "0x17";  //white - Secondary Access Road/Trail
//		case 8: return "0x18";  //Extremely dangerous, pros only!
//		case 11: return "0x19"; //Advanced: Grade 4
//		default:
//			Assert(false, "Nieobsługiwana wartość difficulty: '" + difficulty + "'");
//		return "";
//		}
	}
	
	private String DifficultyToPoiType(Integer difficulty) {
		return conv.DifficultyToPoiType(difficulty.toString());
//		switch (difficulty) {
//		case 1:	return "0x21";	//Access Road/Trail
//		case 2:	return "0x22";  //white - Easiest: Grade 1
//		case 3:	return "0x23";	//green - Easy: Grade 2
//		case 4:	return "0x24";  //blue - Intermediate: Grade 3
//		case 5:	return "0x25";  //double black- Expert: Grade 5		
//		case 6:	return "0x26";  //double orange - Extreme: Grade 6
//		case 7: return "0x27";  //white - Secondary Access Road/Trail
//		case 8: return "0x28";  //Extremely dangerous, pros only!
//		case 11: return "0x29"; //Advanced: Grade 4
//		default:
//			Assert(false, "Nieobsługiwana wartość difficulty: '" + difficulty + "'");
//		return "";
//		}
	}
	
	//Geopunkt ma budowę (latitude, longitude)
	private String GetPoint(TrackData track, int idx) {
		return "(" + track.latitude.get(idx).toString() +"," + track.longitude.get(idx).toString() + ")";
	}
	
	private String GetPoi(TrailData trail) {
		return GetPoint(trail.trackData, 0);
	}
	
	private String GetLine(TrailData trail) {
		String result = "";
		for (int i = 0; i < trail.trackData.latitude.size(); i++) 		
			result = Concat(",", result, GetPoint(trail.trackData, i));
		return result;
	}
	
	private void AddTrail(List<String> lines, TrailData trail) {		
		//Wstawienie punktu startowego
		lines.add("[POI]");
		lines.add("Type="+DifficultyToPoiType(trail.difficulty));
		lines.add("Label="+trail.title);
		lines.add("EndLevel=9");
		lines.add("Data0=" + GetPoi(trail));
		lines.add("[END]");		
		
		//Wstawienie linii
		lines.add("[POLYLINE]");
		lines.add("Type="+DifficultyToLineType(trail.difficulty));
		lines.add("Label="+trail.title);
		lines.add("EndLevel=9");
		lines.add("Data0=" + GetLine(trail));
		lines.add("[END]");				
	}
	
	public List<String> getMpLines(List<TrailData> trails){
		List<String> result = new ArrayList<>();
		AddHeader(result, "75010001");
		for (TrailData trail : trails) 
			AddTrail(result, trail);
		return result;
	}
}
