import java.util.PriorityQueue;

public class Physiotherapist implements Comparable<Physiotherapist>{
	private int ID;
	private double serviceTime;
	private boolean isAvailable=true;
	private static int numOfTherapists=0;
	private static PriorityQueue<Physiotherapist> physQueue = new PriorityQueue<Physiotherapist>(); 
	public Physiotherapist(int ID,double serviceTime) {
		this.ID = ID;
		this.serviceTime = serviceTime;
		
	}
	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * @return the serviceTime
	 */
	public double getServiceTime() {
		return serviceTime;
	}
	/**
	 * @param serviceTime the serviceTime to set
	 */
	public void setServiceTime(double serviceTime) {
		this.serviceTime = serviceTime;
	}
	/**
	 * @return the numberOfTherapists
	 */
	public static int getNumOfTherapists() {
		return numOfTherapists;
	}
	/**
	 * @param numberOfTherapists the numberOfTherapists to set
	 */
	public static void setNumberOfTherapists(int numOfTherapists) {
		Physiotherapist.numOfTherapists = numOfTherapists;
	}
	@Override
	public int compareTo(Physiotherapist o) {
		if(this.isAvailable && !o.isAvailable) {
			return -1;
		}
		else if (!this.isAvailable && o.isAvailable) {
			return 1;
		}
		else if(this.ID< o.getID()) {
			return -1;
		}
		else {
			return 1;
		}
	}
	/**
	 * @return the isAvailable
	 */
	public boolean isAvailable() {
		return isAvailable;
	}
	/**
	 * @param isAvailable the isAvailable to set
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	/**
	 * @return the physQueue
	 */
	public static PriorityQueue<Physiotherapist> getPhysQueue() {
		return physQueue;
	}
	/**
	 * @param physQueue the physQueue to set
	 */
	public static void setPhysQueue(PriorityQueue<Physiotherapist> physQueue) {
		Physiotherapist.physQueue = physQueue;
	}
	
}
