package ir.project1.tokenizer;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Map.Entry;

public class MainDriver {
//  public static  String DIRECTORY_PATH =  "/people/cs/s/sanda/cs6322/Cranfield";
	public static  String DIRECTORY_PATH = "C:/Study/IR/HW1/Cranfield";

	public static void main(String[] args) throws Exception {
		
		if (args.length >= 1) {
			DIRECTORY_PATH = args[0];
		} else {
			System.out.println("DIRECTORY_PATH = ");
			Scanner scn = new Scanner(System.in);
			DIRECTORY_PATH = scn.nextLine();
		}
		
		Tokenizer tokenizer = new Tokenizer(DIRECTORY_PATH);	
		long startTime = System.currentTimeMillis();
		Map<String, Integer> dictionary = tokenizer.buildDictionary();
		long endTime = System.currentTimeMillis();
	
		long noOfTokens = 0, noOfWords = 0, singleFreqWords = 0;
		for(Entry<String, Integer> entry : dictionary.entrySet()){
			noOfTokens += entry.getValue();
			noOfWords++;
			if(entry.getValue() == 1){
				singleFreqWords++;
			}
		}
		
		System.out.println("**************************************************************************");
		System.out.println("1. Time Taken = " + (endTime - startTime) + " milliseconds");
		System.out.println("2. The number of tokens = " + noOfTokens);
		System.out.println("3. The number of unique words = " + noOfWords);
		System.out.println("4. The number of words that occur only once = " + singleFreqWords);
		System.out.println("5. The average number of word tokens per document = "+ (noOfTokens/tokenizer.getNoOfScannedDocument()) );		
		System.out.println("**************************************************************************");
		System.out.println("6. The 30 most frequent words");
		
		TreeSet<Entry<String, Integer>> sortedSet = new TreeSet<Entry<String, Integer>>(new ValueComparator());
		sortedSet.addAll(dictionary.entrySet());
		Iterator<Entry<String, Integer>> iterator = sortedSet.descendingIterator();
		for(int i = 0 ; i < 30 && iterator.hasNext(); i++){
			System.out.println(iterator.next());
		}	
	}
}
