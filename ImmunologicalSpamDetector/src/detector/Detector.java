package detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import util.file.ProjectProperties;
import util.file.SpamDictionary;

public class Detector {
	private List<String> keywords;
	private List<String> spamDictionary;
	private int numberOfWordsInDictionary;
	
	public Detector() {
		this.keywords = new ArrayList<String>();
		this.spamDictionary = SpamDictionary.getSpamDictionary();
		this.numberOfWordsInDictionary = spamDictionary.size();
		
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
	
	public List<String> getKeywords() {
		return keywords;
	}
}
