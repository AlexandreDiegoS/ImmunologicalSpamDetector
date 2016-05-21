package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpamWords {
	private List<String> spamDictionary;

	public SpamWords() {
		try {
			File spamDictionaryFile = new File("res/spamWords.txt");
			BufferedReader bReader = new BufferedReader(new FileReader(spamDictionaryFile));
			this.spamDictionary = new ArrayList<String>();
			
			while(bReader.ready()){
				this.spamDictionary.add(bReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getSpamDictionary() {
		return spamDictionary;
	}
	
	public void setSpamDictionary(List<String> spamDictionary) {
		this.spamDictionary = spamDictionary;
	}
}
