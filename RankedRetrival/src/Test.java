import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Test {
	public static  String QueryFile= "./hw3.queries.orig";
	static Pattern pattern = Pattern.compile("[Q0-9:]+");
	public static void main(String[] args) throws Exception{	
		
		List<String> querySet = readQueries(QueryFile);
		
		for(String query : querySet){
			System.out.println("Q:");
			System.out.println(query);	
		}

	}
	
	private static List<String> readQueries(String filename) throws Exception {
		String data = new String(Files.readAllBytes(new File(filename).toPath() ));
		String[] parts= pattern.split(data);
		List<String> queries = new ArrayList<>();
		for(String part : parts ){
			if(part.trim().length() > 0){
				queries.add(part.trim());
			}
		}
		return queries;
	}
}
