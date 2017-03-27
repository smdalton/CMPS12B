import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/*
 +---------------------+
 | Shane Dalton        |
 | Thooba Samimi       |
 |                     |
 | CMPS12B/M W2017     |
 | IndexTheBard        |
 |                     |
 +---------------------+
 *------------------------------------------------------------------------------------------------------------------------------
     * SOURCES
     stackOverflow, javadocs
 *------------------------------------------------------------------------------------------------------------------------------
     Parses shakespeares collected works into a hashmp, zips the key:value pairs into an array of word objects, and performs analysis based on inputs
 *-------------------------------------------------------------------------------------------------------------------------------
 */



public class Bard {
	public static void main(String[] args) throws IOException{
	
	Map<String, Integer> filledMap = indexTheBard();//populate hashmap
	List<Word> wordList = fillArray(filledMap);//convert Hashmap to sortable array
	generateOutput(wordList,filledMap);
	
	
}	
	//CALLED BY MAIN
	//here we are going to process the queries and do meaningful analysis of them
	private static void generateOutput(List<Word> wordList, Map<String, Integer> map) throws IOException {
		//read the input file
		Scanner input = new Scanner(new File("input.txt"));
		PrintWriter outfile = new PrintWriter(new FileWriter("analysis.txt",true));
		while(input.hasNextLine()){
			//make a string with input
			String queryLine = input.nextLine().trim();
			String[] queryTokens = queryLine.trim().split("\\s+");
			//outfile.println(analyzeQuery(wordList,map,queryTokens));
            //  System.out.println(analyzeQuery(wordList,map,queryTokens));         
			//send the string off to a function to turn it into an output
			outfile.println(analyzeQuery(wordList,map,queryTokens));
			//write that ouput of the function
		}
		//cleanup
		input.close();
		outfile.close();
	}
	
	//CALLED BY generateOutput
	//Decides if query is for a word freqeuncy, or the mth most words of length n
	private static String analyzeQuery(List<Word> wordList, Map<String, Integer> map, String[] queryTokens) {
		//Here we are going to check if it is numeric or string
		String result = "";
		if (queryTokens[0].matches("[-+]?\\d*\\.?\\d+")) {//number match
			//search the list for the l-th long words of n-frequency
			int wordLen = Integer.parseInt(queryTokens[0]);//word length
                        System.out.println(wordLen);
			int wordNumber = Integer.parseInt(queryTokens[1]);
                        System.out.println(wordNumber);
			for(Word currentWord:wordList) {
				if(currentWord.length.equals(wordLen)) {
					result+= (currentWord.word + " ");
					wordNumber --;
					if(wordNumber<1)
						{
							return result;
						}
				}
			}
			String notFound = "0";
			return notFound;
		}else {//word
			//find the frequency of a single word.
			if (map.get(queryTokens[0]) == null){
                            return "0";
                        }else{
			System.out.println(queryTokens[0]);
			return map.get(queryTokens[0])+"";
                        }
		}
	}
	
	//CALLED BY MAIN
	//index the bard will index all of shakespeares works
	//Reads in the skakespeare file, and stores it in a hashmap
	public static Map<String,Integer> indexTheBard() throws IOException{
		Map<String, Integer> filledMap = new LinkedHashMap<String, Integer>();//populate hashmap
		Scanner in = new Scanner(new File("Shakespeare.txt"));
		//input processing loop
		//Use of .replaceAll combined with the filter expression, replaces targeted chars with '""' where 
		//'""' == no character, thus removing all targeted patterns from the .
		while(in.hasNextLine()) {
			String line = in.nextLine();
			//System.out.println(line);
			line = line.replaceAll("[^\\s+A-Za-z'-]", "");
			line = line.replaceAll("( )+"," ");
			line = line.trim();
			String[] finalSentence = line.split(" ");
			String result = "";
			for(String word:finalSentence) {
				if (!word.equals(word.toUpperCase())) {
					result+=word.toLowerCase()+ " ";
				}
			}
			//System.out.println(line);
            String[] strings = result.split("\\s");
			fillMap(strings,filledMap);
		}
		in.close();
		return filledMap;
		
	}
	
	//populates the hashmap object and counts frequency in the following format
	//HASHMAP(key:value) =  (word:wordCount)
	// When map is queried with word, it will return the count of that word, wordCount
	public static void fillMap(String[] strings, Map<String, Integer> map){
		  for(String word:strings){
			  if(!map.containsKey(word)) {
			  map.put(word,1);//if first count of word, init to 1
			  }
			  else {
				  map.put(word, map.get(word)+1);//otherwise take current freq and add 1 to it
			  } 
		  }
	}
	
	
	//Conversion from Hashmap to array is necessary for sorting in order to query from user input
	//This is all handled using Java generic types, due to ease of implementation and a highly optimal
	//Conversion from hashmap to array, doing this by hand would create a garbage pile
	//LinkedHashMap guarantees in-order storage of keys and values, so that position is always preserved
	//In this way it is possible to look at the set of keys, and set of values and they retain a 1-1
	//relationship, and can be stored into two separate arrays, and then combined into one array for sorting
	
	public static List<Word> fillArray(Map<String, Integer> map) {
		List<String> keyList = new ArrayList<String>(map.keySet());//holds the words
		List<Integer> valueList = new ArrayList<Integer>(map.values());//holds the frequencies of words
		List<Word> wordList = new ArrayList<Word>();//holds both frequencies and words 
		Iterator<String> keyIt = keyList.iterator();//to iterate the words
		Iterator<Integer> valueIt = valueList.iterator();//to iterate the frequencies
		while (keyIt.hasNext()&&valueIt.hasNext()) {//iterate over all ordered pairs and store them in word objects
			
			wordList.add(new Word(valueIt.next(),keyIt.next()));
		}
		//sorting by word length implemented by custom class override in word.java under function: compareTo
		Collections.sort(wordList);
		Collections.reverse(wordList);
		return wordList;
	}
	
}


