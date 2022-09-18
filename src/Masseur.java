import java.util.PriorityQueue;

public class Masseur implements Comparable<Masseur>{
	private int ID;
	private boolean isAvailable = true;
	private static PriorityQueue<Masseur> masseurQueue = new PriorityQueue<Masseur>();
	public Masseur(int ID) {
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
	public int compareTo(Masseur o) {
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
	 * @return the masseurQueue
	 */
	public static PriorityQueue<Masseur> getMasseurQueue() {
		return masseurQueue;
	}
	/**
	 * @param masseurQueue the masseurQueue to set
	 */
	public static void setMasseurQueue(PriorityQueue<Masseur> masseurQueue) {
		Masseur.masseurQueue = masseurQueue;
	}
}
