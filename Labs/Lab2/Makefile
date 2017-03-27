#------------------------------------------------------------------------------
# Another Makefile with macros Lab2
#------------------------------------------------------------------------------

JAVASRC    = FileTokens.java FileCopy.java FileReverse.java
SOURCES    = README Makefile $(JAVASRC)
MAINCLASS  = FileReverse
CLASSES    = FileTokens.class FileCopy.class FileReverse.class
JARFILE    = FileReverse.jar 

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)
