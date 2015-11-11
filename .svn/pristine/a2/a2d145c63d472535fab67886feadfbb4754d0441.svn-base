package uk.ac.rhul.csle.tooling.CSCompiler;

import uk.ac.rhul.csle.tooling.lexer.MultiLexer;

/**
 * Maintains the functions which define the lexical disambiguation rules used
 * for the C# case study.
 * 
 * @author Robert Michael Walsh
 *
 */
public class CSLexer {

  /**
   * A function that initialises all lexical longest match rules
   *
   * @param lex
   *          The lexer to initialise the longest match rules for
   */
  public static void longestMatchesCS(MultiLexer lex) {
    lex.addLeftLongestGrouping("/", "comment");
    lex.addLeftLongestGrouping("whitespace");
    lex.addLeftLongestGrouping("new_line");

    lex.addLeftLongestGrouping("string_literal");

    lex.addLeftLongestGrouping("integer_literal", "real_literal", true);
    lex.addLeftLongestGrouping(".", "real_literal", true);
    lex.addLeftLongestGrouping("in", "int", "internal", "interface", "identifier");
    lex.addLeftLongestGrouping("do", "double", "identifier");
    lex.addLeftLongestGrouping("for", "foreach", "identifier");
    lex.addLeftLongestGrouping("identifier");
    lex.addLeftLongestGrouping("abstract", "identifier", true);
    lex.addLeftLongestGrouping("as", "identifier", true);
    lex.addLeftLongestGrouping("base", "identifier", true);
    lex.addLeftLongestGrouping("base", "identifier", true);
    lex.addLeftLongestGrouping("bool", "identifier", true);
    lex.addLeftLongestGrouping("break", "identifier", true);
    lex.addLeftLongestGrouping("byte", "identifier", true);
    lex.addLeftLongestGrouping("case", "identifier", true);
    lex.addLeftLongestGrouping("catch", "identifier", true);
    lex.addLeftLongestGrouping("char", "identifier", true);
    lex.addLeftLongestGrouping("checked", "identifier", true);
    lex.addLeftLongestGrouping("class", "identifier", true);
    lex.addLeftLongestGrouping("const", "identifier", true);
    lex.addLeftLongestGrouping("continue", "identifier", true);
    lex.addLeftLongestGrouping("decimal", "identifier", true);
    lex.addLeftLongestGrouping("default", "identifier", true);
    lex.addLeftLongestGrouping("else", "identifier", true);
    lex.addLeftLongestGrouping("enum", "identifier", true);
    lex.addLeftLongestGrouping("event", "identifier", true);
    lex.addLeftLongestGrouping("explicit", "identifier", true);
    lex.addLeftLongestGrouping("finally", "identifier", true);
    lex.addLeftLongestGrouping("fixed", "identifier", true);
    lex.addLeftLongestGrouping("float", "identifier", true);
    lex.addLeftLongestGrouping("extern", "identifier", true);
    lex.addLeftLongestGrouping("goto", "identifier", true);
    lex.addLeftLongestGrouping("if", "identifier", true);
    lex.addLeftLongestGrouping("implicit", "identifier", true);
    lex.addLeftLongestGrouping("is", "identifier", true);
    lex.addLeftLongestGrouping("lock", "identifier", true);
    lex.addLeftLongestGrouping("long", "identifier", true);
    lex.addLeftLongestGrouping("namespace", "identifier", true);
    lex.addLeftLongestGrouping("new", "identifier", true);
    lex.addLeftLongestGrouping("object", "identifier", true);
    lex.addLeftLongestGrouping("operator", "identifier", true);
    lex.addLeftLongestGrouping("out", "identifier", true);
    lex.addLeftLongestGrouping("override", "identifier", true);
    lex.addLeftLongestGrouping("params", "identifier", true);
    lex.addLeftLongestGrouping("private", "identifier", true);
    lex.addLeftLongestGrouping("ref", "identifier", true);
    lex.addLeftLongestGrouping("sbyte", "identifier", true);
    lex.addLeftLongestGrouping("sealed", "identifier", true);
    lex.addLeftLongestGrouping("short", "identifier", true);
    lex.addLeftLongestGrouping("stackalloc", "identifier", true);
    lex.addLeftLongestGrouping("static", "identifier", true);
    lex.addLeftLongestGrouping("string", "identifier", true);
    lex.addLeftLongestGrouping("struct", "identifier", true);
    lex.addLeftLongestGrouping("switch", "identifier", true);
    lex.addLeftLongestGrouping("this", "identifier", true);
    lex.addLeftLongestGrouping("throw", "identifier", true);
    lex.addLeftLongestGrouping("try", "identifier", true);
    lex.addLeftLongestGrouping("typeof", "identifier", true);
    lex.addLeftLongestGrouping("uint", "identifier", true);
    lex.addLeftLongestGrouping("ulong", "identifier", true);
    lex.addLeftLongestGrouping("unchecked", "identifier", true);
    lex.addLeftLongestGrouping("unsafe", "identifier", true);
    lex.addLeftLongestGrouping("ushort", "identifier", true);
    lex.addLeftLongestGrouping("using", "identifier", true);
    lex.addLeftLongestGrouping("virtual", "identifier", true);
    lex.addLeftLongestGrouping("void", "identifier", true);
    lex.addLeftLongestGrouping("volatile", "identifier", true);
    lex.addLeftLongestGrouping("while", "identifier", true);

    lex.addLeftLongestGrouping("method", "identifier", true);
    lex.addLeftLongestGrouping("assembly", "identifier", true);
    lex.addLeftLongestGrouping("module", "identifier", true);
    lex.addLeftLongestGrouping("field", "identifier", true);
    lex.addLeftLongestGrouping("param", "identifier", true);
    lex.addLeftLongestGrouping("property", "identifier", true);
    lex.addLeftLongestGrouping("type", "identifier", true);
    lex.addLeftLongestGrouping("add", "identifier", true);
    lex.addLeftLongestGrouping("remove", "identifier", true);
    lex.addLeftLongestGrouping("get", "identifier", true);
    lex.addLeftLongestGrouping("set", "identifier", true);

    lex.addLeftLongestGrouping("null_literal", "identifier", true);
    lex.addLeftLongestGrouping("boolean_literal", "identifier", true);

    lex.addLeftLongestGrouping("=", "==");
    lex.addLeftLongestGrouping("!", "!=");
    lex.addLeftLongestGrouping("<", "<=", "<<", "<<=");
    lex.addLeftLongestGrouping(">", ">=", ">>", ">>=");
    lex.addLeftLongestGrouping("+", "++", "+=");
    lex.addLeftLongestGrouping("-", "--", "-=");
    lex.addLeftLongestGrouping("*", "*=");
    lex.addLeftLongestGrouping("/", "/=");
    lex.addLeftLongestGrouping("%", "%=");
    lex.addLeftLongestGrouping("|", "||", "|=");
    lex.addLeftLongestGrouping("^", "^=");
    lex.addLeftLongestGrouping("&", "&&", "&=");
  }

  /**
   * A function that initialises all lexical priority rules
   *
   * @param lex
   *          The lexer to initialise the priority rules for
   */
  public static void equalPriorityMatchesCS(MultiLexer lex) {

    lex.addRestrictedPriorityGrouping("identifier", "abstract");
    lex.addRestrictedPriorityGrouping("identifier", "as");
    lex.addRestrictedPriorityGrouping("identifier", "base");
    lex.addRestrictedPriorityGrouping("identifier", "bool");
    lex.addRestrictedPriorityGrouping("identifier", "break");
    lex.addRestrictedPriorityGrouping("identifier", "byte");
    lex.addRestrictedPriorityGrouping("identifier", "case");
    lex.addRestrictedPriorityGrouping("identifier", "catch");
    lex.addRestrictedPriorityGrouping("identifier", "char");
    lex.addRestrictedPriorityGrouping("identifier", "checked");
    lex.addRestrictedPriorityGrouping("identifier", "class");
    lex.addRestrictedPriorityGrouping("identifier", "const");
    lex.addRestrictedPriorityGrouping("identifier", "continue");
    lex.addRestrictedPriorityGrouping("identifier", "decimal");
    lex.addRestrictedPriorityGrouping("identifier", "default");
    lex.addRestrictedPriorityGrouping("identifier", "delegate");
    lex.addRestrictedPriorityGrouping("identifier", "do");
    lex.addRestrictedPriorityGrouping("identifier", "double");
    lex.addRestrictedPriorityGrouping("identifier", "else");
    lex.addRestrictedPriorityGrouping("identifier", "enum");
    lex.addRestrictedPriorityGrouping("identifier", "event");
    lex.addRestrictedPriorityGrouping("identifier", "explicit");
    lex.addRestrictedPriorityGrouping("identifier", "extern");
    lex.addRestrictedPriorityGrouping("identifier", "finally");
    lex.addRestrictedPriorityGrouping("identifier", "fixed");
    lex.addRestrictedPriorityGrouping("identifier", "float");
    lex.addRestrictedPriorityGrouping("identifier", "for");
    lex.addRestrictedPriorityGrouping("identifier", "foreach");
    lex.addRestrictedPriorityGrouping("identifier", "goto");
    lex.addRestrictedPriorityGrouping("identifier", "if");
    lex.addRestrictedPriorityGrouping("identifier", "implicit");
    lex.addRestrictedPriorityGrouping("identifier", "in");
    lex.addRestrictedPriorityGrouping("identifier", "int");
    lex.addRestrictedPriorityGrouping("identifier", "interface");
    lex.addRestrictedPriorityGrouping("identifier", "internal");
    lex.addRestrictedPriorityGrouping("identifier", "is");
    lex.addRestrictedPriorityGrouping("identifier", "lock");
    lex.addRestrictedPriorityGrouping("identifier", "long");
    lex.addRestrictedPriorityGrouping("identifier", "namespace");
    lex.addRestrictedPriorityGrouping("identifier", "new");
    lex.addRestrictedPriorityGrouping("identifier", "object");
    lex.addRestrictedPriorityGrouping("identifier", "operator");
    lex.addRestrictedPriorityGrouping("identifier", "out");
    lex.addRestrictedPriorityGrouping("identifier", "override");
    lex.addRestrictedPriorityGrouping("identifier", "params");
    lex.addRestrictedPriorityGrouping("identifier", "private");
    lex.addRestrictedPriorityGrouping("identifier", "protected");
    lex.addRestrictedPriorityGrouping("identifier", "public");
    lex.addRestrictedPriorityGrouping("identifier", "readonly");
    lex.addRestrictedPriorityGrouping("identifier", "ref");
    lex.addRestrictedPriorityGrouping("identifier", "return");
    lex.addRestrictedPriorityGrouping("identifier", "sbyte");
    lex.addRestrictedPriorityGrouping("identifier", "sealed");
    lex.addRestrictedPriorityGrouping("identifier", "short");
    lex.addRestrictedPriorityGrouping("identifier", "stackalloc");
    lex.addRestrictedPriorityGrouping("identifier", "static");
    lex.addRestrictedPriorityGrouping("identifier", "string");
    lex.addRestrictedPriorityGrouping("identifier", "struct");
    lex.addRestrictedPriorityGrouping("identifier", "switch");
    lex.addRestrictedPriorityGrouping("identifier", "this");
    lex.addRestrictedPriorityGrouping("identifier", "throw");
    lex.addRestrictedPriorityGrouping("identifier", "try");
    lex.addRestrictedPriorityGrouping("identifier", "typeof");
    lex.addRestrictedPriorityGrouping("identifier", "uint");
    lex.addRestrictedPriorityGrouping("identifier", "ulong");
    lex.addRestrictedPriorityGrouping("identifier", "unchecked");
    lex.addRestrictedPriorityGrouping("identifier", "unsafe");
    lex.addRestrictedPriorityGrouping("identifier", "ushort");
    lex.addRestrictedPriorityGrouping("identifier", "using");
    lex.addRestrictedPriorityGrouping("identifier", "virtual");
    lex.addRestrictedPriorityGrouping("identifier", "void");
    lex.addRestrictedPriorityGrouping("identifier", "volatile");
    lex.addRestrictedPriorityGrouping("identifier", "while");
    lex.addRestrictedPriorityGrouping("identifier", "boolean_literal");
    lex.addRestrictedPriorityGrouping("identifier", "null_literal");
  }
}
