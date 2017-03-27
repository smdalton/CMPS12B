#------------------------------------------------------------------------------
# A Makefile with macros
#------------------------------------------------------------------------------

JAVASRC=ChessBoard.java
SOURCES=README Makefile $(JAVASRC)
MAINCLASS=ChessBoard
CLASSES=ChessBoard.class MyLinkedList.class Node.class ChessPiece.class
JARFILE=Chessboard.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)
	rm analysis.txt