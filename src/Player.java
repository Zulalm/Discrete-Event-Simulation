import java.util.ArrayList;
public class Player {
	private int ID;
	private int skillLevel;
	private double waitingTimeInPQ=0;
	private double waitingTimeInMQ=0;
	private int massage = 0;
	private boolean isAvailable = true;
	private static ArrayList<Player> allPlayers = new ArrayList<Player>();
	public Player(int ID,int skillLevel) {
		this.ID = ID;
		this.skillLevel = skillLevel;
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
	 * @return the skillLevel
	 */
	public int getSkillLevel() {
		return skillLevel;
	}
	/**
	 * @param skillLevel the skillLevel to set
	 */
	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
	/**
	 * @return the allPlayers
	 */
	public static ArrayList<Player> getAllPlayers() {
		return allPlayers;
	}
	/**
	 * @param allPlayers the allPlayers to set
	 */
	public static void setAllPlayers(ArrayList<Player> allPlayers) {
		Player.allPlayers = allPlayers;
	}
	/**
	 * @return the waitingTimeInPQ
	 */
	public double getWaitingTimeInPQ() {
		return waitingTimeInPQ;
	}
	/**
	 * @param waitingTimeInPQ the waitingTimeInPQ to set
	 */
	public void setWaitingTimeInPQ(double waitingTimeInPQ) {
		this.waitingTimeInPQ = waitingTimeInPQ;
	}
	/**
	 * @return the waitingTimeInMQ
	 */
	public double getWaitingTimeInMQ() {
		return waitingTimeInMQ;
	}
	/**
	 * @param waitingTimeInMQ the waitingTimeInMQ to set
	 */
	public void setWaitingTimeInMQ(double waitingTimeInMQ) {
		this.waitingTimeInMQ = waitingTimeInMQ;
	}
	/**
	 * @return the massage
	 */
	public int getMassage() {
		return massage;
	}
	/**
	 * @param massage the massage to set
	 */
	public void setMassage(int massage) {
		this.massage = massage;
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

	
}
