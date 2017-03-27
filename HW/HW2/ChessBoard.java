

/*
 +---------------------+
 | Shane Dalton        |
 | smdalton@ucsc.edu   |
 | CMPS12B/M W2017     |
 | ChessBoard          |
 |                     |
 +---------------------+
 *------------------------------------------------------------------------------------------------------------------------------
 * SOURCES
 *	Print Help: //http://stackoverflow.com/questions/4389480/print-array-without-brackets-and-commas
 *  Linked List Tutorial: https://www.youtube.com/watch?v=VR363sIAvr4
 *  Lab 2 string parsing code filecopy.java
 *------------------------------------------------------------------------------------------------------------------------------
 * Description:
 * ChessBoard is a program that reads in an arbitrarily long set of strings contained in an input file inputs.txt
 * It parses these inputs as chesspieces and their locations and then instantiates their class defined objects
 * and inserts those objects into a linked list. The majority of the program code deals with functions that are
 * performed on the list these include:
 * 	-Insertion of chesspieces
 *  -Traversal of the list in order to search for attack interactions between pieces
 *  -Verification of board validity ( ensures that no two pieces are in the same square, and two different kings are present
 -------------------------------------------------------------------------------------------------------------------------------
 */
import java.io.*;
import java.util.Scanner;

public class ChessBoard {
    public static int listLength;




    public static void main(String [] args) throws IOException{

        handleInput();
    }

    /*HandleInput instantiates file object then reads lines in every two lines
     * after reading in a line and its assosciated query it sends query and char array
     * to makeListFromText()
     *
     */



    public static void handleInput() throws IOException{
        Scanner in = new Scanner(new File("input.txt"));
        PrintWriter outfile = new PrintWriter(new FileWriter("analysis.txt",true));
        while(in.hasNextLine() ){
            boolean sentinel = false;//this was a hack to alter control flow for specific case of invalid board
            MyLinkedList boardList;//initialize the list
            String line = in.nextLine();//first line in
            String[] tokenizedLineOfPieces = line.trim().split("\\s+");  //split the string to an array on spaces
            int kingCount = 0;
            int bKingCount= 0;
            for(String x:tokenizedLineOfPieces) {
                if(x.equals("k"))
                    kingCount++;
                if(x.equals("K"))
                    bKingCount++;
            }
            if(bKingCount != 1 || kingCount!= 1) {//if there is more than 1 king of each kind or not one king
                //outfile.println("Invalid"+" "+bKingCount+" "+kingCount);
                System.out.println("Invalid"+" "+bKingCount+" "+kingCount);
                sentinel = true;
            }
            boardList = makeListFromText(tokenizedLineOfPieces);// make a list based on the information in this char array

            String queryLine = in.nextLine().trim() + " "; //perform the same as above for 2nd input query lines
            String[] queryTokens = queryLine.trim().split("\\s+");

            //System.exit(1);
            int col = Integer.parseInt(queryTokens[0]);
            int row = Integer.parseInt(queryTokens[1]);
            if(boardList.isBoardValid()== true && sentinel == false) {
                String result = boardList.checkQuery(col,row);
                System.out.println(boardList.isBoardValid());
                System.out.println(result);
                outfile.print(result);
                //call search for attacks member function
                ChessPiece attackPair = boardList.findAttackers();
                if(attackPair != null) {//if it returns an object that is not null
                    String attacker = attackPair.name; //get the name of attacker
                    int attackerCol = attackPair.col;//column
                    int attackerRow = attackPair.row;//row
                    String attacked	= attackPair.hasBeenAttacked.name;//access the stored attackee object
                    int attackedCol = attackPair.hasBeenAttacked.col;
                    int attackedRow = attackPair.hasBeenAttacked.row;
                    outfile.println(" "+attacker+" "+attackerCol+" "+attackerRow+" "+attacked+" "+attackedCol+" "+attackedRow);
                }else outfile.print(" -"+"\n");
            }else{
                outfile.println("Invalid");
            }

        }

        in.close();
        outfile.close();
        System.out.println("Done reading in lines");
    }

    public static MyLinkedList makeListFromText(String[] line){
        MyLinkedList list = new MyLinkedList();
        String name;
        int col,row;
        //int size = Integer.parseInt(line[0]);

        for(int i = 1; i < line.length; i+=3) {
            name = line[i];
            col = Integer.parseInt(line[i+1]);
            row = Integer.parseInt(line[i+2]);
            //increment the counter to keep track of the list size of the linked list
            listLength++;
            //System.out.println(name);
            switch(name) {
                case "K":
                    list.add(new ChessPiece(col,row,"b","K"));
                    break;
                case "k":
                    list.add(new ChessPiece(col,row,"w","k"));
                    break;
                case "Q":
                    list.add(new ChessPiece(col,row,"b","Q"));
                    break;
                case "q":
                    list.add(new ChessPiece(col,row,"w","q"));
                    break;
                case "N":
                    list.add(new ChessPiece(col,row,"b","N"));
                    break;
                case "n":
                    list.add(new ChessPiece(col,row,"w","n"));
                    break;
                case "R":
                    list.add(new ChessPiece(col,row,"b","R"));
                    break;
                case "r":
                    list.add(new ChessPiece(col,row,"w","r"));
                    break;
                case "B":
                    list.add(new ChessPiece(col,row,"b","B"));
                    break;
                case "b":
                    list.add(new ChessPiece(col,row,"w","b"));
                    break;
                case "P":
                    list.add(new ChessPiece(col,row,"b","P"));
                    break;
                case "p":
                    list.add(new ChessPiece(col,row,"w","p"));
                    break;
                default:
                    System.out.println("fall through switch case placement"+ name);
                    break;
            }
        }
        return list;
    }
}
class Node {
    ChessPiece data;//this is the object
    Node nextNode;//this is the reference to the next link
    public Node(ChessPiece data){
        this.data = data;
    }
}
class MyLinkedList {
    int size = 0;
    Node head;//these are null until they reference something
    Node tail;
    //Add an element method
    public void add(ChessPiece data){
        Node node = new Node(data);
        //case 1 there is no list yet so head and tail must be referenced to a node
        if(tail == null) {
            //if head is null there is no list yet, so set head and tail both = to the new head
            head = node;
            tail = node;
        }else {
            tail.nextNode = node;
            tail = node;
        }
        size++;
    }
    //this method checks the query
    public String checkQuery(int col,int row){
        if(head.nextNode!=null) {
            Node node = head;
            while(node != null) {
                if(node.data.col == col && node.data.row == row) {
                    return (node.data.name);
                }
                node = node.nextNode;
            }
        }
        return "-"; //return - for no result found

    }
    void traverse() {
        if(head != null) {
            Node node = head;
            while (node.nextNode !=null) {
                //will call chesspiece.isAttacking(query col, query row)
                System.out.println(node.data.name);
                System.out.println(node.data.col);
                System.out.println(node.data.row);
                System.out.println(node.data.color);
                node = node.nextNode;
            }
        }
    }
    ChessPiece findAttackers() {
        if(head !=null)//make sure we have a list that has a head first
        {
            Node node = head;
            while(node != null) {//walk the list with an inner and outer loop to compare piece by piece
                Node node1 = head;
                while(node1 != null) {
                    //compare the inner chesspiece to outer chesspiece attack
                    String name = node.data.name;
                    String name1 = node1.data.name;
                        //the next part eliminates cases of self attack I think
                    if ( !name.equals(name1) && node1.data.isAttacking(node.data.col,node.data.row)&&node1.data.hasAlreadyAttacked==false){
                        node1.data.hasBeenAttacked = node.data;// encapsulate the victim in the attackers data fields
                        node1.data.hasAlreadyAttacked = true;
                        return node1.data;
                    }

                    node1 = node1.nextNode;
                }


                node = node.nextNode;
            }
        }
        return null;


    }
    //traverse and search here
    boolean isBoardValid() {
        if(head != null) {
            Node node = head;
            Node node1 = head;
            int appearanceCount=0;
            while(node1/*.nextNode*/ != null) {
                //this is the outer data for the comparison
                int qcol = node1.data.col;
                int qrow = node1.data.row;
                appearanceCount =0;
                node = head;
                while (node/*.nextNode*/ !=null) {
                    int comCol = node.data.col;
                    int comRow = node.data.row;
                    if(comCol==qcol && comRow == qrow)
                    {
                        appearanceCount++;
                        if(appearanceCount>1) {
                            System.out.println("Duplicate");
                            return false;
                        }
                    }

                    node = node.nextNode;
                }
                //System.out.println(qcol+" "+qrow);
                node1 = node1.nextNode;
            }
        }
        return true;
    }
}
class ChessPiece{
        ChessPiece hasBeenAttacked;
        int col;
        int row;
        boolean hasAlreadyAttacked = false;
        String color;
        String name;
        ChessPiece(int col1 ,int row1,String color1, String name1){
            this.col = col1;
            this.row = row1;
            this.color = color1;
            this.name = name1;
        }
        //isAttacking method returns true if this chesspiece can attack target at tcol trow
        boolean isAttacking(int pCol,int pRow) {
            //return false for when it tries to attack itself because it is retarded
            if(pRow == row && pCol == col)
                return false;

            switch(name) {
                case "K":if(
                            //firstColumn to left of king
                            ((pCol == col-1)&&((pRow == row-1)||(pRow == row)||(pRow == row+1)))
                            //column of king
                            ||((pCol == col)&&((pRow == row -1)||(pRow == row+1)))
                            //column to right of king
                            ||((pCol == col+1)&&((pRow == row-1)||(pRow == row)||(pRow == row +1))))
                {
                    return true;
                }
                    break;
                case "k":if(
                            //firstColumn to left of king
                            ((pCol == col-1)&&((pRow == row-1)||(pRow == row)||(pRow == row+1)))
                            //column of king
                            ||((pCol == col)&&((pRow == row -1)||(pRow == row+1)))
                            //column to right of king
                            ||((pCol == col+1)&&((pRow == row-1)||(pRow == row)||(pRow == row +1))))
                {
                    return true;
                }
                    break;
                case "Q":
                    //if the queen is on the same diagonal as the piece or the same row or the same column
                    if(pRow == row || pCol == col|| ((Math.abs(((pRow-row)*10)/(pCol-col)) == 10)))
                        return true;
                    break;
                case "q":
                    if(pRow == row || pCol == col|| ((Math.abs(((pRow-row)*10)/(pCol-col)) == 10)))
                        return true;
                    break;
                case "N":

                    if((((row - pRow)*(col-pCol))==2)||(((row - pRow)*(col-pCol))==-2))
                        return true;
                    break;
                case "n":
                    if((((row - pRow)*(col-pCol))==2)||(((row - pRow)*(col-pCol))==-2))
                        return true;
                    break;
                case "R":
                    if((pRow == row) || (pCol == col))
                        return true;
                    break;
                case "r":
                    if((pRow == row) || (pCol == col))
                        return true;
                    break;
                case "B":
                    if(((pCol-col) != 0)&&(Math.abs(((pRow-row)*10)/(pCol-col)) == 10))
                        return true;
                    break;
                case "b":
                    if(((pCol-col) !=0 ) && (Math.abs(((pRow-row)*10)/(pCol-col)) == 10))
                        return true;
                    break;

                case "P":
                    if((pRow ==(row-1)) && ((pCol == (col-1))||(pCol == (col+1))))
                        return true;
                    break;
                case "p":
                    if((pRow == (row+1))&& ((pCol == (col-1))||(pCol ==(col+1))))
                        return true;
                    break;
                default:
                    System.out.println("fall through switch case placement"+ name);
                    return false;
            }
            return false;
        }
    }
