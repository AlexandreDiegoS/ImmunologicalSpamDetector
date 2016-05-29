package census;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import detector.Detector;
import monitor.Monitor;
import util.PreprocessEmail;
import util.file.IOManipulation;
import util.file.ProjectProperties;

public class Census {
	
	public static void generateSpamDictionary(){
		Properties projectProperties = ProjectProperties.getProperties();
		String spamsBasePath = projectProperties.getProperty("spamsBasePath");
		float frequentWordPercentageThreshold = Float.parseFloat(projectProperties.getProperty("frequentWordPercentageThreshold"));
		int frequentWordThreshold = 0;
		int biggerOccurrence = Integer.MIN_VALUE;
		List<String> spamContentList = IOManipulation.readEmailsContents(IOManipulation.listFilesInDirectory(spamsBasePath));
		spamContentList = PreprocessEmail.clearEmailsContents(spamContentList);
		Map<String, Integer> wordOccurrenceMap = new LinkedHashMap<String, Integer>();
		List<String> mostFrequentWords = new ArrayList<String>();
		
		for(String spamContent : spamContentList){
			String[] wordArray = spamContent.split(" ");
			
			for(String word : wordArray){
				if(word.length() > 4){
					if(wordOccurrenceMap.containsKey(word)){
						int occurrence = wordOccurrenceMap.get(word) + 1;
						wordOccurrenceMap.put(word, occurrence);
						
						if(occurrence > biggerOccurrence){
							biggerOccurrence = occurrence;
							frequentWordThreshold = Math.round(biggerOccurrence * frequentWordPercentageThreshold);
						}
					}else{
						wordOccurrenceMap.put(word, 1);
					}
				}
			}
		}
		
		for(String word : wordOccurrenceMap.keySet()){
			if(wordOccurrenceMap.get(word) >= frequentWordThreshold){
				mostFrequentWords.add(word);
			}
		}
		
		IOManipulation.saveContentsInFile(mostFrequentWords, projectProperties.getProperty("spamDictionaryFile"));
	}
	
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
		List<String> emailsContents = PreprocessEmail.clearEmailsContents(IOManipulation.readEmailsContents(validEmails));
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
							if(Monitor.verifyTerms(keyWord, emailContentWord)){
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
