JC =javac
OUT_DIR = ./class/

.SUFFIXES:.java .class

.java.class:
	$(JC) -d $(OUT_DIR) $*.java

CLASSES =\
	MainOne.java\
	MainTwo.java\
	MainThree.java\
	MainFour.java

default:CLASSES

classes:$(CLASSES:.java=.class)

clean:
	rm ./class/*.class
