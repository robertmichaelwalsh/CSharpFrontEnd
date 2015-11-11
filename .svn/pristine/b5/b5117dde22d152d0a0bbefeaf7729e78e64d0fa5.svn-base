package uk.ac.rhul.csle.tooling.CSCompiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import uk.ac.rhul.csle.tooling.CSCompiler.preprocessing.CSProcessorMap;
import uk.ac.rhul.csle.tooling.io.IOReadWrite;
import uk.ac.rhul.csle.tooling.lexer.DFAMap;
import uk.ac.rhul.csle.tooling.parsing.InvalidParseException;
import uk.ac.rhul.csle.tooling.trees.TreeBuilder;

/**
 * This class contains the main function which takes a C# 1.2 string and
 * transforms it into an AST.
 * 
 * @author Robert Michael Walsh
 *
 */
public class CSCompiler {

  /**
   * This function sets up the command line options that are applicable for this
   * program, using the Apache CLI 1.2 library.
   * <p>
   * Currently defined commands are
   * <ul>
   * <li><code>-D</code> <em>output_directory</em> - Tells the program to use
   * <em>output_directory</em> as the output directory.</li>
   * <li><code>-d</code> - If present, tells the program to display additional
   * debugging information
   * </ul>
   * 
   * @return An Apache CLI Options instantiation.
   */
  public static Options createCommandOptions() {
    final Options options = new Options();

    OptionBuilder.withArgName("output_directory");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Output to output_directory");
    options.addOption(OptionBuilder.create('D'));

    OptionBuilder.withArgName("debug_file");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Debug mode");
    options.addOption(OptionBuilder.create('d'));
    return options;
  }

  /**
   * The main function which takes a C# string and outputs an AST.
   * 
   * @param args
   *          The command line string (expects a filename as well as zero or
   *          more command line flags as defined by
   *          <code>createCommandOptions()</code>
   */
  public static void main(String[] args) {
    // Process command line options
    String output_directory = "output";
    String debug_file = "";
    final Options options = createCommandOptions();
    boolean debug = false;
    CommandLineParser parser;
    CommandLine line;
    String input = "";
    try {
      parser = new PosixParser();
      line = parser.parse(options, args);
      input = IOReadWrite.readFile(line.getArgs()[0]);
    } catch (IOException e) {
      System.err.println("File not found or cannot be opened.");
      final HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(
              "java -Dfile.encoding=UTF-8 -classpath bin:commons-cli-1.2.jar:gll.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler FILE",
              options);
      return;

    } catch (final ParseException e) {
      final HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp(
              "java -Dfile.encoding=UTF-8 -classpath bin:commons-cli-1.2.jar:gll.jar uk.ac.rhul.csle.tooling.CSCompiler.CSCompiler FILE",
              options);
      return;
    }

    if (line.hasOption("D")) {
      output_directory = line.getOptionValue("D");
    }
    if (line.hasOption("d")) {
      debug_file = line.getOptionValue("d");
      debug = true;
    }
    if (output_directory.equals(debug_file)) {
      System.err.println("Debug file cannot be the same as the output directory.");
      return;
    }
    final String unqualifiedFilename = line.getArgs()[0].substring(
            line.getArgs()[0].lastIndexOf('/') == -1 ? 0 : line.getArgs()[0].lastIndexOf('/') + 1,
            line.getArgs()[0].lastIndexOf(".") == -1 ? line.getArgs().length : line.getArgs()[0].lastIndexOf("."));
    // Create the output directory if it does not exist
    try {
      Files.createDirectories(new File(output_directory).toPath());
    } catch (IOException e1) {
      System.err.println("Unable to create output directory.");
      return;
    }

    try {
      // Run the main body
      final TreeBuilder cstb = new CSTreeBuilder(new CSParser(), debug);
      cstb.generateAST(unqualifiedFilename, initialProcessor(input));
      final int directoryEndIndex = line.getArgs()[0].lastIndexOf("/");

      // Write out files
      if (debug) {
        final PrintWriter debugger = new PrintWriter(new BufferedWriter(new FileWriter(debug_file, true)));
        debugger.println(line.getArgs()[0].substring(directoryEndIndex != -1 ? directoryEndIndex + 1 : 0,
                line.getArgs()[0].length()) + "," + cstb.getRemainingDerivations() + "," + cstb.getDPPFCount() + ","
                + cstb.getASTCount());
        debugger.close();
      }

      IOReadWrite.writeFile(output_directory + "/" + line.getArgs()[0]
              .substring(directoryEndIndex != -1 ? directoryEndIndex : 0, line.getArgs()[0].lastIndexOf(".")) + ".ast",
              cstb.astToTreeString());

    } catch (final InvalidParseException e) {
      System.err.println("Compilation terminated due to unresolved errors in parsing process (see above).");
      return;
    } catch (final IOException e) {
      System.err.println("Unable to write file");
      return;
    }

  }

  /**
   * This function takes any C# string and processes it to replace all
   * whitespace and comments with a single new-line character.
   * <p>
   * The output of this function is a reformatted C# string consisting of only
   * sequences of characters that are non-layout, delimited by new-line
   * characters.
   * 
   * @param input
   *          The C# string to process
   * @return The initially processed string
   */
  public static String initialProcessor(String input) {
    final DFAMap processor = new CSProcessorMap();
    int i = 0;
    final StringBuilder newString = new StringBuilder("");
    // We only care about identifying the layout tokens so we can use a
    // traditional longest-match lexer.
    while (i < input.length()) {
      boolean whitespaceCaptured = false;
      final int startIndex = i;
      int longestEndIndex = i + 1;
      for (final String token : processor.getTokens()) {
        int endIndex = i;
        while (i < input.length() && processor.applyTransition(token, input.charAt(i))) {
          i++;
          if (processor.atAcceptingState(token)) {
            endIndex = i;
            // We might be looking at a character or string literal instead in
            // which case we simply want to copy it over
            if (processor.isLayout(token)) {
              whitespaceCaptured = true;
            }
          }
        }
        processor.reset(token);
        if (endIndex > longestEndIndex) {
          longestEndIndex = endIndex;
        }
        i = startIndex;
      }
      // If this portion of the input string was not captured by a layout token
      // then we simply copy it over. Note that if the string is also not
      // matched by string and character literal, then we simply copy a single
      // character over.
      if (!whitespaceCaptured) {
        newString.append(input.substring(startIndex, longestEndIndex));
      } else {
        newString.append('\n');
      }
      i = longestEndIndex;
    }
    return newString.toString().replaceAll("\n+", "\n").trim();
  }
}
