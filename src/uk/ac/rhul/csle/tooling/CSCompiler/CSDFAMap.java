package uk.ac.rhul.csle.tooling.CSCompiler;

import java.util.Arrays;
import java.util.stream.Collectors;

import uk.ac.rhul.csle.tooling.lexer.BasicDFAMap;

/**
 * This class represents a finite-state automaton implementation of the C# 1.2
 * lexical specification. This implementation is hand-written and should not be
 * taken to be a model implementation.
 *
 * @author Robert Michael Walsh
 *
 */
public class CSDFAMap extends BasicDFAMap {

  /*
   * (non-Javadoc)
   * 
   * @see uk.ac.rhul.csle.tooling.lexer.BasicDFAMap#getKeywordString(java.lang.
   * String)
   */
  @Override
  public String getKeywordString(String token) {
    if (token.equals("null_literal")) {
      return "null";
    } else {
      return super.getKeywordString(token);
    }
  }

  /**
   * Constructs a new CSDFAMap instance and initialises all the Tokens and DFAs
   */
  public CSDFAMap() {
    super();
  }

  /**
   * Private function initialising all the tokens and DFAs in this specification
   */
  @Override
  protected void initialise() {
    /**
     * The full list of tokens in C# 1.2
     * 
     */
    TOKENS = new String[] { "whitespace", "comment", "identifier", "abstract", "as", "base", "bool", "break", "byte",
            "case", "catch", "char", "checked", "class", "const", "continue", "decimal", "default", "delegate", "do",
            "double", "else", "enum", "event", "explicit", "extern", "false", "finally", "fixed", "float", "for",
            "foreach", "goto", "if", "implicit", "in", "int", "interface", "internal", "is", "lock", "long",
            "namespace", "new", "object", "operator", "out", "override", "params", "private", "protected", "public",
            "readonly", "ref", "return", "sbyte", "sealed", "short", "stackalloc", "static", "string", "struct",
            "switch", "this", "throw", "try", "typeof", "uint", "ulong", "unchecked", "unsafe", "ushort", "using",
            "virtual", "void", "volatile", "while", "method", "integer_literal", "real_literal", "true",
            "character_literal", "string_literal", ".", ",", "(", ")", "[", "]", "++", "--", "new_line", "+", "-", "!",
            "*", "/", "%", "|", "<<", ">>", "<", ">", "<=", ">=", "==", "!=", "&", "^", "&&", "||", "?", ":", ";", "=",
            "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "<<=", ">>=", "{", "}", "~", "assembly", "module", "field",
            "param", "property", "type", "add", "remove", "get", "set", "null_literal" };

    /**
     * The list of tokens in C# 1.2 that correspond to layout (or whitespace)
     */
    LAYOUTTOKENS = new String[] { "whitespace", "new_line", "comment" };

    /**
     * The set of tokens in C# 1.2 which correspond to a single lexeme
     */
    keywords = Arrays.stream(TOKENS).collect(Collectors.toSet());

    /*
     * character_literal : '\'' [any unicode character] '\''
     */
    final DFA c_l = new DFA(20);

    c_l.addTransition(0, 1, '\'');
    c_l.addAllUnicodeTransition(1, 2, '\'', '\\', '\r', '\n');
    c_l.addTransition(2, 19, '\'');
    c_l.addTransition(1, 3, '\\');
    c_l.addTransition(3, 4, '\'', '"', '\\', '0', 'a', 'b', 'f', 'n', 'r', 't', 'v');
    c_l.addTransition(4, 19, '\'');
    c_l.addTransition(3, 5, 'x');
    c_l.addTransition(5, 6, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(6, 19, '\'');
    c_l.addTransition(6, 7, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(7, 19, '\'');
    c_l.addTransition(7, 8, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(8, 19, '\'');
    c_l.addTransition(8, 9, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(9, 19, '\'');
    c_l.addTransition(3, 10, 'u');
    c_l.addTransition(10, 11, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(11, 12, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(12, 13, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(13, 14, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(14, 19, '\'');
    c_l.addTransition(3, 15, 'U');
    c_l.addTransition(15, 16, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(16, 17, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(17, 18, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addTransition(18, 10, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    c_l.addAcceptingState(19);

    DFAMap.put("character_literal", c_l);
    keywords.remove("character_literal");

    /*
     * comment : ('/' '/' [any unicode character] | '/' '*' ([any unicode
     * character except *] | '*'+ [any unicode character except /])* '*'+ '/'
     */
    final DFA comment = new DFA(8);

    comment.addTransition(0, 1, '/');
    comment.addTransition(1, 2, '/');
    comment.addAllUnicodeTransition(2, 2, '\r', '\n');
    comment.addTransition(1, 3, '*');
    comment.addAllUnicodeTransition(3, 4, '*');
    comment.addTransition(3, 5, '*');
    comment.addAllUnicodeTransition(4, 4, '*');
    comment.addTransition(4, 5, '*');
    comment.addTransition(5, 5, '*');
    comment.addAllUnicodeTransition(5, 6, '/');
    comment.addAllUnicodeTransition(6, 4, '*');
    comment.addTransition(6, 5, '*');
    comment.addTransition(5, 7, '/');
    comment.addAcceptingState(2);
    comment.addAcceptingState(7);

    DFAMap.put("comment", comment);
    keywords.remove("comment");

    /*
     * identifier : '@'? [a-zA-Z_] ([0-9a-zA-Z_] | '\' ('u'|'U') [0-9a-fA-F]
     * [0-9a-fA-F] [0-9a-fA-F] ([0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F] [0-9a-fA-F]
     * [0-9a-fA-F])?)*
     */
    final DFA id = new DFA(12);

    id.addTransition(0, 1, '@');
    id.addTransition(1, 2, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_');
    id.addTransition(0, 2, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_');
    id.addTransition(2, 2, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', '_');
    id.addTransition(2, 3, '\\');
    id.addTransition(3, 4, 'u');
    id.addTransition(4, 5, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(5, 6, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(6, 7, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(7, 2, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(3, 8, 'U');
    id.addTransition(8, 9, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(9, 10, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(10, 11, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addTransition(11, 4, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    id.addAcceptingState(2);

    DFAMap.put("identifier", id);
    keywords.remove("identifier");

    /*
     * integer_literal : ([0-9]+ | '0' ('x'|'X') [0-9a-fA-F]+) (('u'|'U')
     * ('l'|'L')? | ('l'|'L') ('u'|'U')?)?
     */
    final DFA i_l = new DFA(8);

    i_l.addTransition(0, 1, '1', '2', '3', '4', '5', '6', '7', '8', '9');
    i_l.addTransition(1, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    i_l.addTransition(0, 2, '0');
    i_l.addTransition(2, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    i_l.addTransition(2, 3, 'x', 'X');
    i_l.addTransition(3, 4, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    i_l.addTransition(4, 4, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    i_l.addTransition(1, 5, 'u', 'U');
    i_l.addTransition(1, 6, 'l', 'L');
    i_l.addTransition(4, 5, 'u', 'U');
    i_l.addTransition(4, 6, 'l', 'L');
    i_l.addTransition(5, 7, 'l', 'L');
    i_l.addTransition(6, 7, 'u', 'U');
    i_l.addAcceptingState(1);
    i_l.addAcceptingState(2);
    i_l.addAcceptingState(4);
    i_l.addAcceptingState(5);
    i_l.addAcceptingState(6);
    i_l.addAcceptingState(7);

    DFAMap.put("integer_literal", i_l);
    keywords.remove("integer_literal");

    /*
     * new_line : ('\r' '\n'? | '\n' )
     */
    final DFA n_l = new DFA(3);

    n_l.addTransition(0, 1, '\r');
    n_l.addTransition(0, 2, '\n');
    n_l.addTransition(1, 2, '\n');
    n_l.addAcceptingState(1);
    n_l.addAcceptingState(2);

    DFAMap.put("new_line", n_l);
    keywords.remove("new_line");

    /*
     * real_literal : ([0-9]+ ('f'|'F'|'d'|'D'|'m'|'M') | [0-9]* '.' [0-9]+
     * (('e'|'E') ('+'|'-')? [0-9]+)? ('f'|'F'|'d'|'D'|'m'|'M')?) | [0-9]+
     * ('e'|'E') ('+'|'-')? [0-9]+ ('f'|'F'|'d'|'D'|'m'|'M') )
     */
    final DFA r_l = new DFA(8);

    r_l.addTransition(0, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(0, 2, '.');
    r_l.addTransition(1, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(1, 2, '.');
    r_l.addTransition(1, 4, 'e', 'E');
    r_l.addTransition(1, 7, 'F', 'f', 'D', 'd', 'M', 'm');
    r_l.addTransition(2, 3, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(3, 3, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(3, 4, 'e', 'E');
    r_l.addTransition(3, 7, 'F', 'f', 'D', 'd', 'M', 'm');
    r_l.addTransition(4, 5, '+', '-');
    r_l.addTransition(4, 6, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(5, 6, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(6, 6, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    r_l.addTransition(6, 7, 'F', 'f', 'D', 'd', 'M', 'm');
    r_l.addAcceptingState(3);
    r_l.addAcceptingState(6);
    r_l.addAcceptingState(7);

    DFAMap.put("real_literal", r_l);
    keywords.remove("real_literal");

    /*
     * string_literal : '"' [any unicode character except " \ or new-line]* '"'
     * | '@' '"' ([any unicode character except "] | '"' '"')* '"'
     */
    final DFA s_l = new DFA(19);

    s_l.addTransition(0, 1, '"');
    s_l.addTransition(0, 16, '@');
    s_l.addAllUnicodeTransition(1, 1, '"', '\\', '\n', '\r');
    s_l.addTransition(1, 2, '\\');
    s_l.addTransition(1, 15, '"');
    s_l.addTransition(2, 3, 'x');
    s_l.addTransition(2, 7, 'u');
    s_l.addTransition(2, 11, 'U');
    s_l.addTransition(2, 1, '\'', '"', '\\', '0', 'a', 'b', 'f', 'n', 'r', 't', 'v');
    s_l.addTransition(3, 4, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(3, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(4, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(4, 5, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(5, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(5, 6, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(6, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(7, 8, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(8, 9, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(9, 10, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(10, 1, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(11, 12, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(12, 13, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(13, 14, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');
    s_l.addTransition(14, 7, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B',
            'C', 'D', 'E', 'F');

    s_l.addTransition(16, 17, '"');
    s_l.addAllUnicodeTransition(17, 17, '"');
    s_l.addTransition(17, 18, '"');
    s_l.addTransition(18, 17, '"');

    s_l.addAcceptingState(15);
    s_l.addAcceptingState(18);

    DFAMap.put("string_literal", s_l);
    keywords.remove("string_literal");

    /*
     * whitespace : ( ' ' | '\t' | '\v' | '\f')
     */
    final DFA whitespace = new DFA(2);

    whitespace.addTransition(0, 1, ' ', '\t', '\f');
    whitespace.addAcceptingState(1);

    DFAMap.put("whitespace", whitespace);
    keywords.remove("whitespace");

  }
}
