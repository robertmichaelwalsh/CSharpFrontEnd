package uk.ac.rhul.csle.tooling.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.ac.rhul.csle.gll.GLLSupport;

/**
 * An extension of <code>BaseDerivationNode</node> which includes operations
 * specific for GIFT transformations.
 *
 * @author Robert Michael Walsh
 *
 */
public class GIFTNode extends BaseDerivationNode {

  /**
   * A store of ID mappings made by tear operations of this node's children
   */
  private final Map<String, BaseDerivationNode> tearMappings;

  /**
   * Creates a node that is a copy of an existing node
   *
   * @param cloneObject
   *          The node to copy.
   */
  public GIFTNode(GIFTNode cloneObject) {
    super(cloneObject);
    tearMappings = cloneObject.tearMappings;
  }

  /**
   * Constructs a new <code>GIFTNode</code> with the given label, left extent
   * and right extent
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
  public GIFTNode(GLLSupport parserContext, int label, int leftExtent, int rightExtent) {
    super(parserContext, label, leftExtent, rightExtent);
    tearMappings = new HashMap<>();
  }

  /**
   * Constructs a new <code>GIFTNode</code> with the given node number, label,
   * left extent and right extent.
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
  public GIFTNode(GLLSupport parserContext, int label, int leftExtent, int rightExtent, GIFTNode parent) {
    super(parserContext, label, leftExtent, rightExtent, parent);
    tearMappings = new HashMap<>();
  }

  /**
   * Constructs a new <code>GIFTNode</code> with the given node number, label,
   * left extent and right extent.
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
  public GIFTNode(GLLSupport parserContext, int label, int leftExtent, int rightExtent, GIFTNode parent, GIFTNode child,
          GIFTNode sibling) {
    super(parserContext, label, leftExtent, rightExtent, parent, child, sibling);
    tearMappings = new HashMap<>();
  }

  /**
   *
   * @see BaseDerivationNode#clone()
   */
  @Override
  public GIFTNode clone() {
    final GIFTNode clone = new GIFTNode(this);
    for (GIFTNode tmp = (GIFTNode) child; tmp != null; tmp = (GIFTNode) tmp.sibling) {
      clone.addChild(tmp.clone());
    }
    return clone;
  }

  /**
   * Applies a fold-over operator on this node. This relabels the node's parent
   * with this node's label, and then replaces this node with its children.
   * <p>
   * Cannot be applied to the root node
   */
  public void foldOver() {
    if (parent != null) {
      parent.setTextLabel(textLabel);
      parent.deleteChild(this);
    } else {
      System.err.println("Cannot fold the root.");
    }
  }

  /**
   * Applies a fold-under operator on this node. This replaces this node with
   * its children.
   * <p>
   * Cannot be applied to the root node
   */
  public void foldUnder() {
    if (parent != null) {
      parent.deleteChild(this);
    } else {
      System.err.println("Cannot fold the root.");
    }
  }

  /**
   * Applies a gather operator making this node a child of a new node labelled
   * <code>to</code>, which takes this node's position in the tree.
   *
   * @param to
   *          The label of the new node to gather this node to.
   * @return The original node
   */
  public GIFTNode gather(String to) {
    final GIFTNode thisNode = clone();
    setTextLabel(to);
    thisNode.setChild(getChild());
    setChild(thisNode);
    setLabelKind(GLLSupport.ART_K_NONTERMINAL);
    return thisNode;
  }

  /**
   * Applies a gather operator making this node, and the given list of node
   * siblings, the children of a new node labelled <code>to</code>, which takes
   * this node's position in the tree.
   *
   * @param to
   *          The label of the new node to gather this node to.
   * @param siblings
   *          The list of siblings of this node that should also be gathered to
   *          the new node.
   */
  public void gather(String to, GIFTNode... siblings) {
    if (this.sibling != null && this.sibling != siblings[0]) {
      System.err.println("Provided list of siblings in a gather operator are not right siblings of (" + this + ")");
      return;
    }
    final GIFTNode thisNode = gather(to);
    GIFTNode temp = thisNode;
    for (final GIFTNode siblingI : siblings) {
      temp.setSibling(siblingI);
      temp = siblingI;
    }
    setSibling(temp.getSibling());
    temp.setSibling(null);
    setRightExtent(temp.getRightExtent());
  }

  /**
   * Retrieves the list of nodes that are right siblings of this node.
   *
   * @return A list of nodes that are right siblings of this node
   */
  public GIFTNode[] getSiblings() {
    final ArrayList<GIFTNode> siblings = new ArrayList<>();
    for (GIFTNode tmp = (GIFTNode) getSibling(); tmp != null; tmp = (GIFTNode) tmp.getSibling()) {
      siblings.add(tmp);
    }

    return siblings.toArray(new GIFTNode[siblings.size()]);
  }

  /**
   * Inserts the tree stored under <code>id</code> in the tear mappings for this
   * node as the <code>position</code>'th child of this node.
   * <p>
   * (Note: Very much work-in-progress)
   * 
   * @param position
   *          The position in the list of children for this node to insert this
   *          new tree.
   * @param id
   *          The identifier that the tree to insert is stored under in this
   *          node's tear mappings (this should be defined by some previous
   *          invocation of <code>tear(String)</code>
   * 
   * @see GIFTNode#tear(String)
   */
  public void insert(int position, String id) {
    if (tearMappings.containsKey(id)) {
      if (position < 0) {
        System.err.println("Cannot supply a negative value for position number");
      } else {
        if (child == null) {
          System.err.println("Invalid child position supplied for insert on (" + this + ").");
        } else {
          int i = 0;
          for (BaseDerivationNode node = child; node != null; node = node.getSibling(), ++i) {
            if (i == position) {
              final BaseDerivationNode insertNode = tearMappings.get(id);
              final BaseDerivationNode oldSibling = node.getSibling();
              node.setSibling(insertNode);
              insertNode.setSibling(oldSibling);
              if (oldSibling == null) {
                lastChild = insertNode;
              }
              break;
            }
          }
          if (i != position) {
            System.err.println("Invalid child position supplied for insert on (" + this + ").");
          }
        }
      }
    }
  }

  /**
   * Applies a tear operator on this node, deleting the entire tree rooted at
   * this node from the tree rooted at its parent.
   */
  public void tear() {
    if (parent != null) {
      // This is a very cheaty way of doing this...
      child = null;
      parent.deleteChild(this);
    } else {
      System.err.println("Cannot tear the root.");
    }
  }

  /**
   * Applies a tear operator on this node, deleting the entire tree rooted at
   * this node from the tree rooted at its parent. Stores the deleted tree under
   * the tear mapping for its parent under <code>id</code>
   * 
   * @param id
   *          The ID that this tree should be stored under in its parent's tear
   *          mappings.
   */
  public void tear(String id) {
    if (parent != null) {
      ((GIFTNode) parent).tearMappings.put(id, clone());
      tear();
    } else {
      System.err.println("Cannot tear the root");
    }
  }
}
