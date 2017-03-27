


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


public class Chessmoves {
    public static int listLength;
    public static int boardSize;
    
    public static void main(String [] args) throws IOException{
        //handle input is really my main method...
        handleInput();
        
    }
    
    
    public static void handleInput() throws IOException{
        Scanner in = new Scanner(new File("input.txt"));
        //PrintWriter outfile = new PrintWriter(new FileWriter("output.txt",true));
        while(in.hasNextLine() ){
            String[] tokenizedLineOfPieces = in.nextLine().trim().split("\\s+");  //split the string to an array on spaces
            MyLinkedList  boardList = makeListFromText(tokenizedLineOfPieces);// make a list based on the information in this char array
            String queryLine = in.nextLine().trim() + " "; //2nd line in
            String[] queryTokens = queryLine.trim().split("\\s+");
            //for(String x:tokenizedLineOfPieces)System.out.println(x+"\n " );
            //boardList.traverse();
            performAnalysis(boardList, queryTokens);
        }
        in.close();
        //outfile.close();
        //System.out.println("Done reading in lines");
        
    }
    public static void performAnalysis(MyLinkedList boardList, String[] moveList)throws IOException {
        //find the "attacker" and the "defender
        PrintWriter outfile = new PrintWriter(new FileWriter("analysis.txt",true));
        int[] moves =  parseMoves(moveList);
        //System.out.println(boardList.checkMoves(moves));
        int[] aMove = new int[2];
        int boardSize = moves [0];
        //System.out.println(boardList.kingInCheck());
        //System.out.println("move analysis here");
        String checkResults = boardList.checkAnalysis();
        String results = boardList.checkMoves(moves);
        //System.out.println(boardList.checkAnalysis());
        outfile.println(results);
        outfile.println(checkResults);
        //System.out.println(results);
        //System.out.println(checkResults);
        outfile.close();
        //for(ChessPiece king:kingsArray)System.out.println(king.name+" "+king.col+" "+king.row);
    }
    
    private static int[] parseMoves(String[] moveList) {
        int [] listOfMoves = new int[moveList.length];
        //listOfMoves[0] = Integer.parseInt(moveList[0]);
        for (int i = 0; i < moveList.length; i++) {
            //return an easy to work with array
            listOfMoves[i] = Integer.parseInt(moveList[i]);
        }
        return listOfMoves;
    }
    
    public static MyLinkedList makeListFromText(String[] line){
        MyLinkedList list = new MyLinkedList();
        String name;
        int col,row;
        //int size = Integer.parseInt(line[0]);
        boardSize = Integer.parseInt(line[0]);
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
                    System.out.println("fall through switch case placement add"+ name);
                    break;
            }
        }
        return list;
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
    //this method checks the query location
    public ChessPiece checkQuery(int col,int row){
        if(head.nextNode!=null) {
            Node node = head;
            while(node != null) {
                if(node.data.col == col && node.data.row == row) {
                    //return the piece at that spot
                    return (node.data);
                }
                node = node.nextNode;
            }
        }
        return null; //return - for no result found
    }
    
    public ChessPiece[] findKings(){
        //System.out.println("Entering findKings");
        ChessPiece[] kings = new ChessPiece[2];
        kings[0]=null;
        kings[1]=null;
        if(head.nextNode!=null) {
            Node node = head;
            while(node != null) {
                if(node.data.name.equals("k")) {
                    kings[0]= node.data;
                }
                if(node.data.name.equals("K")) {
                    kings[1]= node.data;
                }
                node = node.nextNode;
            }
        }
        return kings;
    }
    
    void traverse() {
        if(head != null) {
            Node node = head;
            while (node !=null) {
                //will call chesspiece.isAttacking(query col, query row)
                System.out.println(node.data.name);
                node = node.nextNode;
            }
        }
    }
    
    //will progress along the mover's direction vector and test to see if the square
    //the mover is moving into is occupied, if this occurs before the mover reaches it's destination
    //it will return false
    
    
    public boolean squareIsOccupied(int col,int row) {
        ChessPiece foundPiece = checkQuery(col, row);
        if(foundPiece!=null) {
            return true;
        }
        return false;
    }
    
    public String checkMoves(int[] moves) {
        //for(int move:moves)System.out.print(move+" ");
        String results = new String("");
        for(int i = 0; i< moves.length;i+=4) {
            ChessPiece mover = checkQuery(moves[i],moves[i+1]);
            ChessPiece target;
            //continue because there is nothing in the square
            if(mover==null) {//no piece at input square return invalid
                //System.out.println("Invalid No Piece at starting square");
                return results+="Invalid";
            }
            //examine the proposed movement square
            if(squareIsOccupied(moves[i+2],moves[i+3])) {//if there is a piece there assign it to temp variable target here
                target = checkQuery(moves[i+2],moves[i+3]);
            }else {
                target = new ChessPiece(moves[i+2], moves[i+3], "x", "square");//if there is no piece found, assign the destination to a dummy chesspiece to hold the data of type "square"
                add(target);
            }
            
            if(clearPath(mover,target)) {//check to see if the path is clear with clearPath
                movePiece(mover,target);//if it is clear move the piece and then return valid move
                //System.out.println("Clear path from "+mover.name+" to "+target.name);
                results+="Valid ";
            }else {
                results += "Invalid ";//if it is not clear return invlid
                return results;
            }
        }return results;
    }
    private void movePiece(ChessPiece mover, ChessPiece target) {
        // TODO Auto-generated method stub
        mover.col = target.col;
        mover.row = target.row;
        //poor mans delete
        target.col = 150;
        target.row = 150;
        
        
    }
    //target is either a piece or a square depending on it's calling function
    private boolean clearPath(ChessPiece mover, ChessPiece target) {
        //check that the mover can attack the piece
        // my guess is trying to move a square that does'nt have a piece in it causing checkQuery error to propogate
        
        if(mover == null) System.out.println("mover is null");//clearpath is recieving a null argument from somewhere and throwing error.
        if(mover.isAttacking(target)) {
            //case where the target is the next square away above if statement will clear ambiguities
            //for the case of the night, if it is attacking there is no need to check the path
            if(mover.name.equals("n")||mover.name.equals("N")) return true;
            if(mover.name.equals("q")|| mover.name.equals("Q")|| mover.name.equals("k")||mover.name.equals("K")) {
                //System.out.print("Queens attack distance: ");
                //System.out.println(Math.round(Math.sqrt((Math.pow((mover.col - target.col),2) + Math.pow(mover.row-target.row, 2)))));
                if(Math.round(Math.sqrt((Math.pow((mover.col - target.col),2) + Math.pow(mover.row-target.row, 2))))<=2)
                    return true;
            }
            
            
            int moveCol = mover.col;
            int moveRow = mover.row;
            int[] mVec = mover.getMovementVector(target);
            //check for pieces in the way
            for(int i = 0;i<(Chessmoves.boardSize*3);i+=2){
                moveCol+=mVec[0];
                moveRow+=mVec[1];
                //System.out.println(moveRow+ " "+moveCol);
                //System.out.println(squareIsOccupied(moveCol,moveRow));
                if(squareIsOccupied(moveCol,moveRow)) {
                    ChessPiece foundPiece =	checkQuery(moveCol,moveRow);
                    //System.out.println(foundPiece.name);
                    if((foundPiece.col==target.col) && (foundPiece.row==target.row) && (foundPiece.name.equals(target.name))) {
                        return true;
                    }else return false;//this return false is reached when the piece found is not at the target destination
                }
                
            }return false; // for out of bounds
        }return false;// for not attackable
        
    }
    public String checkAnalysis() {
        ChessPiece[] kings = findKings();
        boolean kingAlreadyInCheck = false;
        //need to iterate across all of the pieces in the board and see which are attacking the king
        
        String returnThis = new String();
        for(ChessPiece king:kings) {
            //if(kingAlreadyInCheck) break;//exit if first king is already accounted for
            if(king == null) {
                System.out.println("Invalid king is null");
                continue;
            }
            Node current = head;
            while(current != null) {
                ChessPiece possibleAttacker = current.data;
                //if the attacker can attack the king and its path is clear return check
                //System.out.println(possibleAttacker.isAttacking(king));
                if(possibleAttacker.isAttacking(king) && clearPath(possibleAttacker,king)) {
                    if(king.name.equals("k")) returnThis+= "White in";
                    if(king.name.equals("K")) returnThis+= "Black in";
                    returnThis+=" check";
                    return returnThis;
                }
                current = current.nextNode;
            }
        }return "All kings safe";
    }
    
    
    public boolean aPieceIsAttacking(ChessPiece square) {
        Node current = head;
        while(current!=null) {
            if(current.data.isAttacking(square)){
                return true;
            }
        }return false;
    }
    
    
}



class Node {
    ChessPiece data;//this is the object
    Node nextNode;//this is the reference to the next link
    public Node(ChessPiece data){
        this.data = data;
    }
}


class ChessPiece{
    ChessPiece hasBeenAttacked;
    int col;
    int row;
    int curCol;
    int curRow;
    boolean hasAlreadyAttacked = false;
    String color;
    String name;
    int[] kMoves = {1,0,1,1,0,1,-1,1,-1,0,-1,-1,0,-1,1,-1};
    int[] bMoves = {1,1,-1,1,-1,-1,1,-1};
    int[] rMoves = {0,1,1,0,0,-1,-1,0};
    int[] qMoves = {1,0,1,1,0,1,-1,1,-1,0,-1,-1,0,-1,1,-1};
    ChessPiece(int col1 ,int row1,String color1, String name1){
        this.col = col1;
        this.row = row1;
        this.color = color1;
        this.name = name1;
    }
    //isAttacking method returns true if this chesspiece can attack target at tcol trow
    double dist(double x1, double y1, double x2, double y2) {
        return(Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2)));
    }
    //attempt to move to the target iteratively
    //return a 2 coordinate direction vector in direction of fastest distance decrease
    void update(int newCol,int newRow) {
        this.col = newCol;
        this.row = newRow;
    }
    boolean isAttacking(ChessPiece target) {
        //return false for when it tries to attack itself
        int pCol = target.col;
        int pRow = target.row;
        String targetCol = target.color;
        if((pRow == row) && (pCol == col)) return false;
        //return false if they are the same color
        if(targetCol.equals(color)) return false;
        if(name.equals("square")) {
            return false;
        }
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
                //System.out.println("fall through switch case placement"+ name);
                return false;
        }
        return false;
    }
    
    int[] getMovementVector(ChessPiece target) {
        //determine the direction which closes the greatest distance to the target based on the pieces type
        int tCol = target.col;
        int tRow = target.row;
        
        double dCol = tCol;
        double dRow = tRow;
        double aCol = col+0.0;
        double aRow = row+0.0;
        double shortDist = 100;
        double  newDist;// dummy value to establish set point
        //all possible movement states for each piece
        double[] kMoves = {1,0,1,1,0,1,-1,1,-1,0,-1,-1,0,-1,1,-1};
        double[] bMoves = {1,1,-1,1,-1,-1,1,-1};
        double[] rMoves = {0,1,1,0,0,-1,-1,0};
        double[] qMoves = {1,0,1,1,0,1,-1,1,-1,0,-1,-1,0,-1,1,-1};
        double[] atkVec = new double[2];
        int[] intAtkVec = new int[2];
        
        switch(name) {
            case "K": //compare and iterate the king movement direction vectors
                for(int i =0;i<kMoves.length;i+=2) {
                    newDist = dist(aCol+kMoves[i],aRow+kMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=kMoves[i];
                        atkVec[1]=kMoves[i+1];
                    }
                }
                break;
            case "k":
                for(int i =0;i<kMoves.length;i+=2) {
                    newDist = dist(aCol+kMoves[i],aRow+kMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=kMoves[i];
                        atkVec[1]=kMoves[i+1];
                    }
                }
                break;
            case "Q":
                
                for(int i =0;i<kMoves.length;i+=2) {
                    newDist = dist(aCol+qMoves[i],aRow+qMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=qMoves[i];
                        atkVec[1]=qMoves[i+1];
                    }
                }
                break;
            case "q":
                
                for(int i =0;i<qMoves.length;i+=2) {
                    newDist = dist(aCol+qMoves[i],aRow+qMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=qMoves[i];
                        atkVec[1]=qMoves[i+1];
                    }
                }
                break;
            case "R":
                for(int i=0; i< rMoves.length;i+=2) {
                    newDist = dist(aCol+rMoves[i],aRow+rMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=rMoves[i];
                        atkVec[1]=rMoves[i+1];
                    }
                }
                break;
            case "r":
                for(int i=0; i< rMoves.length;i+=2) {
                    newDist = dist(aCol+rMoves[i],aRow+rMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=rMoves[i];
                        atkVec[1]=rMoves[i+1];
                    }
                }
                break;
            case "B":
                for(int i=0; i< bMoves.length;i+=2) {
                    newDist = dist(aCol+bMoves[i],aRow+bMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=bMoves[i];
                        atkVec[1]=bMoves[i+1];
                    }
                }
                break;
            case "b":
                for(int i=0; i< bMoves.length;i+=2) {
                    newDist = dist(aCol+bMoves[i],aRow+bMoves[i+1],dCol,dRow);
                    if(newDist<shortDist) {
                        shortDist = newDist;
                        atkVec[0]=bMoves[i];
                        atkVec[1]=bMoves[i+1];
                    }
                }
                break;
            default:
                //System.out.println("doesnt work for knights yet"+ name);
                //System.out.println(" ");
                atkVec[0]=0;
                atkVec[1]=0;
        }
        intAtkVec[0] = (int)Math.round(atkVec[0]);
        intAtkVec[1] = (int)Math.round(atkVec[1]);
        
        return intAtkVec;
        
    }
}
