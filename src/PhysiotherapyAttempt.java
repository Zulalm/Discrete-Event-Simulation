
public class PhysiotherapyAttempt extends Attempt implements Comparable<Attempt>{
	
	public PhysiotherapyAttempt(int playerID,double arrivalTime,double duration,double trainingDuration) {
		super(playerID,arrivalTime,duration,trainingDuration,"p");
	}
	public PhysiotherapyAttempt(Attempt attempt) {
		super(attempt.getPlayerID(),attempt.getArrivalTime(),attempt.getDuration(),attempt.getTrainingDuration(),"p");
	}
	@Override
	public int compareTo(Attempt o) {
		if(o instanceof PhysiotherapyAttempt) {
		if(this.getTrainingDuration()>o.getTrainingDuration()) {
			return -1;
		}
		else if(this.getTrainingDuration()<o.getTrainingDuration()) {
			return 1;
		}
		else if(this.getTrainingDuration()== o.getTrainingDuration()) {
			if(this.getArrivalTime()<o.getArrivalTime()) {
				return -1;
			}
			else if (this.getArrivalTime()>o.getArrivalTime()) {
				return 1;
			}
			else if (this.getArrivalTime()==o.getArrivalTime()) {
				if(this.getPlayerID()< o.getPlayerID()) {
					return -1;
				}
				else {
					return 1;
				}
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
