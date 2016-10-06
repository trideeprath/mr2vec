package java_src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Sets;


public class Utilities {
	
	public static void saveObject(Object c, String name){
	    ObjectOutputStream oos = null;
	    try {
	        oos = new ObjectOutputStream(new FileOutputStream(name));
	    	oos.writeObject(c);
			oos.flush();
			oos.close();
	    } catch (FileNotFoundException e1) {
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
	}
	
	public static ArrayList<String> listFilesForFolder(File folder) {
		HashSet<String> listOfFiles = Sets.newHashSet();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				//listFilesForFolder(fileEntry);
			} else {
				listOfFiles.add(fileEntry.getName());
			}
		}
		return new ArrayList<String>(listOfFiles);
	}
	
	public static ArrayList<String> listFilesPaths(File folder) {
		HashSet<String> listOfFiles = Sets.newHashSet();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listOfFiles.addAll(listFilesPaths(fileEntry));
			} else {
				listOfFiles.add(fileEntry.getAbsolutePath());
			}
		}
		return new ArrayList<String>(listOfFiles);
	}
	
	public static Object load(String path){
		Object obj = null;
		try{
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}

}
