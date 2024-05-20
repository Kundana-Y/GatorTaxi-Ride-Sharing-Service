// This class is the driver class
import java.util.Scanner;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;

public class gatorTaxi {

    public static final int MAX_HEAP_SIZE = 100;
    public static final int PRINT = 1;
    public static final int PRINT_RANGE = 2;
    public static final int INSERT = 3;
    public static final int GET_NEXT_RIDE = 4;
    public static final int CANCEL_RIDE = 5;
    public static final int UPDATE_TRIP = 6;

    private static final String emptyNodeTriplet = "(0,0,0)";

    public static final String OUTPUT_FILE = "output_file.txt";

    public static void main(String[] args) {
        List<RideParameters> parameters = new ArrayList<>();

        try {
            if (args.length == 0)
                throw new IllegalArgumentException();

            // Construct a path and read file from the command line args
            File file = new File(Paths.get(args[0]).toString());
            Scanner scan = new Scanner(file);

            // Set console out to the required output file. The output is in form of a
            // triplet
            PrintStream out = new PrintStream(new FileOutputStream(OUTPUT_FILE));
            System.setOut(out);

            while (scan.hasNext()) {
                String input = scan.nextLine();
                RideParameters RideParameters = new RideParameters();
                // Extracts the input using regex expression
                String[] exp1 = input.split("\\(", 2);
                String[] exp2 = exp1[1].split("\\)", 2);
                String inputCommand = exp1[0].trim();
                String[] exp3 = exp2[0].split(",");
                switch (inputCommand) {
                    case "Print":
                        if (exp3.length == 1) {
                            // The type is set to Print
                            RideParameters.setType(PRINT);
                            RideParameters.setFirstParameter(Integer.parseInt(exp3[0]));
                        } else if (exp3.length == 2) {
                            // The type is set to Print_Range
                            RideParameters.setType(PRINT_RANGE);
                            RideParameters.setFirstParameter(Integer.parseInt(exp3[0]));
                            RideParameters.setSecondParameter(Integer.parseInt(exp3[1]));
                        }
                        break;
                    case "Insert":
                        // The type is set to Insert
                        RideParameters.setType(INSERT);
                        RideParameters.setFirstParameter(Integer.parseInt(exp3[0]));
                        RideParameters.setSecondParameter(Integer.parseInt(exp3[1]));
                        RideParameters.setThirdParameter(Integer.parseInt(exp3[2]));
                        break;
                    case "GetNextRide":
                        // The type is set to Get_Next_Ride
                        RideParameters.setType(GET_NEXT_RIDE);
                        break;
                    case "CancelRide":
                        // The type is set to Cancel
                        RideParameters.setType(CANCEL_RIDE);
                        RideParameters.setFirstParameter(Integer.parseInt(exp3[0]));
                        break;
                    case "UpdateTrip":
                        // The type is set to Update_Trip
                        RideParameters.setType(UPDATE_TRIP);
                        RideParameters.setFirstParameter(Integer.parseInt(exp3[0]));
                        RideParameters.setSecondParameter(Integer.parseInt(exp3[1]));
                        break;
                    default:
                        break;
                }
                // The parameters are stored in a list
                parameters.add(RideParameters);
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // initialize min heap with the max heap sizze and initialize red black tree
        MinHeap minHeap = new MinHeap(MAX_HEAP_SIZE);
        RedBlackTree redBlackTree = new RedBlackTree();
        ListIterator<RideParameters> iter = parameters.listIterator();

        RideParameters RideParameters = null;

        // while loop is executed until parameters are finished and the heap is empty
        while (iter.hasNext()) {
            RideParameters = iter.next();
            switch (RideParameters.getType()) {
                /*
                 * It searches for a particular node with the given ride number. It provides
                 * the ride number, ride cost and trip duration if the node exists, else it
                 * gives an empty triplet.
                 */
                case PRINT:
                    RedBlackNode findNode = redBlackTree.searchNode(RideParameters.getFirstParameter());
                    if (findNode == null) {
                        System.out.println(emptyNodeTriplet);
                    } else {
                        System.out.println("(" + findNode.getRideNumber() + "," + findNode.getRideCost() + ","
                                + findNode.getTripDuration() + ")");
                    }
                    break;

                /*
                 * It searches for a range of ride numbers. It provides the ride numbers, ride
                 * costs and trip durations if the nodes exists, else it gives an empty triplet.
                 */
                case PRINT_RANGE:
                    List<RedBlackNode> rbtNode = redBlackTree.searchInRange(RideParameters.getFirstParameter(),
                            RideParameters.getSecondParameter());
                    int counter = 0;
                    if (rbtNode.size() > 0) {
                        for (RedBlackNode node : rbtNode) {
                            counter++;
                            if (counter == rbtNode.size()) {
                                if (node != null) {

                                    System.out.print("(" + node.getRideNumber() + "," + node.getRideCost() + ","
                                            + node.getTripDuration() + ")");
                                } else {
                                    System.out.print(emptyNodeTriplet);
                                }
                            } else {
                                if (node != null) {

                                    System.out.print("(" + node.getRideNumber() + "," + node.getRideCost() + ","
                                            + node.getTripDuration() + "),");
                                } else {
                                    System.out.print(emptyNodeTriplet + ",");
                                }
                            }

                        }
                        System.out.println();
                    } else {
                        System.out.print(emptyNodeTriplet + "\n");
                    }
                    break;

                /*
                 * It inserts a new node into a Red-Black tree and a corresponding heapNode into
                 * a min-heap. It returns a messagge if the same ride number is inserted.
                 */
                case INSERT:
                    if (redBlackTree.searchNode(RideParameters.getFirstParameter()) == null) {

                        RedBlackNode redBlackNode = new RedBlackNode(RideParameters.getFirstParameter(),
                                RideParameters.getSecondParameter(), RideParameters.getThirdParameter());
                        HeapNode heapNode1 = new HeapNode(RideParameters.getFirstParameter(),
                                RideParameters.getSecondParameter(),
                                RideParameters.getThirdParameter(), redBlackNode);
                        redBlackNode.setHeapNodeReference(heapNode1);
                        redBlackTree.insert(redBlackNode);
                        minHeap.insert(heapNode1);
                    } else {
                        System.out.println("Duplicate RideNumber");
                        System.exit(0);
                    }
                    break;

                /*
                 * It extracts the minimum node from minHeap and deletes its corresponding red
                 * black node from red black tree. If the HeapNode is empty, it returns a
                 * message else it prints the ride number, ride cost, and trip duration of the
                 * HeapNode object.
                 */
                case GET_NEXT_RIDE:
                    HeapNode minNode = minHeap.extractMin();
                    RedBlackNode deleteNode;
                    // It handles when minNode does not exist
                    if (minNode == null) {
                        System.out.println("No active ride requests");
                    } else {
                        System.out.println("(" + minNode.getRideNumber() + "," + minNode.getRideCost() + ","
                                + minNode.getTripDuration() + ")");
                        deleteNode = minNode.getRbtReference();
                        redBlackTree.delete(deleteNode);
                    }
                    break;

                /*
                 * It searches for the particular node in the red black tree and deletes it. The
                 * corresponding HeapNode object is deleted from the min heap.
                 */
                case CANCEL_RIDE:
                    RedBlackNode cancelNode = redBlackTree.searchNode(RideParameters.getFirstParameter());
                    // It handles when cancelNode does not exist
                    if (cancelNode != null) {
                        HeapNode heapNode2 = cancelNode.getHeapNodeReference();
                        redBlackTree.delete(cancelNode);
                         minHeap.deleteElement(heapNode2.getRideNumber());
            
                    }
                    break;

                /*
                 * It updates the ride parameters according to the three conditions specified
                 * below
                 */
                case UPDATE_TRIP:
                    RedBlackNode updateRbNode = redBlackTree.searchNode(RideParameters.getFirstParameter());
                    if (updateRbNode != null) {
                        int exisitingTripDuration = updateRbNode.getTripDuration();
                        int newTripDuration = RideParameters.getSecondParameter();

                        /*
                         * If the new trip duration is less than or equal to the existing trip
                         * duration,it updates the corresponding heap node and red-black node with the
                         * new trip duration.
                         */
                        if (newTripDuration <= exisitingTripDuration) {

                            HeapNode updateHeapNode1 = updateRbNode.getHeapNodeReference();
                            updateRbNode.setTripDuration(newTripDuration);
                            updateHeapNode1.setTripDuration(newTripDuration);

                            /*
                             * If the new trip duration is greater than twice the existing trip duration, it
                             * deletes the corresponding nodes from the min heap and red black tree.
                             */
                        } else if (newTripDuration > 2 * exisitingTripDuration) {

                            HeapNode updateHeapNode2 = updateRbNode.getHeapNodeReference();
                            minHeap.deleteElement(updateHeapNode2.getRideNumber());
                            redBlackTree.delete(updateRbNode);
                            
                            /*
                             * If the new trip duration is between the existing trip duration and
                             * twice the existing trip duration, it increases the ride cost by 10 and
                             * deletes the corresponding nodes. It creates a new red black node with the
                             * updated ride cost and trip duration, and it inserts it into both the min heap
                             * and red black tree.
                             */
                        } else if (exisitingTripDuration < newTripDuration
                                && newTripDuration <= (2 * exisitingTripDuration)) {
                            int newRideCost = updateRbNode.getRideCost() + 10;

                            HeapNode updateHeapNode3 = updateRbNode.getHeapNodeReference();
                            minHeap.deleteElement(updateHeapNode3.getRideNumber());

                            redBlackTree.delete(updateRbNode);
                           
                            RedBlackNode rbNode = new RedBlackNode(updateRbNode.getRideNumber(), newRideCost,
                                    newTripDuration);
                            HeapNode heapNode3 = new HeapNode(updateRbNode.getRideNumber(), newRideCost,
                                    newTripDuration, rbNode);
                            rbNode.setHeapNodeReference(heapNode3);
                            redBlackTree.insert(rbNode);
                            minHeap.insert(heapNode3);

                        }
                    }
                    break;
                default:
                    break;
            }

        }

    }

}