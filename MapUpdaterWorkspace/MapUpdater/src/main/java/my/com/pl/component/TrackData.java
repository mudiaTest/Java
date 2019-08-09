package my.com.pl.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;

public class TrackData {
	public List<Float> latitude = new ArrayList<>();
	public List<Float> longitude = new ArrayList<>();
	public List<Float> altitude = new ArrayList<>();
	
	public void FillFromRaw(RawTrackData raw) {
		latitude  = Arrays.asList(raw.latitude.split(",") ).stream().map(o->Float.parseFloat(o.toString())).collect(Collectors.toList());
		longitude = Arrays.asList(raw.longitude.split(",")).stream().map(o->Float.parseFloat(o.toString())).collect(Collectors.toList());
		altitude = Arrays.asList(raw.altitude.split(",")).stream().map(o->Float.parseFloat(o.toString())).collect(Collectors.toList());
	}
}
