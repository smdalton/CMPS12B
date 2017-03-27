#include <stdio.h>
#include "IntegerLinkeDList.h"
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

int main(int argc, char* argv[]) {
    
    FILE * in;
    FILE * out;
    //input buffers
    char oneLine[256];
    char inString[500];
    
    if(argc != 3){
        printf("Usage <input file> <output file>");
        return(EXIT_FAILURE);
    }
    in = fopen("input.txt","r");
    if(in == NULL) {
        printf("error opening input file");
        return (EXIT_FAILURE);
    }
    out = fopen("output.txt","w");
    if(out == NULL){
        printf("Error opening output file");
        return(EXIT_FAILURE);
    }
    LinkedList dictionary;
    dictionary = newLinkedList();
    //make an input loop that accesses the command line arguments pass int
    int i = 1;
    while(1){
        if(fgets(oneLine,256,in)==NULL) break;
        int length = strlen(oneLine);
        //CASE INSERT
        if(oneLine[0] == 'i'){
            int numLength = strlen(oneLine)-1;
            int sum = 0;
            int finalNumber =0;
            for(int i=2; i<numLength;i++ ) {
                sum = oneLine[i] - '0';
                for (int j = (numLength - i); j > 1; j--) {
                    sum *= 10;
                }
                finalNumber += sum;
            }
            insert(finalNumber,dictionary);
            fprintf(out,"inserted %d\n", finalNumber);
            //call the list to insert final number
            //CASE FIND
        }else if(oneLine[0] == 'f'){
            int numLength = strlen(oneLine)-1;
            int sum = 0;
            int finalNumber =0;
            for(int i=2; i<numLength;i++ ) {
                sum = oneLine[i] - '0';
                for (int j = (numLength - i); j > 1; j--) {
                    sum *= 10;
                }
                finalNumber += sum;
            }
            //            20 not present
            //            21 present
            
            ///IN PROGRESS JUST PLACING FUNCTION CALL
            //NEED TO DO SOMETHING WITH RETURNED VALUE
            //CHECK IT FOR NULL FIRST
            fprintf(out, find(finalNumber, dictionary) != NULL ? "%d present\n" : "%d not present\n", finalNumber);
        }else if(oneLine[0] == 'd'){
            int numLength = strlen(oneLine)-1;
            int sum = 0;
            int finalNumber =0;
            for(int i=2; i<numLength;i++ ) {
                sum = oneLine[i] - '0';
                for (int j = (numLength - i); j > 1; j--) {
                    sum *= 10;
                }
                finalNumber += sum;
            }
            delete(finalNumber,dictionary);
        }
        else if(oneLine[0] == 'p'){
            printLinkedList(out,dictionary);
        }
        i++;
        
        //if(i>300) break;
    }
    fclose(in);
    fclose(out);
    return 0;
    
}
