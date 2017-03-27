Dictionary: DictionaryClient.o Dictionary.o
	gcc -o Dictionary DictionaryClient.o Dictionary.o

DictionaryClient.o: DictionaryClient.c Dictionary.h
	gcc -c DictionaryClient.c

Dictionary.o: Dictionary Dictionary.h
	gcc -c Dictionary.c
clean:
	rm -f DictionaryClient.o Dictionary.o