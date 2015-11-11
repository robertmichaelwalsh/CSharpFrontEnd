package uk.ac.rhul.csle.tooling.parsing;

import uk.ac.rhul.csle.gll.GLLSupport;

/**
 * Defines shortcuts for common operations on an ART generated parser
 * 
 * @author Robert Michael Walsh
 *
 */
public class ParsingSupportFunctions {

  /**
   * Returns the string representation of the given ESPPF packed node element
   * 
   * @param parsingContext
   *          The ART-generated parser containing the ESPPF
   * @param element
   *          The ESPPF packed node element
   * @return The string representation of the ESPPF packed node element
   */
  public static String getPackedNodeInternalString(GLLSupport parsingContext, int element) {
    return parsingContext.getLabelInternalString(parsingContext.sppfPackNodeLabel(element));
  }

  /**
   * Returns the string representation of the given ESPPF symbol/intermediate
   * node element
   * 
   * @param parsingContext
   *          The ART-generated parser containing the ESPPF
   * @param element
   *          The ESPPF symbol/intermediate node element
   * @return The string representation of the ESPPF symbol/intermediate node
   *         element
   */
  public static String getNodeInternalString(GLLSupport parsingContext, int element) {
    return parsingContext.getLabelInternalString(parsingContext.sppfNodeLabel(element));
  }
}
