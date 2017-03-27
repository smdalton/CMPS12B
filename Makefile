# My first simple Makefile
#It starts here and checks for pre-requisites.
#checkk fails for NQueens because NQueens.class does
# not exist yet
#that dependency is met
#flow
#NQueens --> checks for NQueens.class in directory fails --> next makefile block Target is NQueens.java

NQueens: NQueens.class
	echo Main-class: NQueens > Manifest
	jar cvfm NQueens.jar Manifest NQueens.class
	rm Manifest

NQueens.class: NQueens.java
	javac -Xlint NQueens.java

clean:
	rm NQueens.jar NQueens.class Manifest solution.txt
