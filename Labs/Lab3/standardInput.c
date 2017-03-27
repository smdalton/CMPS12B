
/*
 *   standardInput.c
 *   Reads three integers from stdin and prints them to stdout
 */
#include<stdio.h>
#include<stdlib.h>
int main(){
    int x, y, z;
    printf("Enter three integers separated by commas, then press return: ");
    scanf(" %d, %d, %d", &x, &y, &z);
    printf("The integers entered were %d, %d, %d\n", x, y, z);
    return EXIT_SUCCESS;
}
