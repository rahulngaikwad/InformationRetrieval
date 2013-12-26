
1. How to compile and run your program?
   copy all files to your local directory
   a. compile it using following command
   javac *.java 
   b. run using following command ( i.e java MainDriver <path of cranfield collection> )
   java MainDriver /people/cs/s/sanda/cs6322/Cranfield /people/cs/s/sanda/cs6322/hw3.queries
   c. see the output.
   
2.external libraries/Code used for doing this homework
  No external libraries but used Porter.java file for porter stemming  	

3. Design decision / How the program handles:
A. Upper and lower case words (e.g. "People", "people", "Apple", "apple");
Ans : converted all tokens to lowercase 
B. Words with dashes (e.g. "1996-97", "middle-class", "30-year", "tean-ager")
Ans : splitted token on dashes.
C. Possessives (e.g. "sheriff's", "university's")
Ans : removed all "'s" from token
D. Acronyms (e.g., "U.S.", "U.N.")
Ans : removed . and joined acronyms. e.g US , UN

3. Major algorithms and data structures.
	- Used SAXParser to extract text from the file.
	- Used HashMap for dictionary.
	- Used LinkedList for posting.

 
