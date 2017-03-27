//Shane Dalton smdalton
//CMPS12B W2017
//NQueens Solver

/*
 PROGRAM DESCRIPTION
 
 Given and input row in the first column, and a board size the following program will output the solution
 to the problem of placing N-queens on an nxn chesboard such that no queen is under "threat" from any other queen
 This program illustrates the use of backtracking, where each recursion will attempt to solve the whole problem
 and if unsuccesful, will "backtrack" the least number of steps necessary to solve the remainder of the problem.
 
 Sources:
 Data Abstraction and Problem Solving with Java: Walls and Mirrors (2nd Edition) Ch. 6 (implementation of recursive algorithm described in book)
 
 Useful implmentation of threat detection algorithm was easier to debug than my slope intercept form
 http://algorithms.tutorialhorizon.com/backtracking-n-queens-problem/
 
 Googles big picture article
 https://developers.google.com/optimization/puzzles/queens
 
 */

import java.util.Scanner;
import java.io.*;


class NQueens {
    static boolean[][] board;
    static int size;
    
    //This is the primary method that implements the recursive search for the placement of all n queens
    static boolean placeQueen(int col){
        if (col>size-1)//Base Case: exit if all the columns have been filled with Queens
            return true;
        for (int row=0;row<size;row++){ //iterate up the current column row by row
            if(isSafe(row,col)){//if the current row in the column is safe then
                board[row][col]=true; //place a queen at current position:= board[row][column
                if(placeQueen(col+1))//if the next queen has also been placed successfully
                    return true;//return true
                board[row][col]=false;//if the recursion operating on the next column returns false
                //no successful placement of the queen is possible in the next column given the present
                //row that is being iterated by for loop so on next iteration, inspect row + 1 and see if
                //another suitable path can be found
            }
        }//if isSafe returns false, execution continues to here and returns false
        return false; // if all rows have been exhausted return false to the parent recursive function
        //which will in turn increment it's row and continue forward recursion ( or exit on no solution )
    }
    
    //Takes two input arguments representing the row and column of a queen, and checks iteratively all possible positions that
    //could attack its argument position for the presense of a queen. If such a threat is present, returns false
    static boolean isSafe(int row,int col){
        int i,j;
        //search though all columns in the argument "row" passed into the function until reaching last row
        for(i=0;i<col;i++){
            if (board[row][i]==true)
                return false;
        }
        
        //only need to search the Queens behind the current position because no Queens exist ahead
        //search the negative diagonal from the target queen position down and to the "left"
        for(i=row,j=col;(i>=0)&&(j>=0);i--,j--){
            //step through both iterators simultaneously to get a line of slope 1
            if (board[i][j]==true)
                return false;
        }
        //search diagonal that originates from current pos and up and to the "left"
        for(i=row,j=col;(j>=0)&&(i<size);i++,j--){
            //step through both iterators simultaneously to get a line of slope -1
            if (board[i][j]==true)
                return false;
        }
        return true;
    }
    //Printing method reads through array and putputs ordered pair coordinates of the queens after beeing solved
    static void printBoard()throws IOException{
        PrintWriter fileout = new PrintWriter("solution.txt");
        for (int i=0;i<size;i++) {
            //System.out.print("\n");
            for (int j=0;j<size;j++) {
                if(board[i][j] == true){
                    fileout.println( (j+1)+ " " +(i+1));
                }
            }
        }
        //System.out.print("\n");
        fileout.close();
    }
    
    public static void main(String[] args)throws IOException {
        //PrintWriter fileWrite = new PrintWriter("solution.txt");
        size = Integer.parseInt(args[0]); // global
        int firstQueen = Integer.parseInt(args[2]);
        //if firstQueens > size send error message !!!!!!!!
        board = new boolean[size][size];
        if(Integer.parseInt(args[1]) == 1 && size >= firstQueen){
            //int i,j;
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++){
                    board[i][j]=false;
                }
            }
            //set user selectable queen in board + zero index offset EDIT
            //EXPERIMENTAL
            board[firstQueen-1][0] = true;
            if (placeQueen(1)) //When the last case returns true all the way back from the final column
                printBoard();
            else {
                //print this to file also
                PrintWriter fileWrite = new PrintWriter("solution.txt");
                //System.out.println("No Solution");
                fileWrite.println("No solution");
                fileWrite.close();
            }
        }else{
            PrintWriter fileWrite = new PrintWriter("solution.txt");
            //System.out.println("No Solution");
            fileWrite.println("No solution");
            fileWrite.close();
        }
    }
}
