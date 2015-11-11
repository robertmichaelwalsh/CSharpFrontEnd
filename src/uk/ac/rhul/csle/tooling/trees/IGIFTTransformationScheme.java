package uk.ac.rhul.csle.tooling.trees;

/**
 * An interface for specifying a description on how to apply GIFT
 * transformations
 * 
 * @author Robert Michael Walsh
 *
 */
public interface IGIFTTransformationScheme {

  /**
   * Traverses the tree constructed for the given input string and applies GIFT
   * transformation where applicable.
   * 
   * @param tree
   *          The tree to transform
   * @param inputString
   *          The input string that was used to construct this tree
   * @return The GIFT transformed tree.
   */
  public GIFTNode toAST(GIFTNode tree, String inputString);
}
