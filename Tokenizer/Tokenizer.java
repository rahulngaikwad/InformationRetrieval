package ir.project1.tokenizer;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeSet;

public class Tokenizer {
	
	private Map<String, Integer> dictionary;
	private String directoryPath;
	private int noOfScannedDocument; 

	public Tokenizer(String directoryPath) {
		this.directoryPath = directoryPath;
		this.dictionary = new HashMap<String, Integer>();
		this.noOfScannedDocument = 0;
	}

	public Map<String, Integer> buildDictionary() throws Exception {
		File directory = new File(directoryPath);
		FileParser fileParser = new FileParser();
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				noOfScannedDocument++;
				String plainText = fileParser.parse(file);
				plainText = plainText.replaceAll("[^\\w\\s-'.]+", "").toLowerCase();		
				Scanner scanner = new Scanner(plainText);
				while (scanner.hasNext()) {
					String token = scanner.next();	
					processToken(token);
				}
				scanner.close();
			}
		}
		return dictionary;
	}

	private void processToken(String token) {
		
		if(token.endsWith("'s")){
			addToDictionary(token.replace("'s", ""));
		} else if(token.contains("-")){
			splitAndAddToDictinay(token, "-");
		} else if(token.contains("_")){
			splitAndAddToDictinay(token, "_");
		} else {
			addToDictionary(token);
		}
			
	}

	private void splitAndAddToDictinay(String token, String splitBy) {
		String[] newTokens = token.split(splitBy);
		for(String newToken : newTokens){
			addToDictionary(newToken);
		}
	}

	private void addToDictionary(String token) {
		token = token.replaceAll("['.]+", "");
		if(token.length() > 0){
			if (dictionary.containsKey(token)) {
				dictionary.put(token, dictionary.get(token) + 1);
			} else {
				dictionary.put(token, 1);
			}
		}
	}
	
	public int getNoOfScannedDocument() {
		return noOfScannedDocument;
	}
}
