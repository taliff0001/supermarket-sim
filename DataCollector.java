package lepers;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;

/**
 * The DataCollector class handles the collection and recording of data for the
 * Java Market program
 */

public class DataCollector {

	/**
	 * stores the string representation of the data which will be used to generate
	 * an HTML file that stores data from the simulation
	 */

	private static StringBuffer HTMLString;

	/**
	 * keeps track of the total wait time of the customers
	 */

	private static int waitTimeFull;

	private static int waitTimeSelf;

	/**
	 * the total number of customers in the simulation
	 */

	private static int numCustFull;

	private static int numCustSelf;

	/**
	 * An ArrayList used to sort the customers by number for the HTML file
	 */

	private static ArrayList<Customer> alc;

	/**
	 * the number of customers with a wait time under 5 minutes
	 */

	private static int satisfiedFull;

	private static int satisfiedSelf;

	private int[] downtimeFull;

	private int[] downtimeSelf;
	
	private static String parameters;
	
	private static double percentSlower;
	
	private static int numLanesFull;
	
	private static int numLanesSelf;

	private static int numCustTotal;

	
	
	/**
	 * no argument constructor that initializes the Customer ArrayList
	 */

	public static void addParameters(int minA, int maxA, int minS, int maxS) {
		parameters = "<table id=\"new-data\""
				+ "<tr><th>Min Arrival</th><th>Max Arrival</th><th>Min Service</th><th>Max Service</th><th>Customers</th><th>Reg Lanes</th><th>Self Lanes</th>"
				+ "</tr><tr><td>"+ minA +"</td><td>"+ maxA +"</td><td>"+ minS +"</td><td>"+ maxS +"</td><td>"+ CustomerCreator.getNumCust() +"</td><td>"+ numLanesFull +"</td><td>"+ numLanesSelf +"</td></tr></table>";	
	}
	
	public DataCollector() {
		alc = new ArrayList<>();
	}

	/**
	 * Records data that is stored in the customer objects
	 * 
	 * @param c the customer whose data is being recorded
	 */

	public static void addCustomer(Customer c) {
		recordWaitTime(c);
		alc.add(c);

	}

	public void addDowntime(int[] f, int[] s) {
		downtimeFull = f;
		downtimeSelf = s;
	}

	/**
	 * Puts customer data in html format
	 * 
	 * @param c the customer whose data is being recorded
	 */

	public static void logCustomer(Customer c) {

		String tableRow;
		
		if (c.getLane().startsWith("S")) {
			
		tableRow = "<tr class=\"selfCheckout\"><td>" + c.getCustNum() + "</td><td>" + c.getLane() + "</td><td>" + c.getArrivalTime()
				+ "</td><td>" + c.getServiceTime() + "</td><td>" + c.getFinishTime() + "</td><td>" + c.getWaitTime()
				+ "</td></tr>";
		}
		else
			tableRow = "<tr><td>" + c.getCustNum() + "</td><td>" + c.getLane() + "</td><td>" + c.getArrivalTime()
				+ "</td><td>" + c.getServiceTime() + "</td><td>" + c.getFinishTime() + "</td><td>" + c.getWaitTime()
				+ "</td></tr>";
		
		HTMLString.append(tableRow);
	}

	/**
	 * Generates the final version of the HTML documents to be saved
	 */

	public void saveTable(Lanes l, SelfService s, String saveHTML) {

		Collections.sort(alc);

		String CSS = "<style>\r\n"
				+ "*{font-family: Arial, Helvetica, sans-serif;}"
				+ "  /* Old table style */\r\n"
				+ "  #old-data {\r\n"
				+ "    border-collapse: collapse;\r\n"
				+ "    width: 100%;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #old-data td, #old-data th {\r\n"
				+ "    padding: 8px;\r\n"
				+ "    border: 1px solid black;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #old-data tr {\r\n"
				+ "    background-color: seashell;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #old-data th {\r\n"
				+ "    padding-top: 12px;\r\n"
				+ "    padding-bottom: 12px;\r\n"
				+ "    text-align: left;\r\n"
				+ "    background-color: steelblue;\r\n"
				+ "    color: white;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #old-data tr.selfCheckout td {\r\n"
				+ "    background-color: #ddd;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  /* New table style */\r\n"
				+ "  #new-data {\r\n"
				+ "    border-collapse: collapse;\r\n"
				+ "    width: 100%;\r\n"
				+ "    border: 2px solid #ccc;\r\n"
				+ "    margin-top: 20px;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #new-data td, #new-data th {\r\n"
				+ "    padding: 8px;\r\n"
				+ "    border: 1px solid #ccc;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #new-data tr:first-child {\r\n"
				+ "    background-color: #f2f2f2;\r\n"
				+ "    border-top: 2px solid #ccc;\r\n"
				+ "    border-bottom: 2px solid #ccc;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #new-data th {\r\n"
				+ "    padding-top: 12px;\r\n"
				+ "    padding-bottom: 12px;\r\n"
				+ "    text-align: left;\r\n"
				+ "    background-color: #A9A9A9;\r\n"
				+ "    color: white;\r\n"
				+ "    border-right: 1px solid #ccc;\r\n"
				+ "  }\r\n"
				+ "  \r\n"
				+ "  #new-data tr.selfCheckout td {\r\n"
				+ "    background-color: #ddd;\r\n"
				+ "  }\r\n"
				+ "</style>\r\n"
				+ "";
		
		
		HTMLString = new StringBuffer("<html><head>" + CSS + "<title>Leopards Data</title></head><body><h1>Leopards Data" + "</h1>" + parameters
				+ "<br><table  id=\"old-data\"><tr><td>Customer ID</td><td>Service Lane</td><td>Arrival Time</td><td>Service Time</td>"
				+ "<td>Finish Time</td><td>Wait Time</td></tr>");

		for (Customer c : alc)
			logCustomer(c);

		DecimalFormat df = new DecimalFormat("#.##");

		HTMLString.append("</table></body>");
		DatabaseInfo.createConnection();
		HTMLString.append("<h4>Full Service Data: </h4><p>Time unoccupied:  ");
		System.out.println("\n-= FULL SERVICE DATA =-");
		System.out.print("Time unoccupied: ");
		downtimeFull = l.getDowntime();
		for (int i = 0; i < downtimeFull.length; ++i) {
			HTMLString.append("Lane " + i + ": " + downtimeFull[i] + " minutes  |  ");
			System.out.print("Lane " + i + ": " + downtimeFull[i] + " minutes  |  ");
			DatabaseInfo.fullServeTable(i, downtimeFull[i]);
		}
		HTMLString.append("<p>Average wait time was " + df.format(avgWaitTimeFull())
				+ " minutes. Total satisfied customers: " + satisfiedFull + " (wait time < 5)" + "  |  Unsatisfied: "
				+ (numCustFull - satisfiedFull) + " (>= 5)</h4></html>");
		System.out.print("\nAverage wait time was " + df.format(avgWaitTimeFull())
				+ " minutes. Total satisfied customers: " + satisfiedFull + " (wait time < 5)" + "  |  Unsatisfied: "
				+ (numCustFull - satisfiedFull) + " (>= 5)");
		DatabaseInfo.closeConnection();
		
		downtimeSelf = s.getDowntime();
		HTMLString.append("<h4>Self Service Data: </h4><p>Time unoccupied:  ");

		DatabaseInfo.createConnection();
		System.out.println("\n\r-= SELF SERVICE DATA =-");
		System.out.print("Time unoccupied: ");
		//downtimeFull = l.getDowntime();		
		for (int i = 0; i < downtimeSelf.length; ++i) {
			HTMLString.append("Lane " + i + ": " + downtimeSelf[i] + " minutes  |  ");
			System.out.print("Lane " + i + ": " + downtimeSelf[i] + " minutes  |  ");
			DatabaseInfo.selfServeTable(i, downtimeSelf[i]);
		}
		
		HTMLString.append("<p>Average wait time was " + df.format(avgWaitTimeSelf())
				+ " minutes. Total satisfied customers: " + satisfiedSelf + " (wait time < 5)" + "  |  Unsatisfied: "
				+ (numCustSelf - satisfiedSelf) + " (>= 5)</h4></html>");
		System.out.print("\nAverage wait time was " + df.format(avgWaitTimeSelf())
		+ " minutes. Total satisfied customers: " + satisfiedSelf + " (wait time < 5)" + "  |  Unsatisfied: "
		+ (numCustSelf - satisfiedSelf) + " (>= 5)\r");
		DatabaseInfo.closeConnection();
		
		SuggestionBox.setAvgWaitFull(avgWaitTimeFull());
		SuggestionBox.setAvgWaitSelf(avgWaitTimeSelf());
		SuggestionBox.calcPercentUnoccupied(downtimeFull, downtimeSelf);
		
		SuggestionBox sb = new SuggestionBox();
		System.out.println(sb);
		
		String html;
		if(saveHTML.equalsIgnoreCase("y")) {
		html = new String(HTMLString);
		save(html);
		}
	}

	/**
	 * Gets the save location and saves the HTML file to disk
	 * 
	 * @param html the final representation of the data that is saved to disk
	 */

	public void save(String html) {

		System.out.println("\n\r-------- REMEMBER TO SAVE YOUR FILE WITH A .HTML EXTENSION --------\n"
				+ "---- AND THAT THE DIALOGUE BOX MAY APPEAR BEHIND OTHER WINDOWS ----");

		File HTMLTable = new File(InOut.getWriteLocation());
		PrintWriter p = null;

		try {
			p = new PrintWriter(HTMLTable);
			p.print(html);
		}

		catch (Exception e) {
			System.err.print("Something went wrong! See ya later!!");
			System.exit(0);
		}

		finally {
			p.close();
		}
	}

	/**
	 * Stores the sum of the customers' wait times
	 * 
	 * @param c the current customer
	 */

	public static void recordWaitTime(Customer c) {
		if (c.getFullorSelf().equals("f")) {
			if (c.getWaitTime() < 5)
				++satisfiedFull;

			waitTimeFull += c.getWaitTime();
			numCustFull++;
		}
		else {
			if (c.getWaitTime() < 5)
				++satisfiedSelf;

			waitTimeSelf += c.getWaitTime();
			numCustSelf++;
		}
			
	}

	/**
	 * Calculates average customer wait time
	 */

	public static double avgWaitTimeFull() {
		
		return waitTimeFull / (double) numCustFull;

	}
	public static double avgWaitTimeSelf() {
		
		return waitTimeSelf / (double) numCustSelf;

	}
	
	public static void saveToDatabase() {
		Collections.sort(alc);
		Connection conn = DatabaseInfo.createConnection();
		int runID = DatabaseInfo.queryRunID(conn);
		for (int i = 0; i<alc.size();i++) {
			DatabaseInfo.addInfo(runID, alc.get(i).getCustNum(), alc.get(i).getLane(), alc.get(i).getArrivalTime(), alc.get(i).getServiceTime(),
					alc.get(i).getFinishTime(),alc.get(i).getWaitTime());
		}
		
		DatabaseInfo.closeConnection();
	}
	public static double getPercentSlower() {
		return percentSlower;
	}

	public static void setPercentSlower(double percentSlower) {
		DataCollector.percentSlower = percentSlower;
	}

	public static void setNumLanes(int fullServ, int selfServ) {
		numLanesFull = fullServ;
		numLanesSelf = selfServ;
		
	}

	public static int getNumCustTotal() {
		return numCustTotal;
	}

	public static void setNumCustTotal(int numCustTotal) {
		DataCollector.numCustTotal = numCustTotal;
	}
	
	public static int getSatisfiedFull() {
		return satisfiedFull;
	}

	public static int getSatisfiedSelf() {
		return satisfiedSelf;
	}

	public static int getNumCustFull() {
		return numCustFull;
	}

	public static void setNumCustFull(int numCustFull) {
		DataCollector.numCustFull = numCustFull;
	}

	public static int getNumCustSelf() {
		return numCustSelf;
	}

	public static void setNumCustSelf(int numCustSelf) {
		DataCollector.numCustSelf = numCustSelf;
	}
	
	

	

}