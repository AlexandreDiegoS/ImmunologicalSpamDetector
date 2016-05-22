package census;

import java.util.ArrayList;
import java.util.List;

import detector.Detector;

public class Census {
	
	public List<Detector> generateDetectors(int numberOfDetectors){
		List<Detector> detectors = new ArrayList<Detector>();
		
		for(int i = 0; i < numberOfDetectors; i++){
			detectors.add(new Detector());
		}
		
		return detectors;
	}
	
	public static int hamming(String s1, String s2){
		int distance = 0;
		
		for(int i = 0; i < s1.length(); i++){
			if(s1.charAt(i) != s2.charAt(i))
				distance++;
		}
		
		return distance;
	}
}
