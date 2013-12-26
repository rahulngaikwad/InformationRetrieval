

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class MainDriver {
//  public static final String DIRECTORY_PATH =  "/people/cs/s/sanda/cs6322/Cranfield";
//  public static final String STOP_WORD_FILE_NAME = "C:/Study/IR/HW2/resourcesIR/stopwords";
	public static  String DIRECTORY_PATH = "C:/Study/IR/HW2/Cranfield";
	public static  String STOP_WORD_FILE_NAME = "./stopwords";
	public static  String QueryFile= "./hw3.queries";

	public static void main(String[] args) throws Exception {
		
		if(args.length < 2 ){
			System.out.println("Please enter path of cranfield collection");
			Scanner scanner = new Scanner(System.in);
			DIRECTORY_PATH = scanner.nextLine();
			System.out.println("Please enter full pathname of query file");
			QueryFile = scanner.nextLine();
			scanner.close();
		} else {
			DIRECTORY_PATH = args[0];
			QueryFile = args[1];
		}
	
		
		Set<String> stopWords = readStopWords(STOP_WORD_FILE_NAME);
		IndexBuilder indexBuilder = new IndexBuilder(stopWords);	
		
		long startTime = System.currentTimeMillis();
		Map<String, IndexBuilder.DictionaryEntry> uncompressedIndex =  indexBuilder.buildIndex(DIRECTORY_PATH);
		long endTime = System.currentTimeMillis();
		int avgDocLenght = indexBuilder.getAvgDocLength();
		
		long lenghtWithoutCompression = Utility.getSizeOfUnCompressedIndex(uncompressedIndex);
				
		Map<String, Utility.DictionaryEntry> compressedIndex = Utility.createCompressedIndex(uncompressedIndex);
		long lenghtWithCompression = Utility.getSizeOfCompressedIndex(compressedIndex);	
		
		System.out.println("**************************************************************************");
		System.out.println("1. Time required to build index = " + (endTime - startTime) + " milliseconds");
		System.out.println("2. Size of the index uncompressed = " + lenghtWithoutCompression + " bytes");
		System.out.println("3. Size of the index compressed   = " + lenghtWithCompression + " bytes");
		System.out.println("4. number of inverted lists in the index = " + uncompressedIndex.size());
		QueryProcessor queryProcessor = new QueryProcessor(uncompressedIndex, indexBuilder.documentDetails, stopWords, avgDocLenght);
		List<String> querySet = readQueries(QueryFile);
		for(int i = 0; i < querySet.size(); i++){
			System.out.println("\nQuery"+(i+1)+ " : " + querySet.get(i) );
			queryProcessor.process(querySet.get(i));
		}
		
	}

	private static List<String> readQueries(String filename) throws Exception {
		String data = new String(Files.readAllBytes(new File(filename).toPath() ));
		String[] parts= Pattern.compile("[Q0-9:]+").split(data);
		List<String> queries = new ArrayList<>();
		for(String part : parts ){
			String query = part.trim().replaceAll("\\r\\n", " ");
			if(query.length() > 0){
				queries.add(query);
			}
		}
		return queries;
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
