//This claas extends Ride class
public class RedBlackNode extends Ride {

    // Enumeration for red black tree node
    public enum NodeColor {
        RED, BLACK
    };

    // It defines a final variable EXT_NODE. It is the null external black node
    public static final RedBlackNode EXT_NODE = new RedBlackNode(-1, -1, -1);

    // It defines leftNode, rightNode and parentNode pointers
    private RedBlackNode leftNode, rightNode, parentNode;

    // It defines nodeColor of the node. It is either red or black
    private NodeColor nodeColor;

    private HeapNode heapNodeReference;

    // Parameterized constructor of RedBlackNode class
    public RedBlackNode(int rideNumber, int rideCost, int tripDuration) {
        super(rideNumber, rideCost, tripDuration);
        this.setLeftNode(EXT_NODE);
        this.setRightNode(EXT_NODE);
        this.setParentNode(EXT_NODE);

        // This sets red color to all nodes except EXT_NODE node
        this.setColor(rideNumber == -1 ? NodeColor.BLACK : NodeColor.RED);
    }

    // This method returns the color of a node
    public NodeColor getColor() {
        return nodeColor;
    }

    // This method sets the color of a node to the nodeColor value
    public void setColor(NodeColor nodeColor) {
        this.nodeColor = nodeColor;
    }

    // This method returns a reference to the HeapNode object
    public HeapNode getHeapNodeReference() {
        return heapNodeReference;
    }

    // This method sets the reference to the HeapNode object
    public void setHeapNodeReference(HeapNode heapNodeReference) {
        this.heapNodeReference = heapNodeReference;
    }

    // This method returns the left node of the RedBlackNode object
    public RedBlackNode getLeftNode() {
        return leftNode;
    }

    // This method sets the left child node to the RedBlackNode object
    public void setLeftNode(RedBlackNode leftNode) {
        this.leftNode = leftNode;
    }

    // This method returns the right node of the RedBlackNode object
    public RedBlackNode getRightNode() {
        return rightNode;
    }

    // This method sets the right child node to the RedBlackNode object
    public void setRightNode(RedBlackNode rightNode) {
        this.rightNode = rightNode;
    }

    // This method returns the parent node of the RedBlackNode object
    public RedBlackNode getParentNode() {
        return parentNode;
    }

    // This method sets the parent child node to the RedBlackNode object
    public void setParentNode(RedBlackNode parentNode) {
        this.parentNode = parentNode;
    }

}
