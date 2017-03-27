

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class QueueDriver {
    
    public static void main(String[] args) throws IOException{
        
        String line ="(42, 10) d (23, 1) d (32, 10) d (31, 10) (42, 10) d";
        getInput();
        
        //q.traverse();
    }
    
    public static void getInput() throws IOException{
        
        Scanner in = new Scanner(new File("input.txt"));
        PrintWriter outfile = new PrintWriter(new FileWriter("output.txt",true));
        
        while(in.hasNextLine()) {
            PriorityQueue q = new PriorityQueue();
            String line = in.nextLine();
            System.out.println(line);
            line = line.replaceAll("[\\(\\)\\,]", "");
            String[] tokenized = line.trim().split("\\s+");
            for(int i = 0; i < tokenized.length;i++) {
                String s = tokenized[i];
                //System.out.println(s);
                if(s.equals("d")) {
                    int deletedVal = q.deleteMax();
                    outfile.print(deletedVal+ " ");
                    //System.out.println("deleting max := "+ deletedVal );
                }else{
                    int val		 = Integer.parseInt(tokenized[i]);
                    int priority = Integer.parseInt(tokenized[i+1]);
                    q.add(val,priority);
                    i++;
                }
            }
            outfile.println();
        }
        outfile.close();
        in.close();
    }
    
}
