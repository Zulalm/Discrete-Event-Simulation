import java.util.PriorityQueue;

public class Attempt implements Comparable<Attempt>{
	private int playerID;
	private double arrivalTime;
	private double duration;
	private double trainingDuration;
	private String type;
	private Physiotherapist phys;
	private Coach coach;
	private Masseur masseur;
	private static PriorityQueue<Attempt> allAttempts = new PriorityQueue<Attempt>();
	public Attempt(int playerID,double arrivalTime,double duration,double trainingDuration,String type) {
		this.playerID =playerID;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
		this.trainingDuration = trainingDuration;
		this.type = type;
		
	}
	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	/**
	 * @return the arrivalTime
	 */
	public double getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public int compareTo(Attempt o) {
		if (this.arrivalTime< o.getArrivalTime()) {
			return -1;
		}
		else if (this.arrivalTime> o.getArrivalTime()){
			return 1;
		
		}
		else {
			if (this.getPlayerID()<o.getPlayerID()) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
	/**
	 * @return the allAttempts
	 */
	public static PriorityQueue<Attempt> getAllAttempts() {
		return allAttempts;
	}
	/**
	 * @param allAttempts the allAttempts to set
	 */
	public static void setAllAttempts(PriorityQueue<Attempt> allAttempts) {
		Attempt.allAttempts = allAttempts;
	}
	/**
	 * @return the trainingDuration
	 */
	public double getTrainingDuration() {
		return trainingDuration;
	}
	/**
	 * @param trainingDuration the trainingDuration to set
	 */
	public void setTrainingDuration(double trainingDuration) {
		this.trainingDuration = trainingDuration;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the phys
	 */
	public Physiotherapist getPhys() {
		return phys;
	}
	/**
	 * @param phys the phys to set
	 */
	public void setPhys(Physiotherapist phys) {
		this.phys = phys;
	}
	/**
	 * @return the coach
	 */
	public Coach getCoach() {
		return coach;
	}
	/**
	 * @param coach the coach to set
	 */
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	/**
	 * @return the masseur
	 */
	public Masseur getMasseur() {
		return masseur;
	}
	/**
	 * @param masseur the masseur to set
	 */
	public void setMasseur(Masseur masseur) {
		this.masseur = masseur;
	}
	
}
