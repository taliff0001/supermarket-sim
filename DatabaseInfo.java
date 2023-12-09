package lepers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseInfo {
	static int runID = 1;
	static Connection conn = null;
	static Statement stmt = null;
	static CallableStatement callStmt = null;
	
	//creates connection between eclipse and php
	public static Connection createConnection() {
		
		String user = "DBUser";
		String pass = "DBUser";
		String name = "leopards";
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/" + name;

		System.out.println(driver);
		System.out.println(url);

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, user, pass);

			System.out.println("\nConnection successful!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	//close connection of database
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				// stmt.close();
				System.out.println("\nThe connection was successfully closed");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//check connection of database
	public static void checkConnect() {
		if (conn == null) {
			conn = createConnection();
		}
		if (stmt == null) {
			try {
				stmt = conn.createStatement();
			} catch (SQLException e) {
				System.out.println("Cannot create the statement");
			}

		}
	}
	//add information to the database
	public static void addInfo(int runID, int custID, String lane, int arrival, int serveTime, int finishTime, int waitTime) {
		
		String queryAdd = "INSERT INTO java_market(RunID, CustomerID, ServiceLane, "
				+ "ArrivalTime, WaitTime, ServiceTime, FinishTime) VALUES "
				+ "(" +runID + "," + custID + ",'" + lane + "'," + arrival + "," + waitTime + "," +serveTime+ "," + finishTime +")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);


		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	public static void selfServeTable(int lane, int unoccupied) {
		double avgWait = DataCollector.avgWaitTimeSelf();
		int satisfied = DataCollector.getSatisfiedSelf();
		int unsatisfied = DataCollector.getNumCustSelf() - satisfied;
		
		String queryAdd = "INSERT INTO self_service_data(RunID, serviceLane, unoccupiedTime, avgWaitTime, satisfiedCust, "
				+ "unsatisfiedCust) VALUES "
				+ "("+ queryRunID(conn) +",'" + lane + "',"+ unoccupied + "," + avgWait + "," + satisfied + "," + unsatisfied+")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	public static void fullServeTable(int lane, int unoccupied) {
		double avgWait = DataCollector.avgWaitTimeFull();
		int satisfied = DataCollector.getSatisfiedFull();
		int unsatisfied = DataCollector.getNumCustFull() - satisfied;
		
		String queryAdd = "INSERT INTO full_service_data(RunID, serviceLane, unoccupiedTime, avgWaitTime, satisfiedCust, "
				+ "unsatisfiedCust) VALUES "
				+ "("+queryRunID(conn)+ ",'" + lane + "',"+ unoccupied + "," + avgWait + "," + satisfied + "," + unsatisfied+")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(queryAdd);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL insert Exception");
		}
	}
	
	public static void callProcedures(int fullServ, SelfService self) {
		createConnection();
		Scanner scan = new Scanner(System.in);
		boolean cont = true;
		
		while (cont) {
		System.out.println("\nWould you like to run a procedure? (y / n)");
		String desc = scan.nextLine();
		if (desc.equalsIgnoreCase("y")) {
		    menu();
		    System.out.println("Please enter a choice: ");
		    int choice = scan.nextInt();
		    scan.nextLine();

		    if (choice == 1) {
		        System.out.println("\nDo you want Self-Service or Full-Service");
		        System.out.println("Type SS or FS: ");
		        String serviceChoice = scan.nextLine();
		        
		        if (serviceChoice.equalsIgnoreCase("SS")) {
					System.out.println("Which lane specifically? (Enter a lane number between 0 & "+(self.getRegisters().length-1)+")");
					int laneNum = scan.nextInt();
					scan.nextLine();
					String queryAdd = "call lane_stats ('SS"+laneNum+"')"; 
					System.out.println(queryAdd);
					try (PreparedStatement stmt = conn.prepareStatement(queryAdd)) {

						boolean first = true;
						ResultSet rs = stmt.executeQuery(); 
						while (rs.next()) { 
							if (first) { 
								System.out.println("| RunID | Lane | TotalPeople | AvgWaitTime | AvgServiceTime | AvgFinishTime ");
								first = false; 
								}
							
							int id = rs.getInt("RunID"); 
							String lane = rs.getString("Lane"); 
							int totalPeople = rs.getInt("TotalPeople"); 
							double avgWait = rs.getDouble("AvgWaitTime");
							double avgServe = rs.getDouble("AvgServiceTime"); 
							double avgFin = rs.getDouble("AvgFinishTime"); 
							System.out.println("|   "+ id  + "       " + lane +  "        " + totalPeople + "           " + avgWait+ "           " + avgServe
									+ "           " + avgFin);
							} 

						} catch (SQLException e) { 
							System.err.println("SQL Exception: " + e.getMessage());
							e.printStackTrace(); 
							}
					}
				if (serviceChoice.equalsIgnoreCase("FS")){ 
					System.out.println("Which lane specifically? (Enter a lane number between 0 & "+(fullServ-1)+")");
					int laneNum = scan.nextInt();
					scan.nextLine();
					String queryAdd = "call lane_stats ('"+laneNum+"')"; 
					System.out.println(queryAdd);
					try (PreparedStatement stmt = conn.prepareStatement(queryAdd)) {

						boolean first = true;
						ResultSet rs = stmt.executeQuery(); 
						while (rs.next()) { 
							if (first) { 
								System.out.println("| RunID | Lane | TotalPeople | AvgWaitTime | AvgServiceTime | AvgFinishTime "); 
								first = false; 
								}
							int id = rs.getInt("RunID"); 
							String lane = rs.getString("Lane"); 
							int totalPeople = rs.getInt("TotalPeople"); 
							double avgWait = rs.getDouble("AvgWaitTime");
							double avgServe = rs.getDouble("AvgServiceTime"); 
							double avgFin = rs.getDouble("AvgFinishTime"); 
							System.out.println("|   "+ id  + "       " + lane +  "        " + totalPeople + "            " + avgWait+ "            " + avgServe
									+ "            " + avgFin);
									
							} 

						} catch (SQLException e) { 
							System.err.println("SQL Exception: " + e.getMessage());
							e.printStackTrace(); 
							} 
		    }
		    }
			else if (choice == 2) {
				System.out.println("\nWhich CustomerID do you want to see specifically? (Enter number ID)");
				int custID = scan.nextInt();
				scan.nextLine();
				String query = "CALL pick_CustID("+custID+");";
				try(PreparedStatement stmt = conn.prepareStatement(query)){
				
					boolean first = true;
					ResultSet rs = stmt.executeQuery(); 
					while (rs.next()) { 
						if (first) { 
							System.out.println("| RunID | CustomerID | ServiceLane | ArrivalTime | WaitTime | ServiceTime | FinishTime "); 
							first = false; 
							}
						int id = rs.getInt("RunID");
						int foundID = rs.getInt("CustomerID");
						String lane = rs.getString("ServiceLane"); 
						double arrive = rs.getInt("ArrivalTime"); 
						double avgWait = rs.getDouble("WaitTime");
						double service = rs.getDouble("ServiceTime");
						double finish = rs.getDouble("FinishTime");
						System.out.println("|   "+ id + "         " + foundID + "            " + lane + "           " + arrive + "           " + avgWait + "          " + 
						service + "          " + finish);
						} 
					
					} catch (SQLException e) { 
						System.err.println("SQL Exception: " + e.getMessage());
						e.printStackTrace(); 
						} 
				}
				
			
			else if (choice == 3) {
				System.out.println("\nHere are the average statistics of every lane. ");
				String query = "CALL ppl_AndAvgPerLane(); " ;
				System.out.println(query);
				try (PreparedStatement stmt = conn.prepareStatement(query)) {
			 
					boolean first = true;
					ResultSet rs = stmt.executeQuery(); 
					while (rs.next()) { 
						if (first) { 
							System.out.println("| RunID | Lane | TotalPeople | AvgWaitTime "); 
							first = false; 
							}
						int id = rs.getInt("RunID"); 
						String lane = rs.getString("Lane"); 
						int totalPeople = rs.getInt("TotalPeople"); 
						double avgWait = rs.getDouble("AvgWaitTime"); 
						System.out.println("|   "+ id + "      " + lane + "        " + totalPeople + "           " + Math.round(avgWait * 100.0) / 100.0);
						} 
				 
					} catch (SQLException e) { 
						System.err.println("SQL Exception: " + e.getMessage());
						e.printStackTrace(); 
						} 
				
			}//end of choice 3
				}//end of if statement
		else if (desc.equalsIgnoreCase("n"))
			cont = false;
		    }//end of while loop
		System.out.println("\nGoodbye!");
		}
			
	
	public static void menu() {
		System.out.println("1. Show the average statistics of a desired lane: ");
		System.out.println("2. Show the statistics of a customer given their ID: ");
		System.out.println("3. Show the average statistics of every lane: ");
	}
	
	public static int queryRunID(Connection conn) {
		  String query = "SELECT MAX(RunID) FROM java_market";
		  int runID = 0;

		  try (PreparedStatement stmt = conn.prepareStatement(query)) {
		    ResultSet rs = stmt.executeQuery();
		    if (rs.next()) {
		      runID = rs.getInt(1);
		      setRunID(runID);
		    }
		  } catch (SQLException e) {
		    System.err.println("SQL Exception: " + e.getMessage());
		    e.printStackTrace();
		  }

		  return runID + 1;
		}
	
	public static int getRunID() {
		return runID;
	}
	public static void setRunID(int runID) {
		DatabaseInfo.runID = runID;
	}
	public static Connection getConn() {
		return conn;
	}
	public static void setConn(Connection conn) {
		DatabaseInfo.conn = conn;
	}
	public static Statement getStmt() {
		return stmt;
	}
	public static void setStmt(Statement stmt) {
		DatabaseInfo.stmt = stmt;
	}
	public static CallableStatement getCallStmt() {
		return callStmt;
	}
	public static void setCallStmt(CallableStatement callStmt) {
		DatabaseInfo.callStmt = callStmt;
	}
	
	
}
