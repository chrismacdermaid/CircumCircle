
VERSION = 0.1

JFLAGS = -g
JC = javac
JAR = jar
JARFLAGS = 

.PHONY: clean test

SRC := $(wildcard *.java)
CLASSES:=$(patsubst %.java,%.class,$(wildcard *.java))

default: server 

classes : $(SRC)
	$(JC) $(JFLAGS) $(SRC)

server: classes
	${JAR} cvfe CCServer_${VERSION}.jar Server ${CLASSES}

clean:
	$(RM) *.class *.jar

test: classes 
	java Circumcircle
