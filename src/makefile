JFLAGS = -g
JC = javac
JARFLAGS = cvfm
JAR = jar

DV = DynamicVoting

OBJECTS = Main.class core/*.class net/*.class log/*.class
META = META-INF/MANIFEST.MF

INSTALL = mv

DynamicVoting: $(OBJECTS)
	$(JAR) $(JARFLAGS) $(DV).jar $(META) $(OBJECTS)

Main.class: Main.java
	$(JC) $(JFLAGS) Main.java

core/*.class:

net/*.class:

log/*.class:

clean:
	$(RM) *.class $(DV).jar

install:
	$(INSTALL) $(DV).jar ..

distclean: clean
	$(RM) ../$(DV).jar

.PHONY: all install clean
