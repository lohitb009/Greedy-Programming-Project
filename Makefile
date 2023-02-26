JC = javac
JR = java
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

.PHONY: run1 run2 run3 run4 clean 

all: $(classes)

$(classes): %.class: %.java
	$(JC) $<

run1: Solution_Strat1.class
	$(JR) Solution_Strat1

run2: Solution_Strat2.class
	$(JR) Solution_Strat2

run3: Solution_Strat3.class
	$(JR) Solution_Strat3

run4: Solution_Strat4.class
	$(JR) Solution_Strat4
	
run5: Solution_StratBonus.class
	$(JR) Solution_StratBonus

clean:
	rm -f *.class
