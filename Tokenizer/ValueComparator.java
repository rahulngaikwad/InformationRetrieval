package ir.project1.tokenizer;

import java.util.Comparator;
import java.util.Map.Entry;

public class ValueComparator implements Comparator<Entry<String, Integer>> {
	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		if(o1.getValue() < o2.getValue()){
			return -1;
		}	
	   return 1;
	}
}
