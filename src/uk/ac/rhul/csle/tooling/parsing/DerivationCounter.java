package uk.ac.rhul.csle.tooling.parsing;

import java.math.BigInteger;
import java.util.HashMap;

import uk.ac.rhul.csle.gll.GLLSupport;

/**
 * 
 * A <code>DerivationCounter</code> defines a mechanism for counting the number
 * of derivations that are embedded in a given ESPPF.
 * 
 * 
 * @author Robert Michael Walsh
 *
 */
public class DerivationCounter {

  /**
   * The ART-generated parser context.
   */
  private final GLLSupport parser;

  /**
   * Maps an ESPPF element to a <code>BigInteger</code> denoting the number of
   * derivations embedded in the subtree rooted at the ESPPF element.
   */
  HashMap<Integer, BigInteger> previousValues;

  /**
   * Constructs a new <code>DerivationCounter</code> for the given ART-generated
   * parser context.
   * 
   * @param parser
   *          The ART-generated parser context
   */
  public DerivationCounter(GLLSupport parser) {
    this.parser = parser;
    previousValues = new HashMap<Integer, BigInteger>();
  }

  /**
   * Counts and returns the number of derivations embedded in the ESPPF
   * constructed by the parser context.
   * <p>
   * As the number of derivations can be very large, a <code>BigInteger</code>
   * is needed to represent this value.
   * <p>
   * (Note: This function will return an incorrect value for ESPPFs containing
   * cycles (which would have infinite derivations))
   * 
   * @return A <code>BigInteger</code> representing the number of derivation
   *         embedded in the ESPPF
   * @throws InvalidParseException
   *           If the parser context does not embed an ESPPF (which occurs
   *           either if no parse has occurred or the string was rejected by the
   *           parser)
   */
  public BigInteger countDerivations() throws InvalidParseException {
    if (!parser.getInLanguage()) {
      System.err.println("Attempting to count without a valid parse.");
      throw new InvalidParseException();
    }
    parser.sppfResetVisitedFlags();
    BigInteger count = countDerivationsRec(parser.sppfRoot());
    parser.sppfResetVisitedFlags();
    return count;
  }

  /**
   * Traverse the ESPPF rooted at the given element and returns the number of
   * derivations embedded in this ESPPF.
   * 
   * @param element
   *          The root of the ESPPF
   * @return The number of derivations embedded in this ESPPF.
   */
  private BigInteger countDerivationsRec(int element) {
    if (parser.sppfNodeVisited(element)) {
      return previousValues.get(element);
    }
    parser.sppfNodeSetVisited(element);
    BigInteger count = new BigInteger("0");

    if (parser.sppfNodeArity(element) == 0) {
      previousValues.put(element, new BigInteger("1"));

      return new BigInteger("1");
    }
    for (int tmp = parser.sppfNodePackNodeList(element); tmp != 0; tmp = parser.sppfPackNodePackNodeList(tmp)) {
      if (!parser.sppfPackNodeSuppressed(tmp)) {
        BigInteger packNodeCount = new BigInteger("0");
        final int leftChild = parser.sppfPackNodeLeftChild(tmp);
        if (leftChild != 0) {
          BigInteger leftChildCount = countDerivationsRec(leftChild);
          packNodeCount = packNodeCount.add(leftChildCount);
        }
        BigInteger rightChildCount = countDerivationsRec(parser.sppfPackNodeRightChild(tmp));
        if (!packNodeCount.equals(new BigInteger("0"))) {
          packNodeCount = packNodeCount.multiply(rightChildCount);
        } else {
          packNodeCount = packNodeCount.add(rightChildCount);
        }
        count = count.add(packNodeCount);
        previousValues.put(element, count);
      }
    }
    return count;
  }
}
