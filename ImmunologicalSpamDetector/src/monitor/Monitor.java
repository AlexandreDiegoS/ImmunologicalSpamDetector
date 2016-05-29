package monitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import census.Census;
import detector.Detector;
import util.PreprocessEmail;
import util.file.IOManipulation;
import util.file.ProjectProperties;

public class Monitor {
	private List<Detector> detectors;
	
	public Monitor() {
		try {
			File acceptedDetectorsFile = new File("res/acceptedDetectors.txt");
			BufferedReader bufferedReader = new BufferedReader(new FileReader(acceptedDetectorsFile));
			List<String> lines = new ArrayList<String>();
			List<Detector> detectors = new ArrayList<Detector>();
			
			while(bufferedReader.ready()){
				lines.add(bufferedReader.readLine());
			}
			
			for(String line : lines){
				String[] keyWords = line.split("#");
				List<String> keyWordsList = new ArrayList<String>();
				
				for(String keyWord : keyWords){
					keyWordsList.add(keyWord);
				}
				
				detectors.add(new Detector(keyWordsList));
			}
			
			bufferedReader.close();
			this.detectors = detectors;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Monitor(List<Detector> detectors) {
		this.detectors = detectors;
	}

	public boolean verifyEmail(File email){
		boolean isValid = true;
		Properties projectProperties = ProjectProperties.getProperties();
		String emailContent = PreprocessEmail.clearEmailContent((IOManipulation.readEmailContent(email)));
		String[] contentWords = emailContent.split(" ");
		float spamDetectionPercentageThreshold = Float.parseFloat(projectProperties.getProperty("spamDetectionPercentageThreshold"));
		int spamDetectionThreshold = Math.round(spamDetectionPercentageThreshold * this.detectors.size());
		float keyWordMatchingPercentageThreshold = Float.parseFloat(projectProperties.getProperty("keyWordMatchingPercentageThreshold"));
		int keyWordMatchingThreshold = 0;
		
		int detectorMatchCount = 0;
		for(Detector detector : this.detectors){
			int matchCount = 0;
			for(String keyWord : detector.getKeywords()){
				for(String word : contentWords){
					if(Monitor.verifyTerms(keyWord, word)){
						matchCount++;
						break;
					}
				}
			}
			
			keyWordMatchingThreshold = Math.round(keyWordMatchingPercentageThreshold * detector.getKeywords().size());
			if(matchCount >= keyWordMatchingThreshold){
				detectorMatchCount++;
			}
		}
		
		if(detectorMatchCount >= spamDetectionThreshold){
			isValid = false;
		}
		
		return isValid;
	}
	
	public List<File> verifyEmails(List<File> emails){
		List<File> validEmails = new ArrayList<File>();
		
		for(File email : emails){
			if(this.verifyEmail(email)){
				validEmails.add(email);
			}
		}
		
		return validEmails;
	}
	
	public static boolean verifyTerms(String s1, String s2){
		boolean termsMatches = false;
		Properties projectProperties = ProjectProperties.getProperties();
		int hammingDistanceThreshold = Integer.parseInt(projectProperties.getProperty("hammingDistanceThreshold"));
		int hammingDistance = Census.hammingDistance(s1, s2);
		
		if(hammingDistance <= hammingDistanceThreshold){
			termsMatches = true;
		}
		
		return termsMatches;
	}
}
