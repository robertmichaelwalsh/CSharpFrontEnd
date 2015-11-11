#!/bin/sh
if [ ! -d "abstractOutput" ]; then
    mkdir abstractOutput
fi
rm abstractOutput/*;
printf "filename,Non-suppressed Derivation Trees,DPPF Node Count,AST Node Count\n" > "abstractOutput/log.csv"
for f in *.cs ; do
        echo $f
        java -Dfile.encoding=UTF-8 -classpath ../bin:../commons-cli-1.2.jar:../gll.jar:../multilexer.jar uk.ac.rhul.csle.tooling.CSCompiler.CSAbstractCompiler -dabstractOutput/log.csv $f &> abstractOutput/$(basename $f).log;
    done
