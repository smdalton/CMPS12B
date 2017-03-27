#------------------------------------------------------------------------------
# A Makefile with macros
#------------------------------------------------------------------------------

JAVASRC=Chessmoves.java
SOURCES=README Makefile $(JAVASRC)
MAINCLASS=Chessmoves
CLASSES=Chessmoves.class MyLinkedList.class Node.class ChessPiece.class
JARFILE=Chessmoves.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)