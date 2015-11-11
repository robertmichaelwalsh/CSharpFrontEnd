package uk.ac.rhul.csle.tooling.CSCompiler;

import uk.ac.rhul.csle.gll.GLLSupport;
import uk.ac.rhul.csle.tooling.parsing.reduction.EmbeddedTreeReductor;

/**
 * This class defines the set of derivation reduction rules used by the C# 1.2
 * case study.
 *
 * @author Robert Michael Walsh
 *
 */
public class CSDerivationReductor extends EmbeddedTreeReductor {

  /**
   * Initialises a new <code>CSDerivationReductor</code> with the given
   * ART-generated parser.
   *
   * @param parsingContext
   *          The ART-generated C# parser
   */
  public CSDerivationReductor(GLLSupport parsingContext) {
    super(parsingContext);
    initialise();
  }

  /**
   * Initialises a new <code>CSDerivationReductor</code> with the given
   * ART-generated parser.
   *
   * @param parsingContext
   *          The ART-generated C# parser
   * @param debugging
   *          If true, then methods in this object will display additional
   *          output to the console
   */
  public CSDerivationReductor(GLLSupport parsingContext, boolean debugging) {
    super(parsingContext, debugging);
    initialise();
  }

  /**
   * A private function which initialises all derivation reduction rules used in
   * the C# 1.2 case study
   */
  private void initialise() {
    suppress("Csharp.type ::= Csharp.value_type . ", "Csharp.type ::= Csharp.reference_type . ");
    suppress("Csharp.reference_type ::= Csharp.delegate_type . ", "Csharp.reference_type ::= Csharp.class_type . ");
    suppress("Csharp.reference_type ::= Csharp.delegate_type . ", "Csharp.reference_type ::= Csharp.interface_type . ");
    suppress("Csharp.reference_type ::= Csharp.interface_type . ", "Csharp.reference_type ::= Csharp.class_type . ");
    suppress("Csharp.value_type ::= Csharp.struct_type . ", "Csharp.value_type ::= Csharp.enum_type . ");
    suppress("Csharp.class_base ::= ':' Csharp.class_type . ",
            "Csharp.class_base ::= ':' Csharp.interface_type_list . ");
    longest("Csharp.class_base ::= ':' Csharp.class_type ',' Csharp.interface_type_list . ",
            "Csharp.class_base ::= ':' Csharp.interface_type_list . ");
    shortest("Csharp.class_base ::= ':' Csharp.class_type ',' Csharp.interface_type_list . ",
            "Csharp.class_base ::= ':' Csharp.interface_type_list . ");
    suppress("Csharp.class_base ::= ':' Csharp.class_type ',' Csharp.interface_type_list . ",
            "Csharp.class_base ::= ':' Csharp.interface_type_list . ");
    suppress("Csharp.unary_expression ::= Csharp.primary_expression . ",
            "Csharp.unary_expression ::= Csharp.cast_expression . ");
    shortest("Csharp.additive_expression ::= Csharp.multiplicative_expression . ",
            "Csharp.additive_expression ::= Csharp.additive_expression '-' Csharp.multiplicative_expression . ");
    longest("Csharp.additive_expression ::= Csharp.multiplicative_expression . ",
            "Csharp.additive_expression ::= Csharp.additive_expression '-' Csharp.multiplicative_expression . ");
    suppress("Csharp.additive_expression ::= Csharp.multiplicative_expression . ",
            "Csharp.additive_expression ::= Csharp.additive_expression '-' Csharp.multiplicative_expression . ");
    shortest("Csharp.additive_expression ::= Csharp.multiplicative_expression . ",
            "Csharp.additive_expression ::= Csharp.additive_expression '+' Csharp.multiplicative_expression . ");
    longest("Csharp.additive_expression ::= Csharp.multiplicative_expression . ",
            "Csharp.additive_expression ::= Csharp.additive_expression '+' Csharp.multiplicative_expression . ");
    suppress("Csharp.additive_expression ::= Csharp.multiplicative_expression . ",
            "Csharp.additive_expression ::= Csharp.additive_expression '+' Csharp.multiplicative_expression . ");
    suppress("Csharp.primary_no_array_creation_expression ::= Csharp.delegate_creation_expression . ",
            "Csharp.primary_no_array_creation_expression ::= Csharp.object_creation_expression . ");
    // changed
    shortest(
            "Csharp.if_statement ::= 'if' '(' Csharp.boolean_expression ')' Csharp.embedded_statement 'else' Csharp.embedded_statement . ",
            "Csharp.if_statement ::= 'if' '(' Csharp.boolean_expression ')' Csharp.embedded_statement . ");
    longest("Csharp.if_statement ::= 'if' '(' Csharp.boolean_expression ')' Csharp.embedded_statement 'else' Csharp.embedded_statement . ",
            "Csharp.if_statement ::= 'if' '(' Csharp.boolean_expression ')' Csharp.embedded_statement . ");
    suppress(
            "Csharp.if_statement ::= 'if' '(' Csharp.boolean_expression ')' Csharp.embedded_statement 'else' Csharp.embedded_statement . ",
            "Csharp.if_statement ::= 'if' '(' Csharp.boolean_expression ')' Csharp.embedded_statement . ");
    suppress("Csharp.attribute_arguments ::= '(' Csharp.positional_argument_list ')' . ",
            "Csharp.attribute_arguments ::= '(' Csharp.positional_argument_list ',' Csharp.named_argument_list ')' . ");
    suppress("Csharp.attribute_arguments ::= '(' Csharp.positional_argument_list ')' . ",
            "Csharp.attribute_arguments ::= '(' Csharp.named_argument_list ')' . ");
    suppress("Csharp.attribute_arguments ::= '(' Csharp.positional_argument_list ',' Csharp.named_argument_list ')' . ",
            "Csharp.attribute_arguments ::= '(' Csharp.named_argument_list ')' . ");
    shortest("Csharp.array_type ::= Csharp.non_array_type Csharp.rank_specifiers . ",
            "Csharp.array_type ::= Csharp.non_array_type Csharp.rank_specifiers . ");
    shortest("Csharp.attribute_arguments ::= '(' Csharp.positional_argument_list ',' Csharp.named_argument_list . ')' ",
            "Csharp.attribute_arguments ::= '(' Csharp.positional_argument_list ',' Csharp.named_argument_list . ')' ");

  }
}
