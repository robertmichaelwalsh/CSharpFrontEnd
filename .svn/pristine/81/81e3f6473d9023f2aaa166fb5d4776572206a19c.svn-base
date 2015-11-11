package uk.ac.rhul.csle.tooling.CSCompiler;

import uk.ac.rhul.csle.gll.GLLSupport;
import uk.ac.rhul.csle.tooling.lexer.RegularLexer;
import uk.ac.rhul.csle.tooling.trees.TreeBuilder;

/**
 * Constructs a <code>TreeBuilder</code> specific for the C# 1.2 case study.
 * 
 * @author Robert Michael Walsh
 *
 */
public class CSTreeBuilder extends TreeBuilder {

  /**
   * Constructs a <code>CSTreeBuilder</code> with the given ART-generated
   * parser, using a <code>new RegularLexer(new CSDFAMap())</code> as the lexer,
   * <code>new CSDerivationReductor(parser)</code> as the derivation reduction
   * schema and <code>new CSTransformations()</code> as the GIFT transformation
   * scheme.
   * 
   * @param parser
   *          The ART-generated C# parser
   */
  public CSTreeBuilder(GLLSupport parser) {
    super(parser, new RegularLexer(new CSDFAMap()), new CSDerivationReductor(parser), new CSTransformations());
  }

  /**
   * Constructs a <code>CSTreeBuilder</code> with the given ART-generated
   * parser, using a <code>new RegularLexer(new CSDFAMap())</code> as the lexer,
   * <code>new CSDerivationReductor(parser)</code> as the derivation reduction
   * schema and <code>new CSTransformations()</code> as the GIFT transformation
   * scheme.
   * 
   * @param parser
   *          The ART-generated C# parser
   * @param debugging
   *          If true, then methods in this object will display additional
   *          output to the console
   */
  public CSTreeBuilder(GLLSupport parser, boolean debugging) {
    super(parser, new RegularLexer(new CSDFAMap()), new CSDerivationReductor(parser, debugging),
            new CSTransformations(), debugging);
  }
}
