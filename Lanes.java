package lepers;

public class Lanes {

	private CustomerList[] serviceLanes;
	private int[] downtime;
	
	public Lanes(int numLanes) {
		
		serviceLanes = new CustomerList[numLanes];
		for(int i=0;i<serviceLanes.length;++i)
			serviceLanes[i] = new CustomerList();
		downtime = new int[numLanes];
		
	}
		public void addCustomer(Customer cust, int time) {		
			int i = getShortestLane();		 
			cust.setServiceStartTime(time);
			cust.setLane(""+i, time);
			setFinish(cust, serviceLanes[i]);
			setWait(cust, serviceLanes[i]);
			serviceLanes[i].addFirst(cust);			
	}
		
	public boolean areEmpty() {
		for(int i=0;i<serviceLanes.length;++i) { 
			if(!serviceLanes[i].isEmpty())
				return false;
		}
		return true;
		
	}
	
	public void checkDepartures(int time) {
		Customer cust;
		CustomerList lane;
		for(int i=0;i<serviceLanes.length;++i) {
			lane = serviceLanes[i];
			if(!lane.isEmpty() && lane.frontPeek().getFinishTime() == time) {
				cust = lane.removeLast();
				DataCollector.addCustomer(cust);
				System.out.println(cust.getCustNum() + " finishes at " + time);
			}
		}
	}	
	public void emptyCheck() {
		for(int i=0;i<serviceLanes.length;++i)
			if(serviceLanes[i].isEmpty()) {
				++downtime[i];
			}
	}
	
	//Returns index of lane with the shortest line
	public int getShortestLane() {
		int index = 0;
		int len = serviceLanes[0].size();
		for(int i=1;i<serviceLanes.length;++i) {
			if(serviceLanes[i].size() < len) {
				len = serviceLanes[i].size();
				index = i;
			}
		}
		return index;
	}
	

	public void setFinish(Customer cust, CustomerList a) {
		
		int finishTime = 0;
		
		if (!a.isEmpty())
			finishTime = a.rearPeek().getFinishTime() + cust.getServiceTime();
		else
			finishTime = cust.getArrivalTime() + cust.getServiceTime();
		
		cust.setFinishTime(finishTime);
		
	}
	public static void setWait(Customer cust, CustomerList a) {
		
		int waitTime;
		
		if(a.isEmpty())
			waitTime = 0; //cust.getServiceTime();
		else
			waitTime = a.rearPeek().getFinishTime() - cust.getArrivalTime();
		
		cust.setWaitTime(waitTime);
		
	}
	public CustomerList[] getServiceLanes() {
		return serviceLanes;
	}
	public void setServiceLanes(CustomerList[] serviceLanes) {
		this.serviceLanes = serviceLanes;
	}
	public int[] getDowntime() {
		return downtime;
	}
	public void setDowntime(int[] downtime) {
		this.downtime = downtime;
	}
	
		
}
