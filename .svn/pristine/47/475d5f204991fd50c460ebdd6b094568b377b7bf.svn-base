package uk.ac.rhul.csle.tooling.parsing.reduction;

import uk.ac.rhul.csle.gll.GLLSupport;
import uk.ac.rhul.csle.tooling.parsing.ParsingSupportFunctions;

/**
 * 
 * This is a derivation reduction strategy which suppresses packed nodes whose
 * grammar slot label has a relation with the grammar slot label of a sibling
 * with the same pivot value.
 * 
 * @author Robert Michael Walsh
 *
 */
public class SuppressRedStrategy extends RedStrategy {

  /**
   * Constructs a new <code>SuppressRedStrategy</code> with the given
   * ART-generated parser context.
   * 
   * @param parsingContext
   *          The ART-generated parser to use for context
   * 
   * @see uk.ac.rhul.csle.tooling.parsing.reduction.RedStrategy#RedStrategy(GLLSupport)
   */
  public SuppressRedStrategy(GLLSupport parsingContext) {
    super(parsingContext);
  }

  /**
   * Marks <code>s1</code> for suppression if the element's grammar slot label
   * has a relation with the grammar slot label for <code>s2</code> and the
   * pivot values of <s1> and <s2> are equal.
   * 
   * @param s1
   *          The left-hand side element in the test
   * @param s2
   *          the right-hand side grammar slot in the test
   */
  @Override
  public void apply(int s1, int s2) {
    String s1string = ParsingSupportFunctions.getPackedNodeInternalString(parsingContext, s1);
    String s2string = ParsingSupportFunctions.getPackedNodeInternalString(parsingContext, s2);
    if (matrix.containsKey(s1string)) {
      if (matrix.get(s1string).contains(s2string)
              && parsingContext.sppfPackNodePivot(s1) == parsingContext.sppfPackNodePivot(s2)) {
        parsingContext.sppfPackNodeSetSuppressed(s1);
        if (debugging) {
          System.out.println(s1string + "," + parsingContext.sppfPackNodePivot(s1) + " suppressed.");
        }
      }
    }
  }
}
