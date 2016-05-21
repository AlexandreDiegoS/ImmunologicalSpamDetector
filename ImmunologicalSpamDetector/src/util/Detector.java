package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import files.SpamWords;

public class Detector {
	private List<String> keywords = new ArrayList<String>();
	private List<String> spamDictionary = new SpamWords().getSpamDictionary();
	private int numOfWordsInDictionary = spamDictionary.size();
	
	public Detector() {
		Random index = new Random();
		String word = "";
		int wordsOnDetector = 0;
		do{
			word = spamDictionary.get(index.nextInt(numOfWordsInDictionary-1));
			if(!this.keywords.contains(word)){
				this.keywords.add(word);
				wordsOnDetector++;
			}
		}while(wordsOnDetector < Properties.sizeOfDetectors);
	}
	public List<String> getKeywords() {
		return keywords;
	}
}
