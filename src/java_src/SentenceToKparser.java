package java_src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import module.graph.ParserHelper;


class KparserOutput implements Runnable{
	   private Thread t;
	   private String threadName;
	   private int startIndex;
	   private int endIndex;
	   public static ParserHelper ph = new ParserHelper();
		
	   
	   KparserOutput(String name, int startIndex , int endIndex) {
	      threadName = name;
	      this.endIndex = endIndex;
	      this.startIndex = startIndex;
	      System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      
	      try{
	    	  String csvFile = "./data/amazon/amazon_sentences.csv";
	    	  String outFile = "./data/amazon/amazon_kparser.csv";
	    	  String line = "";
	          String cvsSplitBy = ",";
	          BufferedReader in = new BufferedReader(new FileReader(csvFile));
	          BufferedWriter out = new BufferedWriter(new FileWriter(outFile,true));
	          in.readLine();  
	          
	          while((line = in.readLine())!=null){
	        	  JSONObject json_obj = new JSONObject();
	        	  String kparser_input = line.split(cvsSplitBy)[1];
	        	  if(kparser_input.length() < 3){
	        		  continue;
	        	  }
	        	  String index = line.split(cvsSplitBy)[0];
	        	  if(Integer.parseInt(index) < startIndex){
	        		  continue;
	        	  }
	        	  if(Integer.parseInt(index) > endIndex){
	        		  break;
	        	  }
	        	  System.out.println(threadName + " " + index);
	        	  String json_output = ph.getJsonString(kparser_input, false);
	        	  json_obj.put("index", index);
	        	  json_obj.put("sentence", kparser_input);
	        	  json_obj.put("kparser", new JSONArray(json_output));
	        	  out.append(json_obj.toString());
	        	  out.newLine();
	          }
	  		in.close();
	  		out.close();
	      }catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      System.out.println("Thread " +  threadName + " exiting.");
	   }
	   
	   public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}

public class SentenceToKparser {
	
	public static ParserHelper ph = new ParserHelper();
	
	public static void main(String[] args) throws IOException, JSONException{
		//KparserOutput kThread1 = new KparserOutput( "Thread-1",0,100);
		//kThread1.start();
		
		//KparserOutput kThread2 = new KparserOutput( "Thread-2",101,200);
		//kThread2.start();
		
		//KparserOutput kThread3 = new KparserOutput( "Thread-3",201,300);
		//kThread3.start();
		
		KparserOutput kThread4 = new KparserOutput( "Thread-4",301,400);
		kThread4.start();
	    
		/*
		KL R2 = new RunnableDemo( "Thread-2");
		R2.start();
		*/
		
		//System.exit(0);
	}

}
