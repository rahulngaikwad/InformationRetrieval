

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MainDriver {
//  public static final String DIRECTORY_PATH =  "/people/cs/s/sanda/cs6322/Cranfield";
//  public static final String STOP_WORD_FILE_NAME = "C:/Study/IR/HW2/resourcesIR/stopwords";
	public static  String DIRECTORY_PATH ; //= "C:/Study/IR/HW2/Cranfield";
	public static  String STOP_WORD_FILE_NAME = "./stopwords";

	public static void main(String[] args) throws Exception {
		
		if(args.length < 1 ){
			System.out.println("Please enter path of cranfield collection");
			Scanner scanner = new Scanner(System.in);
			DIRECTORY_PATH = scanner.nextLine();
		} else {
			DIRECTORY_PATH = args[0];
		}
		
		
		Set<String> stopWords = readStopWords(STOP_WORD_FILE_NAME);
		IndexBuilder indexBuilder = new IndexBuilder(stopWords);	
		
		long startTime = System.currentTimeMillis();
		Map<String, IndexBuilder.DictionaryEntry> uncompressedIndex =  indexBuilder.buildIndex(DIRECTORY_PATH);
		long endTime = System.currentTimeMillis();			
		
		long uncompressedLenght = Utility.getSizeOfUnCompressedIndex(uncompressedIndex);
				
		Map<String, Utility.DictionaryEntry> compressedIndex = Utility.createCompressedIndex(uncompressedIndex);
		long compressedLenght = Utility.getSizeOfCompressedIndex(compressedIndex);	
		
		System.out.println("**************************************************************************");
		System.out.println("1. Time required to build index = " + (endTime - startTime) + " milliseconds");
		System.out.println("2. Size of the index uncompressed = " + uncompressedLenght + " bytes");
		System.out.println("3. Size of the index compressed   = " + compressedLenght + " bytes");
		System.out.println("4. number of inverted lists in the index = " + uncompressedIndex.size());
		System.out.println("5. term,df,tf,and inverted list length (in bytes) for the given terms ");
		System.out.println(String.format("\n\t %-10s  %-10s %-10s %-10s bytes", "Term", "DF", "TF", "Inverted List Length"));		
		
		String[] terms = { "Reynolds", "NASA", "Prandtl", "flow", "pressure", "boundary", "shock"};
		Porter porterStemmer = new Porter();
		for(String term : terms){
			String stemmedTerm = porterStemmer.stripAffixes(term.toLowerCase());
			IndexBuilder.DictionaryEntry  entry = uncompressedIndex.get(stemmedTerm);		
			System.out.println(String.format("\t %-10s  %-10d %-10d %-10d bytes",stemmedTerm,entry.docFrequency,entry.termFrequency,Utility.getSizeOfCompressedPostingList(compressedIndex.get(stemmedTerm).postingList)));
		}
	}

	private static Set<String> readStopWords(String filename) throws FileNotFoundException {
		Set<String> stopWords = new HashSet<>(); 
		Scanner scanner = new Scanner(new File(filename));
		while(scanner.hasNext()){
			stopWords.add(scanner.next());
		}
		scanner.close();
		return stopWords;
	}
}
