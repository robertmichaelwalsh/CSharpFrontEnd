package uk.ac.rhul.csle.tooling.trees;

import java.util.Stack;

import uk.ac.rhul.csle.gll.GLLSupport;

/**
 * A data structure representing a node in a derivation structure. This node can
 * have one or more children. A node can be associated with an underlying
 * character string .
 *
 * @author Robert Michael Walsh
 *
 */
public class BaseDerivationNode {

  /**
   * A value used to keep track of indentation whilst printing this node
   */
  private static int stackDepth = 0;

  /**
   * A unique number that is generated for each new
   * <code>BaseDerivationNode</code> created
   */
  private static long uniqueNumber = 0;

  /**
   * The type of symbol that this node represents (terminal, non-terminal,
   * epsilon, intermediate)
   */
  protected int labelKind;

  /**
   * The unique identifier for this <code>BaseDerivationNode</code> (must be
   * different from the original ART assigned node number as nodes in this
   * structure can be repeated)
   */
  protected long newID;

  /**
   * Information stored within this node.
   */
  protected int leftExtent, rightExtent;

  /**
   * An immediate neighbour of this node.
   */
  protected BaseDerivationNode parent, child, sibling, lastChild;

  /**
   * The text label for this node
   */
  protected String textLabel;

  /**
   * Creates a node that is a copy of an existing node
   *
   * @param cloneObject
   *          The node to copy.
   */
  public BaseDerivationNode(BaseDerivationNode cloneObject) {
    initialise(cloneObject.leftExtent, cloneObject.rightExtent);
    labelKind = cloneObject.labelKind;
    textLabel = cloneObject.textLabel;
  }

  /**
   * Constructs a new <code>BaseDerivationNode</code> with the given label, left
   * extent and right extent
   *
   * @param parserContext
   *          The parser context which this node is constructed from
   * @param label
   *          The ART generated parser label number
   * @param leftExtent
   *          The left extent of this node
   * @param rightExtent
   *          The right extent of this node
   */
  public BaseDerivationNode(GLLSupport parserContext, int label, int leftExtent, int rightExtent) {
    initialise(leftExtent, rightExtent);
    labelKind = parserContext.getLabelKind(label);
    textLabel = parserContext.getLabelString(label);
  }

  /**
   * Constructs a new <code>BaseDerivationNode</code> with the given node
   * number, label, left extent and right extent.
   * <p>
   * Also specifies the parent of this node.
   *
   * @param parserContext
   *          The parser context which this node is constructed from
   * @param label
   *          The ART generate parser label number
   * @param leftExtent
   *          The left extent of this node
   * @param rightExtent
   *          The right extent of this node
   * @param parent
   *          The parent of this node
   */
  public BaseDerivationNode(GLLSupport parserContext, int label, int leftExtent, int rightExtent,
          BaseDerivationNode parent) {
    initialise(leftExtent, rightExtent);
    labelKind = parserContext.getLabelKind(label);
    textLabel = parserContext.getLabelString(label);
    setParent(parent);
  }

  /**
   * Constructs a new <code>BaseDerivationNode</code> with the given node
   * number, label, left extent and right extent.
   * <p>
   * Also specifies the parent, left-most child and immediate right sibling of
   * this node.
   *
   * @param parserContext
   *          The parser context which this node is constructed from
   * @param label
   *          The ART generate parser label number
   * @param leftExtent
   *          The left extent of this node
   * @param rightExtent
   *          The right extent of this node
   * @param parent
   *          The parent of this node
   * @param child
   *          The left-most child of this node
   * @param sibling
   *          The immediate right sibling of this node
   */
  public BaseDerivationNode(GLLSupport parserContext, int label, int leftExtent, int rightExtent,
          BaseDerivationNode parent, BaseDerivationNode child, BaseDerivationNode sibling) {
    initialise(leftExtent, rightExtent);
    labelKind = parserContext.getLabelKind(label);
    textLabel = parserContext.getLabelString(label);
    setChild(child);
    setSibling(sibling);
    setParent(parent);
  }

  /**
   * Adds a node as the right-most child of this node.
   *
   * @param child
   *          The node to add as a child.
   */
  public void addChild(BaseDerivationNode child) {
    if (this.child == null) {
      setChild(child);
    } else {
      lastChild.setSibling(child);
      BaseDerivationNode last = child;
      if (child != null) {
        for (BaseDerivationNode current = child; current != null; current = current.sibling) {
          current.setParent(this);
          last = current;
        }
      }
      lastChild = last;
    }
  }

  /**
   * Returns a copy of the entire tree rooted at this node.
   *
   * @see java.lang.Object#clone()
   */
  @Override
  public BaseDerivationNode clone() {
    final BaseDerivationNode clone = new BaseDerivationNode(this);
    for (BaseDerivationNode tmp = child; tmp != null; tmp = tmp.sibling) {
      clone.addChild(tmp.clone());
    }
    return clone;
  }

  /**
   * Counts the number of nodes in the tree rooted at this node.
   *
   * @return The number of nodes in the tree rooted at this node
   */
  public int countNodes() {
    final Stack<BaseDerivationNode> stack = new Stack<>();
    stack.push(this);
    int count = 1;

    while (!stack.isEmpty()) {
      final BaseDerivationNode current = stack.pop();
      for (BaseDerivationNode tmp = current.getChild(); tmp != null; tmp = tmp.getSibling()) {
        count++;
        if (tmp.getChild() != null) {
          stack.push(tmp);
        }
      }
    }

    return count;
  }

  /**
   * Deletes the given child node. If the child had its own children, then these
   * children become children of <code>this</code> node in the former child's
   * place.
   *
   * @param child
   *          The child to delete
   */
  public void deleteChild(BaseDerivationNode child) {
    BaseDerivationNode parentToUse;
    if (child.getParent() == this) {
      parentToUse = this;
    } else {
      // Otherwise we assume this node was deleted
      parentToUse = child.getParent();
    }
    if (this.child == child) {
      if (child.getChild() == null) {
        parentToUse.setChild(child.getSibling());
      } else {
        parentToUse.setChild(child.getChild());

        BaseDerivationNode orphan = child.getChild();
        while (orphan.getSibling() != null) {
          orphan = orphan.getSibling();
          orphan.setParent(parentToUse);
        }
        orphan.setSibling(child.getSibling());
      }
    } else if (this.child != null) {
      BaseDerivationNode current = this.child;
      while (current.getSibling() != null) {
        if (current.getSibling() == child) {
          break;
        }
        current = current.getSibling();
      }
      if (current == null) {
        System.err.println("Node not found.");
        return;
      }
      if (child.getChild() == null) {
        current.setSibling(child.getSibling());
      } else {
        current.setSibling(child.getChild());
        BaseDerivationNode orphan = child.getChild();
        while (orphan.getSibling() != null) {
          orphan = orphan.getSibling();
          orphan.setParent(parentToUse);
        }
        orphan.setSibling(child.getSibling());
      }
    } else {
      System.err.println("Node not found.");
    }
  }

  /**
   * Returns the left-most child of this node
   *
   * @return The left-most child of this node
   */
  public BaseDerivationNode getChild() {
    return child;
  }

  /**
   * Returns the type of symbol this node represents (As a
   * <code>GLLSupport</code> enum)
   *
   * @return the labelKind
   *
   * @see uk.ac.rhul.csle.gll.GLLSupport#getLabelKind(int)
   */
  public int getLabelKind() {
    return labelKind;
  }

  /**
   * Returns the right-most child of this node (null if no children).
   *
   * @return The right-most child of this node.
   */
  public BaseDerivationNode getLastChild() {
    return lastChild;
  }

  /**
   * Returns the left-extent of this node
   *
   * @return The left-extent of this node
   */
  public int getLeftExtent() {
    return leftExtent;
  }

  /**
   * @return This node's parent node.
   */
  public BaseDerivationNode getParent() {
    return parent;
  }

  /**
   * Returns the right-extent of this node
   *
   * @return the right-extent of this node
   */
  public int getRightExtent() {
    return rightExtent;
  }

  /**
   * Returns the immediate right sibling of this node (null if no right sibling)
   *
   * @return The immediate right sibling of this node.
   */
  public BaseDerivationNode getSibling() {
    return sibling;
  }

  /**
   * Returns the label of this node
   *
   * @return the node label
   */
  public String getTextLabel() {
    return textLabel;
  }

  /**
   * This function returns a string representing the underlying character
   * sequence that this node covers.
   *
   * @param inputString
   *          The original input string (required for context)
   * @return The underlying represented character sequence
   */
  public String getUnderlyingString(String inputString) {
    return inputString.substring(leftExtent, rightExtent).trim();
  }

  /**
   * Helper function for initialising a new instance of this
   * <code>BaseDerivationNode</code>
   *
   * @param leftExtent
   *          The left extent for this node
   * @param rightExtent
   *          The right extent for this node
   */
  private void initialise(int leftExtent, int rightExtent) {
    this.leftExtent = leftExtent;
    this.rightExtent = rightExtent;
    newID = uniqueNumber++;
  }

  /**
   * Replaces the children of this node with the given node and its siblings.
   *
   * @param child
   *          The left-most child of the new set of children
   */
  public void setChild(BaseDerivationNode child) {
    this.child = child;
    BaseDerivationNode last = child;
    if (child != null) {
      child.setParent(this);
      for (BaseDerivationNode current = child; current != null; current = current.sibling) {
        last = current;
      }
    }
    lastChild = last;
  }

  /**
   * Sets the <code>GLLSupport</code> label kind for this node
   *
   * @param labelKind
   *          the new label kind
   */
  public void setLabelKind(int labelKind) {
    this.labelKind = labelKind;
  }

  /**
   * Sets the left extent of this node
   *
   * @param leftExtent
   *          The new left extent of this node
   */
  public void setLeftExtent(int leftExtent) {
    this.leftExtent = leftExtent;
  }

  /**
   * Sets the parent of this node
   *
   * @param parent
   *          The new parent of this node
   */
  public void setParent(BaseDerivationNode parent) {
    this.parent = parent;
  }

  /**
   * Sets the right extent of this node
   *
   * @param rightExtent
   *          The new right extent of this node
   */
  public void setRightExtent(int rightExtent) {
    this.rightExtent = rightExtent;
  }

  /**
   * Sets the immediate right sibling of this node, replacing all siblings to
   * the right of this node.
   *
   * @param sibling
   *          The new right sibling of this node
   */
  public void setSibling(BaseDerivationNode sibling) {
    this.sibling = sibling;
    if (sibling != null) {
      sibling.setParent(getParent());
    }
  }

  /**
   * Sets the label of this node
   *
   * @param textLabel
   *          The new label for this node
   */
  public void setTextLabel(String textLabel) {
    this.textLabel = textLabel;
  }

  @Override
  public String toString() {
    return newID + ":" + textLabel + " " + leftExtent + "," + rightExtent;
  }

  /**
   * If the current node is T and the list of its children is T1,...,TN then
   * this method returns the string
   *
   * T(T1(..),...,TN(..))
   *
   * @return A string representation of the tree.
   */
  public String toTreeString() {
    final StringBuilder sb = new StringBuilder();

    if (getParent() == null) {
      stackDepth = 0;
    }
    if (labelKind != GLLSupport.ART_K_EPSILON) {
      if (labelKind == GLLSupport.ART_K_CASE_INSENSITIVE_TERMINAL) {
        sb.append('\"');
      } else if (labelKind == GLLSupport.ART_K_CASE_SENSITIVE_TERMINAL) {
        sb.append('\'');
      } else if (labelKind == GLLSupport.ART_K_CHARACTER_TERMINAL) {
        sb.append('`');
      }
      if (textLabel.equals(" EOS $")) {
        sb.append("ambig");
      } else {
        if (labelKind == GLLSupport.ART_K_CASE_SENSITIVE_TERMINAL) {
          sb.append(textLabel.replaceAll("'", "\'"));
        } else if (labelKind == GLLSupport.ART_K_CASE_INSENSITIVE_TERMINAL) {
          sb.append(textLabel.replaceAll("\"", "\""));
        } else {
          sb.append(textLabel);
        }
      }
      if (labelKind == GLLSupport.ART_K_CASE_INSENSITIVE_TERMINAL) {
        sb.append('"');
      } else if (labelKind == GLLSupport.ART_K_CASE_SENSITIVE_TERMINAL) {
        sb.append('\'');
      }

      if (child != null) {
        if (child.getLabelKind() != GLLSupport.ART_K_EPSILON && child.getSibling() == null) {
          sb.append("(");

        } else {
          sb.append("(\n");
          for (int i = 0; i < stackDepth + 1; i++) {
            sb.append("  ");
          }
        }
        stackDepth++;
        sb.append(child.toTreeString());
        for (BaseDerivationNode tmp = child.getSibling(); tmp != null; tmp = tmp.getSibling()) {
          if (tmp.getLabelKind() != GLLSupport.ART_K_EPSILON) {
            sb.append(' ' + "\n");
            for (int i = 0; i < stackDepth; i++) {
              sb.append("  ");
            }
            sb.append(tmp.toTreeString());
          }
        }
        stackDepth--;
        if (child.getLabelKind() != GLLSupport.ART_K_EPSILON && child.getSibling() == null) {
          sb.append(")");

        } else {
          sb.append("\n");
          for (int i = 0; i < stackDepth; i++) {
            sb.append("  ");
          }
          sb.append(")");
        }

      }
    }
    return sb.toString();
  }
}
