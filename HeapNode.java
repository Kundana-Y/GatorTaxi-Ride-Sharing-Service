//This class extends Ride class
public class HeapNode extends Ride {
    // pointer to the corresponding red black rbNodeReference
    private RedBlackNode rbtNodeReference;

    // Parameterized constructor of HeadNode class
    public HeapNode(int rideNumber, int rideCost, int tripDuration, RedBlackNode rbNode) {
        super(rideNumber, rideCost, tripDuration );
        this.setRbtReference(rbNode);
    }

    // This fetches the red black node reference
    public RedBlackNode getRbtReference() {
        return rbtNodeReference;
    }

    // This method sets the reference of red black tree node to rbtNodeReference
    private void setRbtReference(RedBlackNode rbNode) {
        this.rbtNodeReference = rbNode;
    }
}