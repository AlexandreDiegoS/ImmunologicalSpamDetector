package util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Email {
	public static String readEmailContent(File file) {
		String content = "";
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			
			while(bReader.ready()){
				content += bReader.readLine() + "\n";
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static List<String> readEmailsContents(List<File> files){
		List<String> contents = new ArrayList<String>();
		
		if(files != null){
			for(File file : files){
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
					String content = "";
					
					while(bufferedReader.ready()){
						content += bufferedReader.readLine() + "\n";
					}
					
					contents.add(content);
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return contents;
	}

}
