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

public class SentenceToKparser {
	

	public static ParserHelper ph = new ParserHelper();
	

	public static void main(String[] args) throws IOException, JSONException{

		String csvFile = "/home/trideep/PycharmProjects/mr2vec/data/amazon/amazon_sentences.csv";
		String outFile = "/home/trideep/PycharmProjects/mr2vec/data/amazon/amazon_kparser.csv";
        String line = "";
        String cvsSplitBy = ",";
		
		
		BufferedReader in = new BufferedReader(new FileReader(csvFile));
		BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		in.readLine();
		while((line = in.readLine())!=null){
			JSONObject json_obj = new JSONObject();
			String kparser_input = line.split(cvsSplitBy)[1];
			String index = line.split(cvsSplitBy)[0];
			String json_output = ph.getJsonString(kparser_input, false);
			json_obj.put("index", index);
			json_obj.put("sentence", kparser_input);
			json_obj.put("kparser", new JSONArray(json_output));
			out.append(json_obj.toString());
			out.newLine();
			if(Integer.parseInt(index) > 10){
				break;
			}
		}
		in.close();
		out.close();
		
		System.exit(0);
	}

}
