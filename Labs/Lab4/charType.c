#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>


void extract_chars(char* s, char* a, char* d, char* p, char* w)
{
    char letter;
    int alpha=0;
    int digit=0;
    int punct=0;
    int wspace=0;
    int length = strlen(s);
    //initialize letter for the while loop

    for(int i=0;i<length;i++)//exits when it reaches end of line;
    {
        letter = *(s+i);
        //printf("The current char is: %c \n", letter);
        if(isalpha((int)letter))
        {
            *(a+alpha)= letter;
            alpha++;
        }
        if(isdigit((int)letter))
        {
            *(d+digit) = letter;
            digit++;
        }
        if(ispunct((int)letter))
        {
            *(p+punct) = letter;
            punct++;
        }
        if(isspace((int)letter))
        {
            *(w+wspace) = letter;
            wspace++;
        }
        //increment letter to the next memory index space in the string
    }
}

int main(int argc, char* argv[]){
    FILE* in;
    FILE* out;
    //buffer here
    char oneLine[256];
    int i=0;
    char inString[500];//input buffer
    int lineCount = 0;

    //input usage segment
    if(argc!=3){
        printf("Usage: <input file> <output file>\n");
        exit(EXIT_FAILURE);
    }
    in = fopen(argv[1],"r");
    if(in == NULL){
        printf("Error opening file");
        exit(EXIT_FAILURE);
    }
    out = fopen(argv[2],"w");
    if(out == NULL){
        printf("error opening output file");
        exit(EXIT_FAILURE);
    }

    int j = 0;

    while(1){
        lineCount++;
        if(fgets(oneLine,256,in)== NULL) break;
        int length = strlen(oneLine);
        int alphaCount,digitCount,punctCount,spaceCount = 0;
        char *W;
        char *A;
        char *D;
        char *P;
        A = calloc(alphaCount, sizeof(char));//alphabetical chars
        D = calloc(digitCount, sizeof(char));//digit chars
        P = calloc(punctCount, sizeof(char));//punctuation chars
        W = calloc(spaceCount, sizeof(char));//whitespace chars
        //call the function to extract the chars
        extract_chars(oneLine, A,D,P,W);

        fprintf(out,"Line %d contains:\n",lineCount);

        alphaCount = strlen(A);
        digitCount = strlen(D);
        punctCount = strlen(P);
        spaceCount = strlen(W);
        printf("alphalength%d",alphaCount);
        if(alphaCount>1 || alphaCount == 0)
            fprintf(out,"%d alphabetic characters: %s\n", alphaCount,A);
        else
            fprintf(out,"%d alphabetic character: %s\n", alphaCount,A);

        if(digitCount>1 || digitCount == 0)
            fprintf(out,"%d numeric characters: %s\n", digitCount,D);
        else
            fprintf(out,"%d numeric character: %s\n", digitCount,D);

        if(punctCount>1 || punctCount == 0)
            fprintf(out,"%d punctuation characters: %s\n",punctCount,P);
        else
            fprintf(out,"%d punctuation character: %s\n",punctCount,P);

        if(spaceCount>1 || spaceCount ==0)
            fprintf(out,"%d whitespace characters: %s\n",spaceCount, W);
        else
            fprintf(out,"%d whitespace character: %s\n",spaceCount, W);

        free(A);
        free(D);
        free(P);
        free(W);
        A= NULL;
        D= NULL;
        P= NULL;
        W= NULL;
        alphaCount = 0;
        digitCount = 0;
        punctCount = 0;
        spaceCount = 0;
    }
    fclose(in);
    fclose(out);
}
