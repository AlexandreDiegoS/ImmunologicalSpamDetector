package util.file;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class IOManipulation {
	
	public List<File> listFilesInDirectory(String directory) {
		List<File> files = null;
		try{
			final File folder = new File(directory);
			files = new ArrayList<File>();
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		        	List<File> internFiles = listFilesInDirectory(fileEntry.toString());
		        	for (File file : internFiles) {
		        		files.add(file);
		        	}
		        } else {
		            files.add(fileEntry);
		        }
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	    return files;
	}
}
