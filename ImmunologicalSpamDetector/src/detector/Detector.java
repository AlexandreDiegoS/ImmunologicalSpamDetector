package detector;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import util.file.IOManipulation;
import util.file.ProjectProperties;

public class Detector {
	private List<String> keywords;
	
	public Detector() {
		this.keywords = new ArrayList<String>();
		Properties projectProperties = ProjectProperties.getProperties();
		List<String> spamDictionary = IOManipulation.readFileContent(projectProperties.getProperty("spamDictionaryFile"));
		int numberOfWordsInDictionary = spamDictionary.size();
		
		Random index = new Random();
		String word = "";
		int wordsOnDetector = 0;
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
