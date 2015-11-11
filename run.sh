#!/bin/sh
USAGE="Usage: $0 [-h] [-d output_directory] [-s] ([-R input_directory]|filename)"
output_directory=output
input_directory=. 
recursive=false;
suppress=false
while getopts ":d:R:sh" opt;
do 
    case $opt in
        d) output_directory="$OPTARG";;
        s) suppress=true;;
        R) input_directory="$OPTARG"; recursive=true;;
        h) echo $USAGE;
           exit;;
        ?) echo "Error: unknown option -$OPTARG" ;
           echo $USAGE;
           exit 1
           ;;
    esac
done
if [ ! -d "$output_directory" ]; then
    mkdir "$output_directory"
fi
if [ -z "$1" ]; then
    if [ "$recursive" = false ]; then
        echo "No filename specified"
        echo "$USAGE"
        exit 1;
    fi
fi
shift $(($OPTIND - 1))

if [ "$recursive" = true ]; then
    for f in $input_directory/*.cs ; do
        echo $f
        if [ "$suppress" = true ]; then
            java -Dfile.encoding=UTF-8 -classpath bin:commons-cli-1.2.jar:gll.jar:multilexer.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler -D$output_directory $f &> /dev/null;
        else
            java -Dfile.encoding=UTF-8 -classpath bin:commons-cli-1.2.jar:gll.jar:multilexer.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler -d -D$output_directory $f &> $output_directory/$(basename $f).log;
        fi
    done
else
    if [ "$suppress" = true ]; then
        java -Dfile.encoding=UTF-8 -classpath bin:commons-cli-1.2.jar:gll.jar:multilexer.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler -D$output_directory $1 &> /dev/null;
    else
        java -Dfile.encoding=UTF-8 -classpath bin:commons-cli-1.2.jar:gll.jar:multilexer.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler -d -D$output_directory $1 &> $output_directory/$(basename $1).log;
    fi
fi
echo "Output directed to $(pwd)/$output_directory";
