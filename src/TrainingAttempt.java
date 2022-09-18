
public class TrainingAttempt extends Attempt implements Comparable<Attempt>{
	
	public TrainingAttempt(int playerID,double arrivalTime, double duration) {
		super(playerID,arrivalTime,duration,0,"t");
	}
	public TrainingAttempt(Attempt attempt) {
		super(attempt.getPlayerID(),attempt.getArrivalTime(),attempt.getDuration(),0,"t");
	}
	@Override
	public int compareTo(Attempt o) {
		if (o instanceof TrainingAttempt) {
		if(this.getArrivalTime()<o.getArrivalTime()) {
			return -1;
		}
		else if (this.getArrivalTime()>o.getArrivalTime()){
			return 1;
		}
		else if (this.getArrivalTime()==o.getArrivalTime()) {
			if(this.getPlayerID()< o.getPlayerID()) {
				return -1;
			}
			else {
				return 1;
			}
		}}
		else {
			if (this.getArrivalTime()< o.getArrivalTime()) {
				return -1;
			}
			else if (this.getArrivalTime()> o.getArrivalTime()){
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
		return 0;
	}
	
	
}
