package uk.ac.rhul.csle.tooling.parsing.reduction;

import java.util.Stack;

import uk.ac.rhul.csle.gll.GLLHashPool;
import uk.ac.rhul.csle.gll.GLLSupport;
import uk.ac.rhul.csle.tooling.parsing.InvalidParseException;
import uk.ac.rhul.csle.tooling.parsing.ParsingSupportFunctions;
import uk.ac.rhul.csle.tooling.trees.BaseDerivationNode;
import uk.ac.rhul.csle.tooling.trees.GIFTNode;
import uk.ac.rhul.csle.tooling.trees.GIFTVisitor;

/**
 * Provides a framework for taking an ESPPF, applying derivation reduction
 * strategies and obtaining a data structure representing the remaining
 * derivations.
 *
 * @author Robert Michael Walsh
 *
 */
public class EmbeddedTreeReductor {

  /**
   * A <code>RedStrategy</code> storing the <code>suppress</code> derivation
   * reduction rules
   */
  private final RedStrategy suppStrategy;

  /**
   * A <code>RedStrategy</code> storing the <code>longest</code> derivation
   * reduction rules
   */
  private final RedStrategy longStrategy;

  /**
   * A <code>RedStrategy</code> storing the <code>shortest</code> derivation
   * reduction rules
   */
  private final RedStrategy shortStrategy;

  /**
   * The ART-generated parser context
   */
  private final GLLSupport parsingContext;

  /**
   * The input string that was parsed
   */
  private String inputString;

  /**
   * A boolean value determining whether debugging information should be
   * displayed
   */
  private boolean debugging;

  /**
   * Constructs a new <code>EmbeddedTreeReductor</code> with the given
   * ART-generated parser context
   *
   * @param parsingContext
   *          The ART-generated parser context
   */
  public EmbeddedTreeReductor(GLLSupport parsingContext) {
    this.parsingContext = parsingContext;
    suppStrategy = new SuppressRedStrategy(parsingContext);
    longStrategy = new LongestRedStrategy(parsingContext);
    shortStrategy = new ShortestRedStrategy(parsingContext);
    debugging = false;
  }

  /**
   * Constructs a new <code>EmbeddedTreeReductor</code> with the given
   * ART-generated parser context
   *
   * @param parsingContext
   *          The ART-generated parser context
   * @param debugging
   *          If true, then methods in this object will display additional
   *          output to the console
   */
  public EmbeddedTreeReductor(GLLSupport parsingContext, boolean debugging) {
    this.parsingContext = parsingContext;
    suppStrategy = new SuppressRedStrategy(parsingContext);
    longStrategy = new LongestRedStrategy(parsingContext);
    shortStrategy = new ShortestRedStrategy(parsingContext);
    this.debugging = debugging;
  }

  /**
   * Constructs a new <code>EmbeddedTreeReductor</code> with the given
   * ART-generated parser context and parsed input string
   *
   * @param parsingContext
   *          The ART-generated parser context
   * @param inputString
   *          The input string that was parsed
   */
  public EmbeddedTreeReductor(GLLSupport parsingContext, String inputString) {
    this.parsingContext = parsingContext;
    suppStrategy = new SuppressRedStrategy(parsingContext);
    longStrategy = new LongestRedStrategy(parsingContext);
    shortStrategy = new ShortestRedStrategy(parsingContext);
    this.inputString = inputString;
  }

  /**
   * Traverses the ESPPF for the parsing context and attempts to resolve
   * ambiguities using the defined derivation reduction schema.
   *
   * @throws InvalidParseException
   *           If the parsing context does not contain a valid parse (which
   *           occurs either if no parse has occurred or the string was
   *           rejected).
   *
   */
  public void filterSPPF() throws InvalidParseException {
    if (!parsingContext.getInLanguage()) {
      System.err.println("Attempting to filter derivations without a valid parse.");
      throw new InvalidParseException();
    }
    int element = parsingContext.sppfRoot();
    final Stack<Integer> processorStack = new Stack<>();
    // Start with the root node
    processorStack.push(element);

    while (!processorStack.isEmpty()) {
      final int currentElement = processorStack.pop();
      if (!parsingContext.sppfNodeVisited(currentElement)) {
        // Add the children of each packed node to the stack
        parsingContext.sppfNodeSetVisited(currentElement);
        for (int tmp = parsingContext.sppfNodePackNodeList(currentElement); tmp != 0; tmp =
                parsingContext.sppfPackNodePackNodeList(tmp)) {
          final int rightChild = parsingContext.sppfPackNodeRightChild(tmp);
          if (rightChild != 0) {
            processorStack.push(rightChild);
          }
          final int leftChild = parsingContext.sppfPackNodeLeftChild(tmp);
          if (leftChild != 0) {
            processorStack.push(leftChild);
          }
        }
        // Detect whether there are multiple packed node children
        if (parsingContext.sppfNodeArity(currentElement) > 1) {
          if (debugging) {
            System.out.println(ParsingSupportFunctions.getNodeInternalString(parsingContext, currentElement) + " has "
                    + parsingContext.sppfNodeArity(currentElement) + " pack node elements:");
          }

          // Isolate the choices (useful particularly for debugging purposes)
          int counter = 0;
          final int[] choices = new int[parsingContext.sppfNodeArity(currentElement)];
          for (int tmp = parsingContext.sppfNodePackNodeList(currentElement); tmp != 0; tmp =
                  parsingContext.sppfPackNodePackNodeList(tmp)) {
            printPackNode(counter, tmp);
            choices[counter] = tmp;
            counter++;
          }

          for (final int s1 : choices) {
            for (final int s2 : choices) {
              if (s1 != s2) {
                suppStrategy.apply(s1, s2);
                longStrategy.apply(s1, s2);
                shortStrategy.apply(s1, s2);
              }
            }
          }

          // Determine whether more than one derivation remains
          findResidualDerivations(choices);

          if (debugging) {
            // Print the yield of the tree to demonstrate where the ambiguity
            // was
            printYield(currentElement);
          }
        }
      }
    }
    parsingContext.sppfResetVisitedFlags();
    if (debugging) {
      System.out.println("SPPF filtered.");
    }

  }

  /**
   * Detects whether more than one ESPPF packed node in the set of elements
   * given is unsuppressed (indicating that not all ambiguities are unresolved).
   * 
   * @param choices
   *          An array of packed node children in order of left-most to
   *          right-most
   */
  private void findResidualDerivations(final int[] choices) {
    int countValidBranches = 0;
    for (final int packedNode : choices) {
      if (!parsingContext.sppfPackNodeSuppressed(packedNode)) {
        countValidBranches++;
      }
    }

    if (countValidBranches > 1) {
      System.err
              .println(
                      "WARNING: Remaining ambiguities exist for " + "("
                              + ParsingSupportFunctions.getNodeInternalString(parsingContext,
                                      parsingContext.sppfPackNodeParent(choices[0]))
                              + ")" + ", ambiguity nodes will be used.");
    } else if (countValidBranches == 0) {
      System.err.println("WARNING: All packed nodes under" + "("
              + ParsingSupportFunctions.getNodeInternalString(parsingContext,
                      parsingContext.sppfPackNodeParent(choices[0]))
              + ")" + " were suppressed by the disambiguation scheme," + "("
              + ParsingSupportFunctions.getPackedNodeInternalString(parsingContext, choices[0]) + ")"
              + " will be unsuppressed.");
      parsingContext.sppfPackNodeSetSuppressed(choices[0]);
    }
  }

  /**
   * Generates a data structure representing the remaining derivations in the
   * ESPPF.
   * <p>
   * This data structure is a non-binarised ESPPF with the following exceptions
   * <ul>
   * <li>Epsilon node are removed</li>
   * <li>If a packed node has no siblings, then it is not present in the DPPF,
   * otherwise, it is replaced with nodes labelled "ambig"</li>
   * <li>All other nodes are labelled solely with the non-terminal or terminal
   * they represent.</li>
   * <li>Where there is sharing in an ESPPF, there is no sharing in a derivation
   * PPF</li>
   * </ul>
   *
   * @return The root node of this derivation structure
   */
  public GIFTNode generateDerivationPPF() {
    if (debugging) {
      System.out.println("Generating derivation PPF...");
    }
    ((GLLHashPool) parsingContext).derivationSelectAll();
    // Initially just constructs a data structure that contains epsilon and
    // intermediate nodes
    final GIFTVisitor visitor = new GIFTVisitor(parsingContext);
    ((GLLHashPool) parsingContext).derivationVisit(visitor);
    final GIFTNode derivationTreeRoot = visitor.getRoot();
    // Remove the epsilon and intermediate nodes
    removeNonDPPFNodes(derivationTreeRoot);
    return derivationTreeRoot;
  }

  /**
   * Adds a derivation reduction rule saying a packed node labelled with
   * <code>slotA</code> should be suppressed if it has a smaller pivot than a
   * sibling packed node labelled with <code>slotB</code>.
   * 
   * @param slotA
   *          The first grammar slot to relate
   * @param slotB
   *          The second grammar slot to relate
   */
  public void longest(String slotA, String slotB) {
    longest(slotA, slotB, false);
  }

  /**
   * 
   * Adds a derivation reduction rule saying a packed node labelled with
   * <code>slotA</code> should be suppressed if it has a smaller pivot than a
   * sibling packed node labelled with <code>slotB</code>.
   * <p>
   * If <code>bidirectional</code> is true, then also adds a derivation
   * reduction rule saying a packed node labelled with <code>slotB</code> should
   * be suppressed if it has a smaller pivot than a sibling packed node labelled
   * with <code>slotA</code>.
   * 
   * @param slotA
   *          The first grammar slot to relate
   * @param slotB
   *          The second grammar slot
   * @param bidirectional
   *          True if the relation should be bidirectional, false otherwise.
   */
  public void longest(String slotA, String slotB, boolean bidirectional) {
    longStrategy.addRule(slotA, slotB);
    if (bidirectional) {
      longStrategy.addRule(slotB, slotA);
    }
  }

  /**
   * A helper function that prints a packed node as a string of form:
   * <p>
   * [<code>counter</code>] <code>packNodeElement</code>: label
   * <p>
   * This is used to aid in listing the choices that can be made for a given
   * ambiguity.
   * 
   * @param counter
   *          The position of the packed node in the list of children of its
   *          parent
   * @param packNodeElement
   *          The packed node element
   */
  private void printPackNode(int counter, int packNodeElement) {
    System.out.println("[" + counter + "] " + packNodeElement + ":"
            + ParsingSupportFunctions.getPackedNodeInternalString(parsingContext, packNodeElement) + ","
            + parsingContext.sppfPackNodePivot(packNodeElement));
  }

  /**
   * A helper function that prints the yield of the ESPPF rooted at the given
   * element
   * 
   * @param element
   *          The root element of the sub-graph for which the yield should be
   *          printed.
   */
  private void printYield(int element) {
    final int leftCharacterExtent = parsingContext.sppfNodeLeftExtent(element);
    final int rightCharacterExtent = parsingContext.sppfNodeRightExtent(element);
    System.out.println("For the input: \n" + inputString.substring(leftCharacterExtent, rightCharacterExtent));

  }

  /**
   * This function removes epsilon and intermediate nodes from a structural
   * representation of a set of derivations.
   * 
   * @param root
   *          The root of this structural representation.
   */
  private void removeNonDPPFNodes(BaseDerivationNode root) {
    final Stack<BaseDerivationNode> stack = new Stack<>();
    stack.add(root);

    while (!stack.isEmpty()) {
      final BaseDerivationNode current = stack.pop();
      for (BaseDerivationNode tmp = current.getChild(); tmp != null; tmp = tmp.getSibling()) {
        if (tmp.getLabelKind() == GLLSupport.ART_K_EPSILON || tmp.getLabelKind() == GLLSupport.ART_K_INTERMEDIATE) {
          current.deleteChild(tmp);
        }
        if (tmp.getChild() != null) {
          stack.push(tmp);
        }
      }
    }

  }

  /**
   * Sets the character string used as the input for the parser
   *
   * @param inputString
   *          The character string used as the input for the parser
   */
  public void setInputString(String inputString) {
    this.inputString = inputString;
  }

  /**
   * Adds a derivation reduction rule saying a packed node labelled with
   * <code>slotA</code> should be suppressed if it has a larger pivot than a
   * sibling packed node labelled with <code>slotB</code>.
   * 
   * @param slotA
   *          The first grammar slot to relate
   * @param slotB
   *          The second grammar slot to relate
   */
  public void shortest(String slotA, String slotB) {
    shortest(slotA, slotB, false);
  }

  /**
   * 
   * Adds a derivation reduction rule saying a packed node labelled with
   * <code>slotA</code> should be suppressed if it has a larger pivot than a
   * sibling packed node labelled with <code>slotB</code>.
   * <p>
   * If <code>bidirectional</code> is true, then also adds a derivation
   * reduction rule saying a packed node labelled with <code>slotB</code> should
   * be suppressed if it has a larger pivot than a sibling packed node labelled
   * with <code>slotA</code>.
   * 
   * @param slotA
   *          The first grammar slot to relate
   * @param slotB
   *          The second grammar slot
   * @param bidirectional
   *          True if the relation should be bidirectional, false otherwise.
   */
  public void shortest(String slotA, String slotB, boolean bidirectional) {
    shortStrategy.addRule(slotA, slotB);
    if (bidirectional) {
      shortStrategy.addRule(slotB, slotA);
    }
  }

  /**
   * Adds a derivation reduction rule saying a packed node labelled with
   * <code>slotA</code> should be suppressed if there exists a sibling packed
   * node labelled with <code>slotB</code>.
   * 
   * @param slotA
   *          The first grammar slot to relate
   * @param slotB
   *          The second grammar slot to relate
   */
  public void suppress(String slotA, String slotB) {
    suppStrategy.addRule(slotA, slotB);
  }

}