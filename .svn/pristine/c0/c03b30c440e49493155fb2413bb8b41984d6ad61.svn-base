#!/bin/sh
if [ ! -d "output" ]; then
    mkdir output
fi
rm output/*;
printf "filename,Non-suppressed Derivation Trees,DPPF Node Count,AST Node Count\n" > "output/log.csv"
for f in *.cs ; do
		echo $f
		java -Dfile.encoding=UTF-8 -classpath ../bin:../commons-cli-1.2.jar:../gll.jar:../multilexer.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler -doutput/log.csv $f &> output/$(basename $f).log;
	done
