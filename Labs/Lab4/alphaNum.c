/*
*  FileIO.c
*  Reads input file and prints each word on a separate line of
*  the output file.
*/
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<time.h>

enum { MAXLINES = 30 };

int main(int argc, char* argv[]){
   FILE* fp;  /* file handle for input */
   FILE* out; /* file handle for output */
   char buf[12000]; /* char array to store words from input file */
   int c;
   /* check command line for correct number of arguments */
   if( argc != 3 ){
      printf("Usage: %s <input file> <output file>\n", argv[0]);
      exit(EXIT_FAILURE);
   }

   /* open input file for reading */
   fp = fopen(argv[1], "r");
   if( fp==NULL ){
      printf("Unable to read from file %s\n", argv[1]);
      exit(EXIT_FAILURE);
   }

   /* open output file for writing */
   out = fopen(argv[2], "w");
   if( out==NULL ){
      printf("Unable to write to file %s\n", argv[2]);
      exit(EXIT_FAILURE);
   }
   printf("Starting : \n");
    while(fgets(buf, sizeof(buf), fp))
    {
      printf("The String is %s\n", buf);


    //do something with buf here;
    //The input of fgets will be NULL as soon
    //as its input fp has been fully read, then exit the loop
    }
    fclose(fp);
    fclose(fp);


   fclose(fp);
   fclose(out);

   return(EXIT_SUCCESS);
}
