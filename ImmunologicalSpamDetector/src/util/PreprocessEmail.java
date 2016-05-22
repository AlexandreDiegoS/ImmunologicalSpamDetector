package util;

import java.text.Normalizer;
import java.util.List;

import util.file.StopWords;

public class PreprocessEmail {
	public static String clearHeader(String email){
		String body = "";
		for(int i = 0; i < email.length(); i++){
			if(email.charAt(i) == '\n'){
				if(email.charAt(i+1) == '\n'){
					body = email.substring(i+2);
					break;
				}
			}
		}
		return body;
	}
	
	public static String clearEmail(String email){
		String noStopWords = "";
		String []wordsInEmail = email.toLowerCase().split(" ");
		List<String> stopWords = new StopWords().getStopWords();
		for(int i = 0; i < wordsInEmail.length; i++){
			if(stopWords.contains(wordsInEmail[i].toLowerCase())){
				wordsInEmail[i] = "";
			}
		}
		for(String word : wordsInEmail){
			noStopWords += word + " ";
		}
		return normalizeEmail(noStopWords);
	}
	
	public static String normalizeEmail(String email){
		String normalizedEmail = "";
		String noTags = "";
		noTags = email.replaceAll("\\<.*?>","");
		normalizedEmail = Normalizer.normalize(noTags, Normalizer.Form.NFD)
											.replaceAll("[^\\p{ASCII}]", " ");
		return normalizedEmail.replaceAll("[^a-zA-Z0-9 ]+"," ").
				replaceAll("\\s+", " ").trim().toLowerCase();
	}
}
