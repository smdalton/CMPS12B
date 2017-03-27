//FileReverse Reverses file

import java.io.*;
import java.util.Scanner;

public class FileReverse{
  public static void main(String[] args) throws IOException{
    Scanner in = null;
    PrintWriter out = null;
    String lineToTokenize = null;
    String[] token = null;
    int n;
    //throws an exception if less then two args are given
    if(args.length < 2){
      System.err.println("Usage: FileReverse infile outfile");
      System.exit(1);
    }
    //assigns the command line args
    in = new Scanner(new File(args[0]));
    out = new PrintWriter(new FileWriter(args[1]));


    //loop that use in object constructor to set the input delimiting to newline.
    in.useDelimiter("\n");
    while(in.hasNext()){
      lineToTokenize = in.next() +" ";//assigns the input from file into a string
      token = lineToTokenize.split("\\s+");//split on space into string array
      for(int i=0; i<token.length; i++){
        n = token[i].length();
        out.println(stringReverse(token[i],n));
      }
    }
    in.close();
    out.close();
  }

  //recieves a single token and its length and then recursively prints it backwards
  public static String stringReverse(String s, int n){
    if(n>0){
      return  s.charAt(n-1) + stringReverse(s,n-1);
    }
    return "";
  }
}
