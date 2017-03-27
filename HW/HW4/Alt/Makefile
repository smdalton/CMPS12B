JAVASRC=PriorityQueue.java Node.java QueueDriver.java
SOURCES=README Makefile $(JAVASRC)
MAINCLASS=QueueDriver
CLASSES=PriorityQueue.class Node.class QueueDriver.class
JARFILE=Priority.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest

$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)

clean:
	rm $(CLASSES) $(JARFILE)