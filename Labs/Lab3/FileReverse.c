//FileIO.c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define HELLO_STRING "Hello World!\n"
//access the memory space directly and manipulate the string
void stringReverse(char* s){
    int j = strlen(s)-1;
    char first,second;
    int i=0;
    while(i<j){
        first = s[i];//temp to retain data between swaps
        second = s[j];//temp to retaind data between swaps
        s[i] = second;//swap
        s[j] = first;//swap
        i++;
        j--;
    }
}

int main(int argc, char* argv[])
{
    FILE* in;
    FILE* out;
    char word[256];
    
    if(argc!=3){
        printf("Usage %s <input file> <output file>\n",argv[0]);
        exit(EXIT_FAILURE);
    }
    in = fopen(argv[1], "r");
    if(in == NULL){
        printf("Unable to read from input file %s \n",argv[1]);
        exit(EXIT_FAILURE);
    }
    out = fopen(argv[2],"w");
    if(out == NULL){
        printf("unable to open file %s for writing \n",argv[2]);
        exit(EXIT_FAILURE);
    }
    while(fscanf(in," %s ",word)!=EOF){
        
        stringReverse(word);
        printf("Reversed word is: %s\n",word);
        fprintf(out, "%s\n",word);
    }
    fclose(in);
    fclose(out);
    
    return(EXIT_SUCCESS);
}