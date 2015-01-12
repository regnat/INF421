JavaC=javac
JavaI=time java
SRC=$(wildcard *.java)
OBJ=$(SRC:.java=.class)
SH=sh
SHELL=bash

all: testGcds testProductTrees

build: $(OBJ)

testGcds: FactorGcds genKeys.class

testProductTrees: decrypt.sh FactorProductTree genKeys.class
	$(SH) $< weakKeys.txt files/bigsecret.txt

FactorGcds: FindWeakKeysByPGCD.class FileParser.class KeyWriter.class
	$(JavaI) FindWeakKeysByPGCD files/unfactoredKeys/keys100.txt
	$(JavaI) FindWeakKeysByPGCD files/unfactoredKeys/keys1000.txt

FactorProductTree: FindWeakKeysByProductTrees.class FileParser.class KeyWriter.class
	$(JavaI) FindWeakKeysByProductTrees files/unfactoredKeys/keys100.txt
	$(JavaI) FindWeakKeysByProductTrees files/unfactoredKeys/keys1000.txt
	$(JavaI) FindWeakKeysByProductTrees files/unfactoredKeys/keys10000.txt
	$(JavaI) FindWeakKeysByProductTrees files/unfactoredKeys/keys100000.txt

%.class: %.java
	$(JavaC) $^

cleanKeys:
	rm keys/*
	rm weakKeys.txt
	rm decrypted.txt

cleanByteCode:
	rm -r *.class

clean: cleanKeys cleanByteCode
