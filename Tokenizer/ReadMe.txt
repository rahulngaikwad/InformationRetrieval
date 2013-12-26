1. How long the program took to acquire the text characteristics.
Ans : On an average program takes 1.8 second

2. How the program handles:
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
	- Used HashMap<String, Integer> for dictionary.
	- For each token, if dictionary do not contais that, 
	  add new token to dictionaly with count 1,
	  else increament count for that token
	- Used TreeSet to sort and print top 30 words.
******************************************************************************		
Output
**************************************************************************
1. Time Taken = 1944 milliseconds
2. The number of tokens = 235889
3. The number of unique words = 11427
4. The number of words that occur only once = 5422
5. The average number of word tokens per document = 168
**************************************************************************
6. The 30 most frequent words
the=19448
of=12714
and=6669
a=5923
in=4644
to=4560
is=4113
for=3491
are=2428
with=2263
on=1943
flow=1848
at=1834
by=1755
that=1570
an=1388
be=1271
pressure=1207
boundary=1156
from=1116
as=1113
this=1081
layer=1002
which=975
number=973
results=885
it=854
mach=824
theory=788
shock=712
