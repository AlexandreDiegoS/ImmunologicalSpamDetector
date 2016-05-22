package util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StopWords {
	
	@SuppressWarnings("resource")
	public List<String> getStopWords() {
		List<String> stopWords = null;
		
		try {
			File stopWordsFile = new File("res/stopWords.txt");
			BufferedReader bReader = new BufferedReader(new FileReader(stopWordsFile));
			stopWords = new ArrayList<String>();
			
			while(bReader.ready()){
				stopWords.add(bReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopWords;
	}
}
