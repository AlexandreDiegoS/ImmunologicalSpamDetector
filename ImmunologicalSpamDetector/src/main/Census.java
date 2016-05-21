package main;

import java.util.ArrayList;
import java.util.List;

import util.Detector;

public class Census {
	
	public List<Detector> generateDetectors(int numOfDetectors){
		List<Detector> detectors = null;
		try{
			detectors = new ArrayList<Detector>();
		}catch(Exception e){
		}
		for(int i = 0; i < numOfDetectors; i++){
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
