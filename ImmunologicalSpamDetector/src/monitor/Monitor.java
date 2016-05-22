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
import util.file.Email;
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
		String emailContent = PreprocessEmail.clearEmailContent((Email.readEmailContent(email)));
		String[] contentWords = emailContent.split(" ");
		float spamDetectionPercentageThreshold = Float.parseFloat(projectProperties.getProperty("spamDetectionPercentageThreshold"));
		int spamDetectionThreshold = Math.round(spamDetectionPercentageThreshold * this.detectors.size());
		
		int detectorMatchCount = 0;
		for(Detector detector : this.detectors){
			int matchCount = 0;
			for(String keyWord : detector.getKeywords()){
				for(String word : contentWords){
					if(Census.verifyTerms(keyWord, word)){
						matchCount++;
						break;
					}
				}
			}
			
			if(matchCount == detector.getKeywords().size()){
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
}
