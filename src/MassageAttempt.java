
public class MassageAttempt extends Attempt implements Comparable<Attempt>{
	
	
	public MassageAttempt(int playerID,double arrivalTime,double duration,double trainingDuration) {
		super(playerID,arrivalTime,duration,0,"m");
	}
	public MassageAttempt(Attempt attempt) {
		super(attempt.getPlayerID(),attempt.getArrivalTime(),attempt.getDuration(),0,"m");
	}
	@Override
	public int compareTo(Attempt o) {
		if (o instanceof MassageAttempt) {
		Player player1 = Player.getAllPlayers().get(this.getPlayerID());
		Player player2 = Player.getAllPlayers().get(o.getPlayerID());
		if(player1.getSkillLevel()>player2.getSkillLevel()) {
			return -1;
		}
		else if (player1.getSkillLevel()<player2.getSkillLevel()) {
			return 1;
		}
		else if (player1.getSkillLevel()==player2.getSkillLevel()) {
			if (this.getArrivalTime()< o.getArrivalTime()) {
				return -1;
			}
			else if(this.getArrivalTime()> o.getArrivalTime()) {
				return 1;
			}
			else if(this.getArrivalTime()== o.getArrivalTime()) {
				if(this.getPlayerID()<o.getPlayerID()) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
		}
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
