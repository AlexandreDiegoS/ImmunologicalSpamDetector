package util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpamDictionary {
	
	@SuppressWarnings("resource")
	public static List<String> getSpamDictionary() {
		List<String> spamDictionary = null;
		
		try {
			File spamDictionaryFile = new File("res/spamDictionary.txt");
			BufferedReader bReader = new BufferedReader(new FileReader(spamDictionaryFile));
			spamDictionary = new ArrayList<String>();
			
			while(bReader.ready()){
				spamDictionary.add(bReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spamDictionary;
	}
}
