package edu.asu.nlu.alzheimer.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.asu.nlu.alzheimer.helper.Utilities;
import module.graph.ParserHelper;

public class ExtractKparserGraphsInJSON {
	
	public static ParserHelper ph = null;
	
	public ExtractKparserGraphsInJSON(){
		ph = new ParserHelper();
	}

	
	public static void main(String[] args){
		String inFolder = "./input_folder/";
		String outFolder = "./kparser_parsed_out/";
		if(args.length > 1){
			inFolder = args[0];
			outFolder = args[1];
		}
		
		ArrayList<String> fileNames = Utilities.listFilesForFolder(new File(inFolder));
		
		for(String fileName : fileNames){
				System.out.println(fileName);
				try(BufferedReader in = new BufferedReader(new FileReader(inFolder+fileName));
						BufferedWriter out = new BufferedWriter(new FileWriter(outFolder+fileName))){
					String sent = null;
					while((sent = in.readLine())!=null){
						String parse = ph.getJsonString(sent, false);
						out.append(parse);
						out.newLine();
					}
					in.close();
					out.close();
				}catch(IOException e){
					e.printStackTrace();
				}
				
		}
	}
	
	
}
