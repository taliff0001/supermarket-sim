package lepers;

import java.util.Random;

/**
 * The CustomerCreator class creates the Customer with random arrival and service time.
 * @author Andrew, Tommy, Josh, Calvin
 *
 */
public class CustomerCreator {
	/**
	 * Earliest a customer is allowed to arrive.
	 */
	static int minArrivalTime;
	/**
	 * Maximum time allowed for a customer to arrive.
	 */
	static int maxArrivalTime;
	/**
	 * Minimum Time it takes for customer to get serviced.
	 */
	static int minServiceTime;
	/**
	 * Max Time it takes for customer to get serviced.
	 */
	static int maxServiceTime;
	/**
	 * previous arrival time
	 */
	static int previousArrivalTime;
	
	static int numCust;
	
	static boolean firstCust;
	
	/**
	 * Empty constructor
	 */
	public CustomerCreator() {
		
	}
	/**
	 * Constructor CustomerCreator
	 * @param minAT the minimum arrival time for a customer
	 * @param maxAT the maximum arrival time for a customer
	 * @param minST the minimum service time required by a customer
	 * @param maxST the maximum service time required by a customer
	 * @param n the number of customers for the simulation
	 * @param firstCust lets the .next() method know to start off at time zero
	 */
	public CustomerCreator(int minAT, int maxAT, int minST, int maxST,int n) {
		minArrivalTime = minAT;
		maxArrivalTime = maxAT;
		minServiceTime = minST;
		maxServiceTime = maxST;
		numCust = n;
		previousArrivalTime = 0;
		firstCust=true;
	}
	
	/**
	 * Generates a random arrival and service time for a customer.
	 * @return Customer object with random service/arrival time.
	 */
	
		public static Customer next() {
			Random rand = new Random();
			
	        int arrivalTime = rand.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
	        int serviceTime = rand.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
			arrivalTime += previousArrivalTime;
			previousArrivalTime = arrivalTime;
			Customer cust = new Customer(arrivalTime, serviceTime);
			if(firstCust==true) {
				cust.setArrivalTime(0);
				previousArrivalTime=0;
				firstCust=false;
			}
			return cust;
			
		}
	
	/**
	 * @return the number of customers to be serviced
	 */
	
	public static int getNumCust() {
		return numCust;
	}
}
