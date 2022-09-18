import java.util.PriorityQueue;

public class EndOfService implements Comparable<EndOfService>{
	private Attempt attempt;
	private double time;
	private static PriorityQueue<EndOfService> ends = new PriorityQueue<EndOfService>(); 
	public EndOfService(double time,Attempt attempt) {
		this.attempt = attempt;
		this.time = time;
	}
	/**
	 * @return the attempt
	 */
	public Attempt getAttempt() {
		return attempt;
	}
	/**
	 * @param attempt the attempt to set
	 */
	public void setAttempt(Attempt attempt) {
		this.attempt = attempt;
	}
	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}
	@Override
	public int compareTo(EndOfService o) {
		if(this.time<o.time) {
			return -1;
		}
		else if(this.time>o.time) {
			return 1;
		}
		return 0;
	}
	/**
	 * @return the ends
	 */
	public static PriorityQueue<EndOfService> getEnds() {
		return ends;
	}
	/**
	 * @param ends the ends to set
	 */
	public void setEnds(PriorityQueue<EndOfService> ends) {
		EndOfService.ends = ends;
	}
}
