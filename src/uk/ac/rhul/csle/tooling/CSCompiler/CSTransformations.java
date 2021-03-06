package uk.ac.rhul.csle.tooling.CSCompiler;

import uk.ac.rhul.csle.tooling.trees.GIFTNode;
import uk.ac.rhul.csle.tooling.trees.IGIFTTransformationScheme;

/**
 * Provides an implementation of the GIFT transformations required to convert a
 * derivation tree in the C# 1.2 grammar to an AST as specified in the PLanCompS
 * abstract syntax for C#.
 *
 * @author Robert Michael Walsh
 *
 */
public class CSTransformations implements IGIFTTransformationScheme {

  /**
   * Default constructor for <code>CSTransformations</code>. Does no additional
   * setup.
   */
  public CSTransformations() {
  }

  @Override
  public GIFTNode toAST(GIFTNode tree, String inputString) {
    for (GIFTNode tmp = (GIFTNode) tree.getChild(); tmp != null; tmp = (GIFTNode) tmp.getSibling()) {
      if (tmp.getChild() != null) {
        toAST(tmp, inputString);
      }
      toASTSing(tmp, inputString);
    }
    return tree;
  }

  /**
   * A private function which determines whether a GIFT transformation needs to
   * be applied for the given node, and applies the transformation if so.
   *
   * @param node
   *          The node to apply a GIFT transformation to (if applicable).
   * @param inputString
   *          The original parsed C# string.
   */
  private void toASTSing(GIFTNode node, String inputString) {
    switch (node.getTextLabel()) {
      // AST representation needs the underlying character string for the leaf
      // nodes
      case "identifier": // fall-through
      case "integer_literal": // fall-through
      case "real_literal": // fall-through
      case "character_literal": // fall-through
      case "string_literal": // fall-through
      case "null_literal":
      case "boolean_literal":
        node.setTextLabel(node.getUnderlyingString(inputString));
        break;
      // The non-terminals that are simply removed
      case "namespace_declaration": // fall-through
      case "specific_catch_clauses": // fall-through
      case "value_type": // fall-through
      case "reference_type": // fall-through
      case "struct_type": // fall-through
      case "enum_type": // fall-through
      case "simple_type": // fall-through
      case "numeric_type": // fall-through
      case "floating_point_type": // fall-through
      case "variable_reference": // fall-through
      case "array_creation_expression": // fall-through
      case "object_creation_expression": // fall-through
      case "delegate_creation_expression": // fall-through
      case "typeof_expression": // fall-through
      case "checked_expression": // fall-through
      case "unchecked_expression": // fall-through
      case "non_array_type": // fall-through
      case "cast_expression": // fall-through
      case "constant_expression": // fall-through
      case "boolean_expression": // fall-through
      case "labeled_statement": // fall-through
      case "empty_statement": // fall-through
      case "expression_statement": // fall-through
      case "selection_statement": // fall-through
      case "iteration_statement": // fall-through
      case "jump_statement": // fall-through
      case "try_statement": // fall-through
      case "checked_statement": // fall-through
      case "unchecked_statement": // fall-through
      case "lock_statement": // fall-through
      case "using_statement": // fall-through
      case "statement_list": // fall-through
      case "if_statement": // fall-through
      case "switch_statement": // fall-through
      case "switch_block": // fall-through
      case "switch_sections": // fall-through
      case "switch_labels": // fall-through
      case "while_statement": // fall-through
      case "do_statement": // fall-through
      case "for_statement": // fall-through
      case "foreach_statement": // fall-through
      case "for_condition": // fall-through
      case "for_iterator": // fall-through
      case "break_statement": // fall-through
      case "continue_statement": // fall-through
      case "goto_statement": // fall-through
      case "return_statement": // fall-through
      case "throw_statement": // fall-through
      case "finally_clause": // fall-through
      case "using_directives": // fall-through
      case "global_attributes": // fall-through
      case "namespace_member_declarations": // fall-through
      case "namespace_body": // fall-through
      case "using_alias_directive": // fall-through
      case "using_namespace_directive": // fall-through
      case "attributes": // fall-through
      case "class_modifiers": // fall-through
      case "class_body": // fall-through
      case "class_member_declarations": // fall-through
      case "constant_modifiers": // fall-through
      case "field_modifiers": // fall-through
      case "method_header": // fall-through
      case "method_modifiers": // fall-through
      case "fixed_parameters": // fall-through
      case "parameter_modifier": // fall-through
      case "property_modifiers": // fall-through
      case "get_accessor_declaration": // fall-through
      case "set_accessor_declaration": // fall-through
      case "event_modifiers": // fall-through
      case "indexer_modifiers": // fall-through
      case "operator_modifiers": // fall-through
      case "unary_operator_declarator": // fall-through
      case "binary_operator_declarator": // fall-through
      case "conversion_operator_declarator": // fall-through
      case "constructor_modifiers": // fall-through
      case "constructor_declarator": // fall-through
      case "struct_modifiers": // fall-through
      case "struct_body": // fall-through
      case "struct_member_declarations": // fall-through
      case "rank_specifiers": // fall-through
      case "dim_separators": // fall-through
      case "interface_modifiers": // fall-through
      case "interface_body": // fall-through
      case "interface_member_declarations": // fall-through
      case "interface_method_declaration": // fall-through
      case "interface_property_declaration": // fall-through
      case "interface_event_declaration": // fall-through
      case "interface_indexer_declaration": // fall-through
      case "enum_modifiers": // fall-through
      case "enum_body": // fall-through
      case "delegate_modifiers": // fall-through
      case "global_attribute_sections": // fall-through
      case "global_attribute_target_specifier": // fall-through
      case "attribute_sections": // fall-through
      case "attribute_target_specifier": // fall-through
      case "positional_argument": // fall-through
      case "attribute_argument_expression": // fall-through
      case "member_access": // fall-through
      case "simple_name": // fall-through
      case "invocation_expression": // fall-through
      case "element_access": // fall-through
      case "this_access": // fall-through
      case "base_access": // fall-through
      case "conditional_expression": // fall-through
      case "post_increment_expression": // fall-through
      case "post_decrement_expression": // fall-through
      case "parenthesized_expression": // fall-through
      case "pre_increment_expression": // fall-through
      case "pre_decrement_expression": // fall-through
        node.foldUnder();
        break;
      // Predefined Types
      case "bool": // fall-through
      case "decimal": // fall-through
      case "float": // fall-through
      case "double": // fall-through
      case "integral_type":
        if (!node.getParent().getTextLabel().equals("predefined_type")
                && !node.getParent().getTextLabel().equals("enum_base")) {
          node.gather("predefined_type");
        }
        break;
      case "object": // fall-through
      case "string": // fall-through
        // A grandparent relation - not achievable with GIFT
        if (!node.getParent().getParent().getTextLabel().equals("class_base")) {
          node.gather("predefined_type");
        }
        break;
      // Expressions
      case "assignment": // fall-through
      case "conditional_or_expression": // fall-through
      case "conditional_and_expression": // fall-through
      case "inclusive_or_expression": // fall-through
      case "exclusive_or_expression": // fall-through
      case "and_expression": // fall-through
      case "equality_expression": // fall-through
      case "shift_expression": // fall-through
      case "additive_expression": // fall-through
      case "multiplicative_expression": // fall-through
      case "relational_expression": // fall-through
      case "unary_expression": // fall-through
      case "primary_expression": // fall-through
      case "primary_no_array_creation_expression":
        if (node.getSibling() != null || node.getParent().getChild() != node) {
          ((GIFTNode) node.getChild()).gather("expression", ((GIFTNode) node.getChild()).getSiblings());
        }
        node.foldUnder();
        break;
      // Operators
      case "argument_list":
        if (node.getParent().getTextLabel().equals("argument_list")) {
          node.foldUnder();
        }
        break;
      case "expression_list":
        if (node.getParent().getTextLabel().equals("expression_list")) {
          node.foldUnder();
        }
        break;
      case "qualified_identifier":
        if (node.getParent().getTextLabel().equals("qualified_identifier")) {
          node.foldUnder();
        }
        break;
      case "constant_declarators":
        if (node.getParent().getTextLabel().equals("constant_declarators")) {
          node.foldUnder();
        }
        break;
      case "variable_declarators":
        if (node.getParent().getTextLabel().equals("variable_declarators")) {
          node.foldUnder();
        }
        break;
      case "variable_initializer_list":
        if (node.getParent().getTextLabel().equals("variable_initializer_list")) {
          node.foldUnder();
        }
        break;
      case "interface_type_list":
        if (node.getParent().getTextLabel().equals("interface_type_list")
                || node.getParent().getTextLabel().equals("interface_type_list")) {
          node.foldUnder();
        }
        break;
      case "enum_member_declarations":
        if (node.getParent().getTextLabel().equals("enum_member_declarations")) {
          node.foldUnder();
        }
        break;
      case "attribute_list":
        if (node.getParent().getTextLabel().equals("attribute_list")) {
          node.foldUnder();
        }
        break;
      case "named_argument_list":
        if (node.getParent().getTextLabel().equals("named_argument_list")) {
          node.foldUnder();
        }
        break;
      case "!": // fall-through
      case "~": // fall-through
        if (node.getParent().getTextLabel().equals("unary_expression")
                || node.getParent().getTextLabel().equals("overloadable_unary_operator")) {
          node.gather("unary_operator");
        }
        break;
      case "-": // fall-through
      case "+":
        if (node.getParent().getTextLabel().equals("unary_expression")
                || node.getParent().getTextLabel().equals("overloadable_unary_operator")) {
          node.gather("unary_operator");
        }
        if (node.getParent().getTextLabel().equals("additive_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "<<": // fall-through
      case ">>":
        if (node.getParent().getTextLabel().equals("shift_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "*": // fall-through
      case "/": // fall-through
      case "%":
        if (node.getParent().getTextLabel().equals("multiplicative_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "<": // fall-through
      case ">": // fall-through
      case "<=": // fall-through
      case ">=":
        if (node.getParent().getTextLabel().equals("relational_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "==": // fall-through
      case "!=":
        if (node.getParent().getTextLabel().equals("equality_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "&":
        if (node.getParent().getTextLabel().equals("and_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "^":
        if (node.getParent().getTextLabel().equals("exclusive_or_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "&&":
        if (node.getParent().getTextLabel().equals("conditional_and_expression")) {
          node.gather("binary_operator");
        }
        break;
      case "||":
        if (node.getParent().getTextLabel().equals("conditional_or_expression")) {
          node.gather("binary_operator");
        }
        break;
      case "|":
        if (node.getParent().getTextLabel().equals("inclusive_or_expression")) {
          node.gather("overloadable_binary_operator");
          node.gather("binary_operator");
        }
        break;
      case "--":
        if (node.getParent().getTextLabel().equals("pre_decrement_expression")
                || node.getParent().getTextLabel().equals("post_decrement_expression")
                || node.getParent().getTextLabel().equals("overloadable_unary_operator"))
          node.gather("unary_assignment_operator");
        break;
      case "++":
        if (node.getParent().getTextLabel().equals("pre_increment_expression")
                || node.getParent().getTextLabel().equals("post_increment_expression")
                || node.getParent().getTextLabel().equals("overloadable_unary_operator"))
          node.gather("unary_assignment_operator");
        break;
      case "struct_member_declaration": // fall-through
      case "class_member_declaration":
        ((GIFTNode) node.getChild()).gather("member_declaration");
        node.foldUnder();
        break;
      case "statement_expression":
        ((GIFTNode) node.getChild()).gather("expression", ((GIFTNode) node.getChild()).getSiblings());
        node.foldUnder();
        break;
      case "constant_modifier": // fall-through
      case "field_modifier": // fall-through
      case "method_modifier": // fall-through
      case "property_modifier": // fall-through
      case "event_modifier": // fall-through
      case "indexer_modifier": // fall-through
      case "operator_modifier": // fall-through
      case "struct_modifier": // fall-through
      case "interface_modifier": // fall-through
      case "enum_modifier": // fall-through
      case "delegate_modifier": // fall-through
      case "constructor_modifier": // fall-through
      case "class_modifier":
        ((GIFTNode) node.getChild()).gather("modifier");
        node.foldUnder();
        break;
      case "method_body": // fall-through
      case "accessor_body": // fall-through
      case "operator_body": // fall-through
      case "constructor_body": // fall-through
      case "static_constructor_body": // fall-through
      case "destructor_body": // fall-through
        ((GIFTNode) node.getChild()).gather("body");
        node.foldUnder();
        break;
      case "local_variable_declarator":
        ((GIFTNode) node.getChild()).gather("variable_declarator", ((GIFTNode) node.getChild()).getSiblings());
        node.foldUnder();
        break;
      case "local_variable_initializer":
        ((GIFTNode) node.getChild()).gather("variable_initializer", ((GIFTNode) node.getChild()).getSiblings());
        node.foldUnder();
        break;
      case "delegate_type":
        if (node.getParent().getTextLabel().equals("delegate_creation_expression")) {
          ((GIFTNode) node.getChild()).gather("type", ((GIFTNode) node.getChild()).getSiblings());
        }
        node.foldUnder();
        break;
      case "expression":
        if (node.getParent().getTextLabel().equals("delegate_creation_expression")) {
          node.gather("argument");
        }
        break;
      case "type_name":
        String parent = node.getParent().getTextLabel();
        if (parent.equals("class_type") || parent.equals("delegate_type") || parent.equals("enum_type")
                || parent.equals("struct_type")) {
          node = node.gather("qualified_identifier");
        }
        node.foldUnder();
        break;
      case "class_type":
        if (node.getParent().getTextLabel().equals("specific_catch_clause")) {
          ((GIFTNode) node.getChild()).gather("type", ((GIFTNode) node.getChild()).getSiblings());
        }
        node.foldUnder();
      case "member_name":
        ((GIFTNode) node.getChild()).gather("qualified_identifier", ((GIFTNode) node.getChild()).getSiblings());
        node.foldUnder();
        break;
      case "interface_type":
        if (node.getParent().getTextLabel().equals("reference_type")
                || node.getParent().getTextLabel().equals("interface_type_list")) {
          node = node.gather("qualified_identifier");
        }
        node.foldUnder();
        break;
      case "namespace_name":
        ((GIFTNode) node.getChild()).gather("qualified_identifier", ((GIFTNode) node.getChild()).getSiblings());
        node.foldUnder();
        break;
      case "local_variable_declarators":
        if (node.getParent().getTextLabel().equals("local_variable_declaration")) {
          node = node.gather("variable_declarators");
        }
        node.foldUnder();
        break;
      case "statement_expression_list":
        if (node.getParent().getTextLabel().equals("for_initializer")
                || node.getParent().getTextLabel().equals("for_iterator")) {
          node = node.gather("expression_list");
        }
        node.foldUnder();
        break;
      case "namespace_or_type_name":
        if (node.getParent().getTextLabel().equals("using_alias_directive")) {
          node = node.gather("qualified_identifier");
        }
        node.foldUnder();
        break;
      case "void": // follow-through
      case "type":
        if (node.getParent().getTextLabel().equals("typeof_expression")) {
          node.gather("return_type");
        } else if (node.getParent().getTextLabel().equals("event_declaration")
                && node.getSibling().getTextLabel().equals("variable_declarators")) {
          node.gather("local_variable_declaration", (GIFTNode) node.getSibling());
        }
        break;
      case "this": // follow-through
      case "base":
        if (node.getParent().getTextLabel().equals("constructor_initializer")) {
          node.gather("constructor_order");
        }
        break;
      case "struct_interfaces":
        if (node.getParent().getTextLabel().equals("struct_declaration")) {
          node = node.gather("interface_base");
        }
        node.foldUnder();
        break;
      case "attribute_name":
        if (node.getParent().getTextLabel().equals("attribute")) {
          node = node.gather("qualified_identifier");
        }
        node.foldUnder();
        break;
      case "positional_argument_list": // fall-through
        if (node.getParent().getTextLabel().equals("attribute_arguments")) {
          node = node.gather("expression_list");
        }
        node.foldUnder();
        break;
    }
  }
}
