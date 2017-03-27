charType: charType.o
	gcc -o charType charType.o
	rm charType.o

charType.o: charType.c
	gcc -c -std=c99 -Wall charType.c

clean:
	rm -f charType charType.o
