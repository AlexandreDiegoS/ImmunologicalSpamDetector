package util;

import java.text.Normalizer;
import java.util.ArrayList;
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
	
	public static String clearEmailContent(String email){
		email = PreprocessEmail.clearHeader(email);
		
		String noStopWords = "";
		String[] wordsInEmail = email.toLowerCase().split(" ");
		List<String> stopWords = StopWords.getStopWords();
		
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
	
	public static List<String> clearEmailsContents(List<String> emailsContents){
		List<String> cleanEmailsContents = new ArrayList<String>();
		
		if(emailsContents != null){
			for(String emailContent : emailsContents){
				String preprocessedEmailContent = PreprocessEmail.clearHeader(emailContent);
				
				String nonStopWords = "";
				String[] wordsInEmail = preprocessedEmailContent.toLowerCase().split(" ");
				List<String> stopWords = StopWords.getStopWords();
				
				for(int i = 0; i < wordsInEmail.length; i++){
					if(stopWords.contains(wordsInEmail[i].toLowerCase())){
						wordsInEmail[i] = "";
					}
				}
				
				for(String word : wordsInEmail){
					nonStopWords += word + " ";
				}
				
				cleanEmailsContents.add(normalizeEmail(nonStopWords));
			}
		}
		
		return cleanEmailsContents;
	}
	
	public static String removeTagsHTML(String source) {
		char[] array = new char[source.length()];
		int arrayIndex = 0;
		boolean inside = false;
		for (int i = 0; i < source.length(); i++) {
		    char let = source.charAt(i);
		    if (let == '<') {
				inside = true;
				continue;
		    }
		    if (let == '>') {
				inside = false;
				continue;
		    }
		    if (!inside) {
				array[arrayIndex] = let;
				arrayIndex++;
		    }
		}
		return new String(array, 0, arrayIndex);
	}
	
	public static String normalizeEmail(String email){
		String normalizedEmail = "";
		String noTags = "";
		noTags = removeTagsHTML(email);
		normalizedEmail = Normalizer.normalize(noTags, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", " ");
		
		return normalizedEmail.replaceAll("[^a-zA-Z0-9]+"," ").replaceAll("\\s+", " ").trim().toLowerCase();
	}
}
