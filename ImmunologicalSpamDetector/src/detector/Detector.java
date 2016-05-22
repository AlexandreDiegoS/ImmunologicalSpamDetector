package detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import util.file.ProjectProperties;
import util.file.SpamDictionary;

public class Detector {
	private List<String> keywords;
	
	public Detector() {
		this.keywords = new ArrayList<String>();
		List<String> spamDictionary = SpamDictionary.getSpamDictionary();
		int numberOfWordsInDictionary = spamDictionary.size();
		
		Random index = new Random();
		String word = "";
		int wordsOnDetector = 0;
		Properties projectProperties = ProjectProperties.getProperties();
		int sizeOfDetectors = Integer.parseInt(projectProperties.getProperty("sizeOfDetectors"));
		
		do{
			word = spamDictionary.get(index.nextInt(numberOfWordsInDictionary-1));
			if(!this.keywords.contains(word)){
				this.keywords.add(word);
				wordsOnDetector++;
			}
		}while(wordsOnDetector < sizeOfDetectors);
	}
	
	public Detector(List<String> keyWords) {
		this.keywords = keyWords;
	}
	
	public List<String> getKeywords() {
		return keywords;
	}
}
