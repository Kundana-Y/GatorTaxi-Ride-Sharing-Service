// This class defines the min heap data structure
import java.util.*;

public class MinHeap {

    // Max heapSize for the heap array
    private int heapSize;
    private HeapNode[] heap;
    private int size = 0;
    private Map<Integer, Integer> rideToIndex = new HashMap<Integer, Integer>();

    // Parameterized constructor of MinHeap class
    public MinHeap(int heapSize) {
        this.heapSize = heapSize;
        createHeap(heapSize);
    }

    // This method initializes heap array with the given heapSize.
    private void createHeap(int heapSize) {
        this.heap = new HeapNode[heapSize + 1];
    }

    // This method returns true if the heap is empty else it returns false.
    public boolean isEmpty() {
        return size == 0;
    }

    // This method swaps two items in the heap array.
    private void swapElement(int i, int j) {
        rideToIndex.put(heap[i].getRideNumber(), j);
        rideToIndex.put(heap[j].getRideNumber(), i);
        HeapNode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /*
     * This method inserts heapNode into heap array. It throws an exception if the
     * size of heap is exceeded.
     */
    public void insert(HeapNode heapNode) {
        if (size > heapSize) {
            throw new IllegalStateException("The heap size exceeded. Cannot insert more than " + heapSize + " items.");
        }

        heap[size] = heapNode;
        size++;
        rideToIndex.put(heapNode.getRideNumber(),size-1);
        heapifyUp(size-1);
    }

    /*
     * This method checks and rearranges the heap array as per the heap property
     * whenever a new node is inserted.
     */
    private void heapifyUp(int position) {
        int index = (position - 1)/2;
        if(index >= 0 && (heap[index].getRideCost() > heap[position].getRideCost() )|| (heap[index].getRideCost() == heap[position].getRideCost() &&
         heap[index].getTripDuration() > heap[position].getTripDuration())){
            swapElement(position, index);
            heapifyUp(index);
         }
    }


    /*
     * This method deletes the element with the given key from the min heap by
     * decreasing its key and then extracting the minimum element from the heap.
     */
    public void deleteElement(int key) {

        if(size==0){
            return;
           }
           if(!rideToIndex.containsKey(key)){
           return;
           }
           int index = rideToIndex.get(key);
           heap[index]=heap[size-1];
           rideToIndex.put(heap[index].getRideNumber(),index);
           rideToIndex.remove(key);
           heap[size-1]=null;
           size--;
           
           heapifyDown(0);
    }


    // This method removes and returns the minimum element from the heap.
    public HeapNode extractMin() {
        if(size==0) {
            return null;
        }
        HeapNode item = heap[0];
        // Replace the root with the last heap item, and then heapify it down.
        int key = heap[0].getRideNumber();
        heap[0] = heap[size - 1];
        rideToIndex.put(heap[0].getRideNumber(),0);
        rideToIndex.remove(key);
        heapifyDown(0);
        heap[size - 1] = null;
        size--;
        return item;
    }

    /*
     * This method recursively performs heapify operation by swapping the current
     * element with its smaller child node until the heap property is maintained.
     */
    private void heapifyDown(int position) {
        // It breaks the recursion when the element is a leaf node.
        if (position == size-1)
            return;

        int left = 2*position +1;
        int right = 2*position+2;

        /*
         * It checks which child has a smaller value. The tie is broken by comparing
         * shortest trip duration if ride cost is same.
         */
            if((left<size) && ((heap[position].getRideCost() > heap[left].getRideCost() ) || (heap[position].getRideCost() == heap[left].getRideCost() && heap[position].getTripDuration() > heap[left].getTripDuration())))
        {
            swapElement(position, left);
            heapifyDown(left);
        }
        if((right<size)&&((heap[position].getRideCost() > heap[right].getRideCost()) || (heap[position].getRideCost() == heap[right].getRideCost() && heap[position].getTripDuration() > heap[right].getTripDuration())))
        {
            swapElement(position, right);
            heapifyDown(right);
        }
        }
    }
