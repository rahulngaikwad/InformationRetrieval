
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

public class QueryProcessor {
	Map<String, IndexBuilder.DictionaryEntry> dictionary;
    Set<String> stopWords;
    DocumentTokenizer documentTokenizer;
    Map<Integer, IndexBuilder.DocumentInfo> documentDetails;
    double avgDoclength;
   
    
    public QueryProcessor(Map<String, IndexBuilder.DictionaryEntry> index,Map<Integer, 
    		IndexBuilder.DocumentInfo> documentDetails, Set<String> stopWords, int avgDoclength) throws Exception {
		this.dictionary = index;
		this.stopWords = stopWords;
		this.documentDetails = documentDetails;
		this.documentTokenizer = new DocumentTokenizer();
		this.avgDoclength = avgDoclength;
	}

	public void process(String query) throws Exception{
    	 Map<String, Integer> termFreqTable = documentTokenizer.buildTermFreqTable(query);
    	 removeStopWords(termFreqTable); 
    	 Map<Integer, Double> W1_table = new HashMap<>();
    	 Map<Integer, Double> W2_table = new HashMap<>();
    	 int queryLenght = getQueryLenght(termFreqTable);
    	 int collectionSize = documentDetails.size();
    	 for(String queryTerm : termFreqTable.keySet()){
    		 IndexBuilder.DictionaryEntry dictEntry = dictionary.get(queryTerm);
    		 if(dictEntry == null){
    			 continue;
    		 }
    		 
    		 int docFreq = dictEntry.docFrequency;
    		 for(IndexBuilder.PostingEntry postingEntry : dictEntry.postingList){
    			 int termFreq = postingEntry.frequency;
    			 int maxTermFreq = (int)documentDetails.get(postingEntry.docID).maxFreq; 
    			 int docLenght = documentDetails.get(postingEntry.docID).docLength.intValue();	
    			 
    			 double w1 = W1(termFreq,maxTermFreq,docFreq,collectionSize);
    			 double w2 = W2(termFreq,docLenght,avgDoclength,docFreq,collectionSize); 
    			 
    			 addWight(W1_table, postingEntry.docID, w1);
    			 addWight(W2_table, postingEntry.docID, w2);
    		 }
    	 }
    	 
    	 System.out.print("Stemmed Query: ");
    	 for(String queryTerm : termFreqTable.keySet()){
    		 System.out.print(queryTerm + " ");
    	 }
    	 System.out.println();
    	 System.out.println("\nTop ten document by W1");
    	 showTopTen(W1_table);
       	 System.out.println("\nTop ten document by W2");
    	 showTopTen(W2_table);
    }
	
	private int getQueryLenght(Map<String, Integer> termFreqTable) {
		int length = 0;
		for(String queryTerm : termFreqTable.keySet()){
			length += termFreqTable.get(queryTerm);
		}
		return length;
	}

	private void addWight(Map<Integer, Double> W_table, int docID, double w) {
		if(W_table.get(docID) == null){
			 W_table.put(docID, w);
			 return;
		 }
	  W_table.put(docID, w + W_table.get(docID) ); 
	}

	public double W1(int termFreq, int maxTermFreq, int docFreq, int collectionSize){
		double temp = 0;
		try {
			temp = ( 0.4 + 0.6 * Math.log (termFreq + 0.5) / Math.log (maxTermFreq + 1.0) ) *  (Math.log(collectionSize / docFreq) / Math.log(collectionSize)) ;
		} catch (Exception e) {
			temp = 0;
		}
		return temp;
	}

	public double  W2(int termFreq, int doclength, double avgDoclength, int docFreq, int collectionSize){
		double temp = 0;
		try {
			temp = (0.4 + 0.6 * (termFreq / (termFreq + 0.5 + 1.5 * (doclength / avgDoclength))) * Math.log (collectionSize / docFreq) / Math.log(collectionSize) );
		} catch (Exception e) {
			temp = 0;
		}
		return temp;
	}
	
	private void removeStopWords(Map<String, Integer> termFreqTable) {
		Iterator<String> iterator = termFreqTable.keySet().iterator();
		while(iterator.hasNext()){
			if(stopWords.contains(iterator.next())){
				iterator.remove();
			}
		}
	}
	

	private void showTopTen(Map<Integer, Double> w1_table) {
		TreeSet<Entry<Integer, Double>> sortedSet = new TreeSet<Entry<Integer, Double>>(new ValueComparator());
		sortedSet.addAll(w1_table.entrySet());
		System.out.println("Rank : " + "\t Weight   " + "    : " + " DocId" + " : " + "Document Name" + " : " + " Title");
		Iterator<Entry<Integer, Double>> iterator = sortedSet.iterator();
		for(int i = 0 ; i < 10 && iterator.hasNext(); i++){
			Entry<Integer, Double> entry = iterator.next();
			IndexBuilder.DocumentInfo documentInfo = documentDetails.get(entry.getKey());
			System.out.println((i+1) + " : " + entry.getValue() + " : " + documentInfo.docId + " : " + documentInfo.docName + " : " + documentInfo.title);
		}	
		
	}
	
   class ValueComparator implements Comparator<Entry<Integer, Double>> {
		@Override
		public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
			if(o1.getValue() < o2.getValue()){
				return 1;
			}	
		   return -1;
		}
	}
}
