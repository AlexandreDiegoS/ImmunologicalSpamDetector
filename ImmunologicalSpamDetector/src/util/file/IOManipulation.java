package util.file;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

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
}
