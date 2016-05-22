package main;

import java.util.List;

import census.Census;
import detector.Detector;

public class ImmunologicalSpamDetector {

	public static void main(String[] args) {
		List<Detector> detectors = new Census().generateDetectors(2);
		List<String> keywords;
		for(int i = 0; i < 2; i++){
			keywords = detectors.get(i).getKeywords();
			for(int j = 0; j < keywords.size(); j++)
				System.out.println(keywords.get(j));
		}
		new Census();
		System.out.println(Census.hamming("sexo", "amor"));
	}

}
