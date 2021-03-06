This tool will parse a string in the C# 1.2 specification language, reduce the amount of ambiguity in the resulting SPPF, then convert the set of derivations into one or more abstract syntax trees. This tool was created as part of the [PLanCompS Project](http://plancomps.org/) and is the version included as part of the thesis.

Building
---------
Run

```bash
ant BuildAll
```

From the root folder on the command line

Running
--------

To run this tool from the command line, use the provided shell script with the following command line

```bash
./run.sh [-h] [-d output_directory] [-s] ([-R input_directory]|
filename)
```

* ```-h``` Displays the usage message

* ```-d``` Specifies $output_directory as the directory for file output

* ```-s``` Suppresses generation of log files and VCG files

* ```-R``` Specifies ```$input_directory``` as the directory where the input files are located. Program will run on all .cs files in this directory

* ```filename``` - should only be specified when ```-R``` flag is not set, and
specifies a single file to run the program on.

This will generate an *.ast file (and a *.vcg file if the -s flag is not present) in the folder "output". The ast file is a textual representation.

Trees in the ast file are of the form 

```
x_1(c_1(..) c_2(..) ... c_n(..))
```

`x_1,c_1,c_2,...,c_n` are node labels, which are non-terminals in the grammar or the names of terminals.  Terminals are surrounded by either double quotes or single quotes.

`c_1,c_2,...,c_n` are the children of `x_1`. `c_1,c_2,...,c_n` will have their own children. Node labelled with "ambig" are ambiguity nodes and represent places in which there are remaining ambiguities.

Credits
--------
* Parsers are generated by the [ART Parser Generator](http://link.springer.com/chapter/10.1007%2F978-3-642-19440-5_20) written by Adrian Johnstone
* The C# 1.2 Language Specification described by Microsoft Corporation in [ECMA-334 2nd Edition](http://www.ecma-international.org/publications/files/ECMA-ST-WITHDRAWN/ECMA-334,%202nd%20edition,%20December%202002.pdf) 
* The C# strings contained in testSuite are modifications of the [Mono 1.2 tests](https://github.com/mono/mono/tree/mono-1-2/mono/tests) as well as Lexer.cs from [LitJSON](https://lbv.github.io/litjson/)
* Command line arguments are handled by the [Apache Commons CLI 1.2 library](https://commons.apache.org/proper/commons-cli/)