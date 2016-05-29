package util.file;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IOManipulation {
	
	public static List<File> listFilesInDirectory(String directory) {
		List<File> files = null;
		try{
			final File folder = new File(directory);
			files = new ArrayList<File>();
		    if(folder.isDirectory()){
				for (File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			        	List<File> internFiles = listFilesInDirectory(fileEntry.toString());
			        	for (File file : internFiles) {
			        		files.add(file);
			        	}
			        } else {
			            files.add(fileEntry);
			        }
			    }
		    }else{
		    	files.add(folder);
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	    return files;
	}
	
	public static void saveContentsInFile(List<String> stringList, String filePath){
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(filePath));
			
			for(String string : stringList){
				bWriter.write(string + "\n");
			}
			bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> readFileContent(String filePath){
		List<String> fileContent = new ArrayList<String>();
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(new File(filePath)));
			while(bReader.ready()){
				fileContent.add(bReader.readLine());
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileContent;
	}
	
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
