// This class defines the red black tree structure
import java.util.*;

public class RedBlackTree {
    /*
     * Create a variable rootNode
     * It is the root rbNode of red black tree
     */
    private RedBlackNode rootNode;

    /*
     * Create a variable extNode
     * It is the null external rbNode of red black tree
     */
    private RedBlackNode extNode;

    // Constructor of RedBlackTree class
    public RedBlackTree() {
        this.extNode = RedBlackNode.EXT_NODE;
        this.rootNode = extNode;
        this.rootNode.setLeftNode(extNode);
        this.rootNode.setRightNode(extNode);
    }

    // This function checks if the rbNode is left child of the tree
    private boolean isLeftChild(RedBlackNode rbNode) {
        return (rbNode == rbNode.getParentNode().getLeftNode());
    }

    // This function checks if the node is right child of the tree
    private boolean isRightChild(RedBlackNode rbNode) {
        return (rbNode == rbNode.getParentNode().getRightNode());
    }

    // This function updates the child link for parent node
    private void updateParentChild(RedBlackNode parentNode, RedBlackNode oldChildNode, RedBlackNode newChildNode) {
        // This sets parentNode as new child node's parent
        newChildNode.setParentNode(parentNode);

        // If parent node is equal to the external node, the new child node becomes the
        // new root node.
        if (parentNode == extNode) {
            rootNode = newChildNode;
            // The left or right child node of the parent is updated with the new child node
            // depending on the position of the old child node.
        } else if (isLeftChild(oldChildNode)) {
            parentNode.setLeftNode(newChildNode);
        } else {
            parentNode.setRightNode(newChildNode);
        }
    }

    // This method performs a left rotation on the given node.
    private void rotateLeft(RedBlackNode rbNode) {

        // It copies rbNode's right child into yNode.
        RedBlackNode yNode = rbNode.getRightNode();

        // It updates the right child of rbNode by copying the left subtree of yNode.
        rbNode.setRightNode(yNode.getLeftNode());

        // It update yNode's left child's parent by rbNode if it is not a leaf.
        // rbNode.
        if (yNode.getLeftNode() != extNode) {
            yNode.getLeftNode().setParentNode(rbNode);
        }
        updateParentChild(rbNode.getParentNode(), rbNode, yNode);
        yNode.setLeftNode(rbNode);
        rbNode.setParentNode(yNode);
    }

    // This method performs right rotation on the given node.
    private void rotateRight(RedBlackNode rbNode) {
        // It copies rbNode's left child into yNode.
        RedBlackNode yNode = rbNode.getLeftNode();

        // It updates the left child of rbNode by copying the right subtree of yNode.
        rbNode.setLeftNode(yNode.getRightNode());

        // It update yNode's right child's parent by rbNode if it is not a leaf.
        // rbNode.
        if (yNode.getRightNode() != extNode) {
            yNode.getRightNode().setParentNode(rbNode);
        }

        updateParentChild(rbNode.getParentNode(), rbNode, yNode);

        yNode.setRightNode(rbNode);
        rbNode.setParentNode(yNode);
    }

    /*
     * This method inserts node into the red black tree as per binary search
     * tree rules and then rebalances to
     * maintain red black tree property.
     */
    public void insert(RedBlackNode rbNode) {
        if (rbNode == null)
            throw new IllegalArgumentException("Invalid input. Cannot insert null into the tree.");

        RedBlackNode xNode = rootNode, yNode = extNode;

        while (xNode != extNode) {
            yNode = xNode;
            xNode = (rbNode.getRideNumber() < xNode.getRideNumber()) ? xNode.getLeftNode() : xNode.getRightNode();
        }

        rbNode.setParentNode(yNode);

        // It inserts the node at appropriate position by binary tree properties.
        if (yNode == extNode) {
            rootNode = rbNode;
        } else if (rbNode.getRideNumber() > yNode.getRideNumber()) {
            yNode.setRightNode(rbNode);
        }

        else if (rbNode.getRideNumber() < yNode.getRideNumber()) {
            yNode.setLeftNode(rbNode);
        }

        else if (rbNode.getRideNumber() == yNode.getRideNumber()) {
            throw new IllegalArgumentException("Trying to insert duplicate rbNode. RideNumber "
                    + rbNode.getRideNumber() + " already exists.");
        }

        insertionRebalance(rbNode);
    }

    /*
     * This method performs the rebalancing of the red black tree after insertion of
     * a new node to maintain the Red Black Tree property.
     */
    private void insertionRebalance(RedBlackNode rbNode) {
        RedBlackNode yNode = null;

        while (rbNode.getParentNode().getColor() == RedBlackNode.NodeColor.RED) {
            if (isLeftChild(rbNode.getParentNode())) {
                yNode = rbNode.getParentNode().getParentNode().getRightNode();
                if (yNode.getColor() == RedBlackNode.NodeColor.RED) {
                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.BLACK);
                    yNode.setColor(RedBlackNode.NodeColor.BLACK);
                    rbNode.getParentNode().getParentNode().setColor(RedBlackNode.NodeColor.RED);
                    rbNode = rbNode.getParentNode().getParentNode();
                } else {
                    if (isRightChild(rbNode)) {
                        rbNode = rbNode.getParentNode();
                        rotateLeft(rbNode);
                    }

                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.BLACK);
                    rbNode.getParentNode().getParentNode().setColor(RedBlackNode.NodeColor.RED);
                    rotateRight(rbNode.getParentNode().getParentNode());
                }
            } else {
                yNode = rbNode.getParentNode().getParentNode().getLeftNode();
                if (yNode.getColor() == RedBlackNode.NodeColor.RED) {
                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.BLACK);
                    yNode.setColor(RedBlackNode.NodeColor.BLACK);
                    rbNode.getParentNode().getParentNode().setColor(RedBlackNode.NodeColor.RED);
                    rbNode = rbNode.getParentNode().getParentNode();
                } else {
                    if (isLeftChild(rbNode)) {
                        rbNode = rbNode.getParentNode();
                        rotateRight(rbNode);
                    }

                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.BLACK);
                    rbNode.getParentNode().getParentNode().setColor(RedBlackNode.NodeColor.RED);
                    rotateLeft(rbNode.getParentNode().getParentNode());
                }
            }
        }

        rootNode.setColor(RedBlackNode.NodeColor.BLACK);
    }

    // This method removes the given node from the tree.
    public void delete(RedBlackNode rbNode) {
        if (rbNode == null)
            throw new IllegalArgumentException("Invalid input. Cannot remove null from the tree.");

        RedBlackNode xNode = null, yNode = rbNode;
        RedBlackNode.NodeColor nodeColor = yNode.getColor();

        if (rbNode.getLeftNode() == extNode) {
            xNode = rbNode.getRightNode();
            updateParentChild(rbNode.getParentNode(), rbNode, rbNode.getRightNode());
        } else if (rbNode.getRightNode() == extNode) {
            xNode = rbNode.getLeftNode();
            updateParentChild(rbNode.getParentNode(), rbNode, rbNode.getLeftNode());
        } else {
            yNode = getMinNode(rbNode.getRightNode());
            nodeColor = yNode.getColor();

            xNode = yNode.getRightNode();

            if (yNode.getParentNode() == rbNode) {
                xNode.setParentNode(yNode);
            } else {
                updateParentChild(yNode.getParentNode(), yNode, yNode.getRightNode());
                yNode.setRightNode(rbNode.getRightNode());
                yNode.getRightNode().setParentNode(yNode);
            }

            updateParentChild(rbNode.getParentNode(), rbNode, yNode);
            yNode.setLeftNode(rbNode.getLeftNode());
            yNode.getLeftNode().setParentNode(yNode);
            yNode.setColor(rbNode.getColor());
        }

        if (nodeColor == RedBlackNode.NodeColor.BLACK) {
            deletionRebalance(xNode);
        }
    }

    //This method performs the necessary rebalancing of the red black tree after a node deletion.
    private void deletionRebalance(RedBlackNode rbNode) {
        while (rbNode != rootNode && rbNode.getColor() == RedBlackNode.NodeColor.BLACK) {
            RedBlackNode sibling;
            if (isLeftChild(rbNode)) {
                sibling = rbNode.getParentNode().getRightNode();
                if (sibling.getColor() == RedBlackNode.NodeColor.RED) {
                    sibling.setColor(RedBlackNode.NodeColor.BLACK);
                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.RED);
                    rotateLeft(rbNode.getParentNode());
                    sibling = rbNode.getParentNode().getRightNode();
                }

                if (sibling.getLeftNode().getColor() == RedBlackNode.NodeColor.BLACK
                        && sibling.getRightNode().getColor() == RedBlackNode.NodeColor.BLACK) {
                    sibling.setColor(RedBlackNode.NodeColor.RED);
                    rbNode = rbNode.getParentNode();
                } else {
                    if (sibling.getRightNode().getColor() == RedBlackNode.NodeColor.BLACK) {
                        sibling.getLeftNode().setColor(RedBlackNode.NodeColor.BLACK);
                        sibling.setColor(RedBlackNode.NodeColor.RED);
                        rotateRight(sibling);
                        sibling = rbNode.getParentNode().getRightNode();
                    }

                    sibling.setColor(rbNode.getParentNode().getColor());
                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.BLACK);
                    sibling.getRightNode().setColor(RedBlackNode.NodeColor.BLACK);
                    rotateLeft(rbNode.getParentNode());
                    rbNode = rootNode;
                }
            } else {
                sibling = rbNode.getParentNode().getLeftNode();

                if (sibling.getRightNode().getColor() == RedBlackNode.NodeColor.BLACK
                        && sibling.getLeftNode().getColor() == RedBlackNode.NodeColor.BLACK) {
                    sibling.setColor(RedBlackNode.NodeColor.RED);
                    rbNode = rbNode.getParentNode();
                } else {
                    if (sibling.getLeftNode().getColor() == RedBlackNode.NodeColor.BLACK) {
                        sibling.getRightNode().setColor(RedBlackNode.NodeColor.BLACK);
                        sibling.setColor(RedBlackNode.NodeColor.RED);
                        rotateLeft(sibling);
                        sibling = rbNode.getParentNode().getLeftNode();
                    }

                    sibling.setColor(rbNode.getParentNode().getColor());
                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.BLACK);
                    sibling.getLeftNode().setColor(RedBlackNode.NodeColor.BLACK);
                    rotateRight(rbNode.getParentNode());
                    rbNode = rootNode;
                }

                if (sibling.getColor() == RedBlackNode.NodeColor.RED) {
                    sibling.setColor(RedBlackNode.NodeColor.BLACK);
                    rbNode.getParentNode().setColor(RedBlackNode.NodeColor.RED);
                    rotateRight(rbNode.getParentNode());
                    sibling = rbNode.getParentNode().getLeftNode();
                }

            }
        }

        rbNode.setColor(RedBlackNode.NodeColor.BLACK);
    }

    //This method returns the minimum node in the subtree rooted at the given node.
    private RedBlackNode getMinNode(RedBlackNode rbNode) {
        while (rbNode.getLeftNode() != extNode) {
            rbNode = rbNode.getLeftNode();
        }
        return rbNode;
    }

    // It searches and returns a list of nodes which satisfies the below condition.
    public List<RedBlackNode> searchInRange(int startRideNumber, int endRideNumber) {
        List<RedBlackNode> result = new ArrayList<>();
        searchInRangeRecursive(rootNode, startRideNumber, endRideNumber, result);
        return result;
    }

    private void searchInRangeRecursive(RedBlackNode rootNode, int startRideNumber, int endRideNumber,
            List<RedBlackNode> rbtNode) {
        if (rootNode == extNode)
            return;
        if (rootNode.getRideNumber() > startRideNumber) {
            searchInRangeRecursive(rootNode.getLeftNode(), startRideNumber, endRideNumber, rbtNode);
        }

        if (rootNode.getRideNumber() >= startRideNumber && rootNode.getRideNumber() <= endRideNumber) {
            rbtNode.add(rootNode);
        }

        if (rootNode.getRideNumber() < endRideNumber) {
            searchInRangeRecursive(rootNode.getRightNode(), startRideNumber, endRideNumber, rbtNode);
        }
    }

    // It searches and returns a node with ride number which is equal to the given ride number.
       public RedBlackNode searchNode(int rideNumber) {
        return search(rootNode, rideNumber);
    }

    private RedBlackNode search(RedBlackNode rootNode, int rideNumber) {
        if (rootNode == extNode)
            return null;
        if (rootNode.getRideNumber() == rideNumber) {
            return rootNode;
        } else if (rootNode.getRideNumber() > rideNumber) {
            return search(rootNode.getLeftNode(), rideNumber);
        } else {
            return search(rootNode.getRightNode(), rideNumber);
        }
    }

}