package census;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import detector.Detector;
import util.PreprocessEmail;
import util.file.Email;
import util.file.IOManipulation;
import util.file.ProjectProperties;

public class Census {
	
	public static List<Detector> generateDetectors(){
		Properties projectProperties = ProjectProperties.getProperties();
		int numberOfDetectors = Integer.parseInt(projectProperties.getProperty("numberOfDetectors"));
		List<Detector> detectors = new ArrayList<Detector>();
		
		for(int i = 0; i < numberOfDetectors; i++){
			detectors.add(new Detector());
		}
		
		return detectors;
	}
	
	public static void evaluatesDetectors(List<Detector> detectors){
		List<Detector> acceptedDetectors = new ArrayList<Detector>();
		Properties projectProperties = ProjectProperties.getProperties();
		List<File> validEmails = IOManipulation.listFilesInDirectory(projectProperties.getProperty("validEmailsBasePath"));
		List<String> emailsContents = PreprocessEmail.clearEmailsContents(Email.readEmailsContents(validEmails));
		int discardThreshold = Integer.parseInt(projectProperties.getProperty("discardThreshold"));
		int spamIdentificationThreshold = Math.round(Float.parseFloat(projectProperties.getProperty("spamIdentificationPercentageThreshold")) * emailsContents.size());
		
		for(Detector detector : detectors){
			int emailsMatchCount = 0;
			
			for(String emailContent : emailsContents){
				String[] emailContentWords = emailContent.split(" ");
				int matchCount = 0;
				
				for(String keyWord : detector.getKeywords()){
					for(String emailContentWord : emailContentWords){
						if(keyWord.length() == emailContentWord.length()){
							if(Census.verifyTerms(keyWord, emailContentWord)){
								matchCount++;
								break;
							}
						}
					}
				}
				
				if(matchCount >= discardThreshold){
					emailsMatchCount++;
				}
			}
			
			if(emailsMatchCount < spamIdentificationThreshold){
				acceptedDetectors.add(detector);
			}
		}
		
		Census.storesDetectors(acceptedDetectors);
	}
	
	public static int hammingDistance(String s1, String s2){
		int hammingDistance = 0;
		
		if(s1.length() == s2.length()){
			for(int i = 0; i < s1.length(); i++){
				if(s1.charAt(i) != s2.charAt(i))
					hammingDistance++;
			}
		}else{
			return Math.max(s1.length(), s2.length());
		}
		
		return hammingDistance;
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
	
	private static void storesDetectors(List<Detector> acceptedDetectors){
		if(acceptedDetectors != null){
			try {
				File acceptedDetectorsFile = new File("res/acceptedDetectors.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(acceptedDetectorsFile, false));
				
				for(int i = 0; i < acceptedDetectors.size(); i++){
					Detector detector = acceptedDetectors.get(i);
					for(int j = 0; j < detector.getKeywords().size(); j++){
						if(j < detector.getKeywords().size() - 1){
							bufferedWriter.write(detector.getKeywords().get(j) + "#");
						}else{
							bufferedWriter.write(detector.getKeywords().get(j));
						}
					}
					
					if(i < acceptedDetectors.size() - 1){
						bufferedWriter.write("\n");
					}
				}
				
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
