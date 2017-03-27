
public class Word implements Comparable<Word>{
	String word;
	Integer length;
	Integer freq;
	//int intLength = length;
	public Word(int freq, String word){
		this.freq = freq;
		this.word = word;
		//this.word.replaceAll("^", " ");
		this.length = word.length();
	}
	@Override
	//compare one word object to another by 
	public int compareTo(Word o) {
		// TODO Auto-generated method stub
		return this.freq.compareTo(o.freq);
	}
}
