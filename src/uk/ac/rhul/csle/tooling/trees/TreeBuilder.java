package uk.ac.rhul.csle.tooling.trees;

import java.math.BigInteger;

import uk.ac.rhul.csle.gll.GLLSupport;
import uk.ac.rhul.csle.tooling.CSCompiler.CSLexer;
import uk.ac.rhul.csle.tooling.lexer.MultiLexer;
import uk.ac.rhul.csle.tooling.parsing.DerivationCounter;
import uk.ac.rhul.csle.tooling.parsing.InvalidParseException;
import uk.ac.rhul.csle.tooling.parsing.reduction.EmbeddedTreeReductor;

/**
 * A class that brings together a lexer, parser, derivation tree reduction
 * scheme and a set of GIFT transformations in order to take a character string
 * and translate it into an AST.
 * 
 * @author Robert Michael Walsh
 *
 */
public class TreeBuilder {

  /**
   * The input string for this <code>TreeBuilder</code>
   */
  private String currentInput;

  /**
   * The root of the derivation tree that was constructed by the parser
   */
  private GIFTNode derivationTreeRoot;

  /**
   * The root of the AST that was constructed through the GIFT transformations
   */
  private GIFTNode astTreeRoot;

  /**
   * The parser for this <code>TreeBuilder</code>
   */
  protected final GLLSupport parser;

  /**
   * The lexer for this <code>TreeBuilder</code>
   */
  protected final MultiLexer lex;

  /**
   * The derivation tree reduction scheme for this <code>TreeBuilder</code>
   */
  protected final EmbeddedTreeReductor reductor;

  /**
   * The set of GIFT transformations for this <code>TreeBuilder</code>
   */
  protected final IGIFTTransformationScheme transforms;

  /**
   * A boolean value determining whether debugging information should be
   * displayed
   */
  protected boolean debugging;

  /**
   * Constructs a new <code>TreeBuilder</code> with the given parser, lexer,
   * derivation tree reduction scheme, and set of GIFT transformations
   * 
   * @param parser
   *          The parser to use
   * @param lex
   *          The lexer to use
   * @param reductor
   *          The derivation tree reduction scheme
   * @param transforms
   *          The set of GIFT transformations
   */
  public TreeBuilder(GLLSupport parser, MultiLexer lex, EmbeddedTreeReductor reductor,
          IGIFTTransformationScheme transforms) {
    this.parser = parser;
    this.lex = lex;
    this.reductor = reductor;
    this.transforms = transforms;
    debugging = false;
  }

  /**
   * Constructs a new <code>TreeBuilder</code> with the given parser, lexer,
   * derivation tree reduction scheme, and set of GIFT transformations
   * 
   * @param parser
   *          The parser to use
   * @param lex
   *          The lexer to use
   * @param reductor
   *          The derivation tree reduction scheme
   * @param transforms
   *          The set of GIFT transformations
   * @param debugging
   *          If true, then methods in this object will display additional
   *          output to the console
   */
  public TreeBuilder(GLLSupport parser, MultiLexer lex, EmbeddedTreeReductor reductor,
          IGIFTTransformationScheme transforms, boolean debugging) {
    this.parser = parser;
    this.lex = lex;
    this.reductor = reductor;
    this.transforms = transforms;
    this.debugging = debugging;
  }

  /**
   * If the root of the AST is T and the list of its children is T1,...,TN then
   * this method returns the string
   *
   * T(T1(..),...,TN(..))
   *
   * @return The AST in tree string format
   */
  public String astToTreeString() {
    if (astTreeRoot == null) {
      return "";
    }
    return astTreeRoot.toTreeString();
  }

  /**
   * If the root of the derivation tree is T and the list of its children is
   * T1,...,TN then this method returns the string
   *
   * T(T1(..),...,TN(..))
   *
   * @return The derivation tree in tree string format
   */
  public String derivationTreeToTreeString() {
    if (derivationTreeRoot == null) {
      return "";
    }
    return derivationTreeRoot.toTreeString();
  }

  /**
   * Disambiguates the ESPPF.
   * <p>
   * (NOTE: This requires {@link TreeBuilder#parse(string)} or
   * {@link TreeBuilder#parse(String, String)} to have been called first)
   *
   * @return The resulting derivation tree
   * @throws InvalidParseException
   */
  public GIFTNode filter(EmbeddedTreeReductor disScheme) throws InvalidParseException {
    if (!parser.getInLanguage()) {
      System.err.println("ERROR: Attempting to reduce the number of derivation trees without a valid parse");
      return null;
    }

    disScheme.filterSPPF();
    derivationTreeRoot = disScheme.generateDerivationPPF();

    astTreeRoot = derivationTreeRoot.clone();

    if (debugging) {
      System.out.println("Derivation tree constructed.");
    }
    return derivationTreeRoot;
  }

  /**
   * Takes the given input string, tokenises it, passes the tokenisations to the
   * parser, and then performs derivation tree reduction and GIFT
   * transformations on the result.
   * 
   * @param filename
   *          The filename of the string being parsed (for error reporting
   *          purposes. Use the empty string if input does not come from a file)
   * @param input
   *          The input string to parse
   * @throws InvalidParseException
   *           If the program is either unable to tokenise or parse the string.
   */
  public void generateAST(String filename, String input) throws InvalidParseException {
    CSLexer.longestMatchesCS(lex);
    CSLexer.equalPriorityMatchesCS(lex);

    lex.lexSegmented(filename, input);

    parse(lex.toTok(lex.getDisambiguated()), input);
    currentInput = input;
    reductor.setInputString(input);
    filter(reductor);
    toAST(transforms);
  }

  /**
   * Returns the number of nodes in the AST (0 if no AST constructed)
   * 
   * @return The number of nodes in the AST
   */
  public int getASTCount() {
    if (astTreeRoot == null) {
      return 0;
    }
    return astTreeRoot.countNodes();
  }

  /**
   * Returns the root node of the AST
   * 
   * @return The root node of the AST
   */
  public GIFTNode getASTTreeRoot() {
    return astTreeRoot;
  }

  /**
   * Returns the root node of the derivation structure
   * 
   * @return The root node of the derivation structure
   */
  public GIFTNode getDerivationTreeRoot() {
    return derivationTreeRoot;
  }

  /**
   * Returns the number of nodes in the derivation structure (0 if no derivation
   * structure constructed)
   * 
   * @return The number of nodes in the derivation structure
   */
  public int getDPPFCount() {
    if (derivationTreeRoot == null) {
      return 0;
    }
    return derivationTreeRoot.countNodes();
  }

  /**
   * Returns the parser for this <code>TreeBuilder</code>
   * 
   * @return The parser for this <code>TreeBuilder</code>
   */
  protected GLLSupport getParser() {
    return parser;
  }

  /**
   * Returns the number of derivations that are not suppressed embedded the
   * ESPPF
   * 
   * @return The number of non-suppressed derivation in the ESPPF
   * @throws InvalidParseException
   *           If the parser context does not embed an ESPPF (which occurs
   *           either if no parse has occurred or the string was rejected by the
   *           parser)
   */
  public BigInteger getRemainingDerivations() throws InvalidParseException {
    final DerivationCounter count = new DerivationCounter(parser);
    return count.countDerivations();

  }

  /**
   * Parses the given input string.
   *
   * @param input
   *          The input string to parse
   * @throws InvalidParseException
   *           If there is no valid parse
   */
  public void parse(String input) throws InvalidParseException {
    parser.parse(input);
    currentInput = input;

    if (!parser.getInLanguage()) {
      System.out.println(input);
      throw new InvalidParseException();
    }

    System.out.println("Parse successful.");
  }

  /**
   * Parses the given Multilexer TWE set representation (as defined by
   * {@link MultiLexer#toTok(java.util.Set)})
   * 
   * @param tok
   *          The TWE set representation of the tokenisations of the string
   * @param input
   *          The original input character string
   * @throws InvalidParseException
   *           If there is no valid parse
   */
  public void parse(String tok, String input) throws InvalidParseException {
    parse(tok);
    currentInput = input;
  }

  /**
   * Sets the derivation tree
   * 
   * @param derivationTreeRoot
   *          The root node of the new derivation tree
   */
  protected void setDerivationTreeRoot(GIFTNode derivationTreeRoot) {
    this.derivationTreeRoot = derivationTreeRoot;
  }

  /**
   * Converts the derivation tree into an AST through application of the GIFT
   * transformations
   * <p>
   * (NOTE: Should only be called after
   * {@link TreeBuilder#filter(EmbeddedTreeReductor)})
   * 
   * @return The resulting AST
   */
  public GIFTNode toAST(IGIFTTransformationScheme transforms) {
    if (astTreeRoot == null) {
      System.err.println("Attempting to create AST without disambiguation");
      return null;
    }

    astTreeRoot = transforms.toAST(astTreeRoot, currentInput);
    System.out.println("AST constructed.");
    return astTreeRoot;
  }
}