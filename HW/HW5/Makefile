#------------------------------------------------------------------------------
# Makefile for HW5
#------------------------------------------------------------------------------

JAVASRC    = Word.java Bard.java
SOURCES    = Makefile $(JAVASRC)
MAINCLASS  = Bard
CLASSES    = Word.class Bard.class
JARFILE    = Bard.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm -f $(CLASSES) $(JARFILE) 
