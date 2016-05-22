package util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Email {
	public String getEmail(File filename) {
		String email = "";
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(filename));
			
			while(bReader.ready()){
				email += bReader.readLine();
				email += "\n";
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return email;
	}

}
