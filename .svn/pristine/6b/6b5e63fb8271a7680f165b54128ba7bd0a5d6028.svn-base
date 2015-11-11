package uk.ac.rhul.csle.tooling.parsing.reduction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.rhul.csle.gll.GLLSupport;

/**
 * 
 * This is an abstract class representing the concept of a class/strategy of
 * derivation reduction operations
 * 
 * @author Robert Michael Walsh
 *
 */
public abstract class RedStrategy {

  /**
   * A Map that maps one grammar slot with a set of grammar slots. Slot A has a
   * relation to Slot B if A maps to a set containing Slot B.
   */
  protected final Map<String, Set<String>> matrix;

  /**
   * A boolean value determining whether debugging information should be
   * displayed
   */
  protected boolean debugging;

  /**
   * The parser context for this schema
   */
  protected final GLLSupport parsingContext;

  /**
   * Constructs a new <code>RedStrategy</code> with the given ART-generated
   * parser.
   * 
   * @param parsingContext
   *          The ART-generated parser to use for context
   */
  public RedStrategy(GLLSupport parsingContext) {
    this.parsingContext = parsingContext;
    matrix = new HashMap<>();
    debugging = false;
  }

  /**
   * Constructs a new <code>RedStrategy</code> with the given ART-generated
   * parser.
   * 
   * @param parsingContext
   *          The ART-generated parser to use for context
   * @param debugging
   *          If true, then methods in this object will display additional
   *          output to the console
   */
  public RedStrategy(GLLSupport parsingContext, boolean debugging) {
    this.parsingContext = parsingContext;
    matrix = new HashMap<>();
    this.debugging = debugging;
  }

  /**
   * A function that adds a new relation with <code>slotA</code> as the
   * left-hand side and <code>slotB</code> as the right-hand side
   * 
   * @param slotA
   *          The left-hand side of the relation
   * @param slotB
   *          The right-hand side of the relation
   */
  public void addRule(String slotA, String slotB) {
    Set<String> slotASet;
    if (matrix.containsKey(slotA)) {
      slotASet = matrix.get(slotA);
    } else {
      slotASet = new HashSet<>();
      matrix.put(slotA, slotASet);
    }
    slotASet.add(slotB);

  }

  /**
   * Removes all relations from this strategy.
   */
  public void clearStrategy() {
    matrix.clear();
  }

  /**
   * Marks <code>s1</code> for suppression if the element's grammar slot label
   * has a relation with the grammar slot label for <code>s2</code> and strategy
   * specific rules apply.
   * 
   * @param s1
   *          The left-hand side element in the test
   * @param s2
   *          the right-hand side grammar slot in the test
   */
  public abstract void apply(int s1, int s2);
}
