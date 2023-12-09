package lepers;

import java.util.NoSuchElementException;


/**
 * The CustomerList class creates the linked list that will be used to store data for each queue in the simulator.
 * @author Andrew, Tommy, Josh, Calvin
 *
 */



public class CustomerList {
	/**
	 * the list head element in list
	 */
    private Node first;  // list head
    /**
	 * the list tail element in list
	 */
    private Node last;   // last element in list
    /**
     * The Node class creates a node in the linked list that allows the manipulation of its data.
     *
     */
    private class Node {
        Customer value;
        
        Node next;
        /**
    	 * a node constructor
    	 * @param val the value storing customer information
    	 * @param n the next node in the list
    	 */
        Node(Customer val, Node n) {
            value = val;
            next = n;
        }
        
        /**
    	 * a node constructor with just the customer
    	 * @param val the value storing customer information
    	 * 
    	 */
        Node(Customer val) {
            this(val, null);
        }
    }
    
    /**
     * Customer List constructor to set the first and last elements
     *
     * 
     */
    public CustomerList() {
        first = null;
        last = null;
    }
    /**
     * determines if the list is empty
     * @return nothing in first node.
     */
    public boolean isEmpty() {
        return first == null;
    }
    /**
     * determines the size of the list
     * @return count of how many nodes are in the list.
     */
    public int size() {
        int count = 0;
        Node p = first;
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }
    /**
     * adds a customer to linked list
     * @param e a new node object based off data found from customer information
     */
    public void add(Customer e) {
        if (isEmpty()) {
            first = new Node(e);
            last = first;
        } else {
            last.next = new Node(e);
            last = last.next;
        }
    }
    /**
     * adds a customer to the first location in the list
     * @param e a new node object based off data found from customer information
     */
    public void addFirst(Customer e) {
        if (isEmpty()) {
            first = new Node(e);
            last = first;
        } else {
            first = new Node(e, first);
        }
    }
    /**
     * removes the last object of the list.
     * @return value of the node previous to the node removed
     */
    public Customer removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Customer value;
        if (first == last) {
            value = first.value;
            first = null;
            last = null;
        } else {
            Node p = first;
            while (p.next != last) {
                p = p.next;
            }
            value = last.value;
            last = p;
            last.next = null;
        }
        return value;
    }
    /**
     * a method to return the last value of the queue
     * @return last value found in the queue 
     */
    public Customer frontPeek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return last.value;
    }
    /**
     * a method to return the first value of the queue
     * @return first value found in the queue 
     */
    public Customer rearPeek() {
        if (isEmpty()) {
            //throw new NoSuchElementException();
        }
        return first.value;
    }
    /**
     * a method to display the information of the value found in the node of the linked list
     * @return a stringbuilder object to display the information of the node
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node p = first;
        while (p != null) {
            sb.append(p.value);
            if (p != last) {
                sb.append(", ");
            }
            p = p.next;
        }
        return "[" + sb.toString() + "]";
    }
}