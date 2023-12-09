package lepers;

import java.util.Random;

/**
 * The Customer class represents a customer in a java market system.
 * Each customer has a unique customer number,
 * An arrival time, a service time, a finish time, a wait time,
 * a service start time, and a lane number
 * @author Andrew, Tommy, Josh, Calvin
 *
 */

public class Customer implements Comparable<Customer> {
	
	private String fullOrSelf;
	
	/**
	 * Unique customer number/ID
	 */
	private int custNum;
	/**
	 * Keeps track of customer number.
	 */
	private static int customerNumber = 0;
	/**
	 * Arrival time of the customer.
	 */
	private int arrivalTime;
	/**
	 * How long it takes to service the customer.
	 */
	private int serviceTime;
	/**
	 * Total amount of time it takes for  customer to finish
	 * Service+Arrival+Wait time
	 */
	private int finishTime;
	/**
	 * How long the customer waited in line
	 */
	private int waitTime;
	/**
	 * Whether the customer wants to use the full-service checkout lanes
	 */
	private boolean fullService;
	
	/**
	 * What time the customer started getting served
	 */
	private int serviceStartTime;
	/**
	 * Lane name of where the customer was served
	 */
	private String lane;
	
	/**
	 * Empty constructor
	 */
	public Customer() {}
	/**
	 * Creates a new Customer object with the arrival time and service time
	 * Every time this is called it increases custNum to give to new customer.
	 * @param a The arrival time of the customer
	 * @param s The Service time of the customer
	 */
	public Customer(int a, int s) {
		this.arrivalTime = a;
		this.serviceTime = s;
		customerNumber++;
		this.custNum = customerNumber;
	}
	
	/**
	 * Prints custnum, arrivalTime, lane, waitTime, serviceTime
	 * @return a string that prints out the Customer object.
	 */
	public String toString() {
		String s = "Customer number " + custNum + " Arrival time: " + arrivalTime + " Entered Service Lane: " + lane + " at " + serviceStartTime + "   |   Service time: " + serviceTime + " Finish time: " + finishTime +
				" Wait time: " + waitTime;
		return s;
	}
	public void slowDown(double percent) {
		
		serviceTime = (int)Math.round((serviceTime*(1+percent)));
		
	}
	
	public int compareTo(Customer c){
		if(this.custNum < c.custNum)
			return -1;
		else if(this.custNum > c.custNum)
			return 1;
		else
			return 0;
	}

	/**
	 * Gets the arrival time of the  customer
	 * @return the arrival time of the customer
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * Sets the arrival time of the customer 
	 * @param arrivalTime desired arrivalTime
 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Gets the service time of the customer
	 * @return how long it takes the customer to get served
	 */
	public int getServiceTime() {
		return serviceTime;
	}

	/**
	 * Sets the service time of the customer
	 * @param serviceTime desired serviceTime
	 */
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * Returns the total time it takes to finish.
	 * @return the total time customer is finished
	 */
	public int getFinishTime() {
		return finishTime;
	}

	/**
	 * Sets the time when customer is finished
	 * @param finishTime desired finishTime
	 */
	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * gets waitTime of the customer
	 * @return wait time of the customer
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the waitTime of the customer
	 * @param waitTime desired waitTime for customer
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	/**
	 * Grabs the lane name
	 * @return lane name
	 */
	public String getLane() {
		return lane;
	}

	/**
	 * Changes the lane name
	 * @param lane new lane name
	 */
	public void setLane(String lane, int time) {
		this.lane = lane;
		System.out.println("Customer " + custNum + " enters lane " + lane + " at " + time);
	}

	/**
	 * gets the service start time of the customer
	 * @return serviceStartTime of customer
	 */
	public int getServiceStartTime() {
		return serviceStartTime;
	}

	/**
	 * changes the serviceSTartTime
	 * @param serviceStartTime desired startTime 
	 */
	public void setServiceStartTime(int serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	/**
	 * gets the customer's ID/number
	 * @return the customer's ID/number
	 */
	public int getCustNum() {
		return custNum;
	}

	/**
	 * changes the customer's ID/number
	 * @param custNum desired customer ID/number
	 */
	public void setCustNum(int custNum) {
		this.custNum = custNum;
	}
	public static int getCustomerNumber() {
		return customerNumber;
	}
	public static void setCustomerNumber(int customerNumber) {
		Customer.customerNumber = customerNumber;
	}
	public boolean isFullService() {
		return fullService;
	}
	public void setFullService(boolean fullService) {
		this.fullService = fullService;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public String getFullorSelf() {
		return fullOrSelf;
	}
	public void setFullorSelf(String fullorSelf) {
		this.fullOrSelf = fullorSelf;
	}
	
	public void flipCoinForServiceType(double percent) {
		Random rand = new Random();
        int flip = rand.nextInt(2);
        
        if(flip == 0)
        	this.fullOrSelf = "f";
        else {
        	this.fullOrSelf = "s";
        	slowDown(percent);
        }
	}
	
}

