import java.util.PriorityQueue;

public class Coach implements Comparable<Coach>{
	private int ID;
	private boolean isAvailable = true;
	private static PriorityQueue<Coach> coachQueue = new PriorityQueue<Coach>(); 
	public Coach(int ID) {
		this.ID = ID;

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
	
	
	@Override
	public int compareTo(Coach o) {
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
	 * @return the coachQueue
	 */
	public static PriorityQueue<Coach> getCoachQueue() {
		return coachQueue;
	}
	/**
	 * @param coachQueue the coachQueue to set
	 */
	public static void setCoachQueue(PriorityQueue<Coach> coachQueue) {
		Coach.coachQueue = coachQueue;
	}
	
}
