import java.util.PriorityQueue;

public class ExcelFed {
	private int maxLengthTQ= 0;
	private int maxLengthPQ =0;
	private int maxLengthMQ = 0;
	private double totalWaitingInTQ = 0;
	private double totalWaitingInPQ = 0;
	private double totalWaitingInMQ = 0;
	private int totalTrainingAttempts = 0;
	private int totalPhysAttempts = 0;
	private int totalMassageAttempts = 0;
	private double totalTrainingTime = 0;
	private double totalPhysTime = 0;
	private double totalMassageTime = 0;
	private double averageTurnaroundTime;
	private int invalidAttempts=0;
	private int canceledAttempts = 0;
	private double time = 0;
	private int IDofMostTimeInPQ;
	private double mostTimeInPQ = 0;
	private int IDofLeastTimeInMQ = -1;
	private double leastTimeInMQ = -1;
	private PriorityQueue<PhysiotherapyAttempt> PQ = new PriorityQueue<PhysiotherapyAttempt>();
	private PriorityQueue<MassageAttempt> MQ = new PriorityQueue<MassageAttempt>();
	private PriorityQueue<TrainingAttempt> TQ = new PriorityQueue<TrainingAttempt>();
	
	public void checkTQ() {
		//checks if there is a possible match between attempts in the queue and coaches
		Coach coach = Coach.getCoachQueue().poll();
		if(coach.isAvailable()&& !TQ.isEmpty()) {
			coach.setAvailable(false);
			Coach.getCoachQueue().add(coach);
			TrainingAttempt attempt = TQ.poll();
			attempt.setCoach(coach);
			double waitingTime = time - attempt.getArrivalTime();
			totalWaitingInTQ += waitingTime;
			totalTrainingTime += attempt.getDuration();
			totalTrainingAttempts++;
			EndOfService end = new EndOfService(time+attempt.getDuration(), attempt);
			EndOfService.getEnds().add(end);
		}
		else {
			Coach.getCoachQueue().add(coach);
			if(TQ.size()>maxLengthTQ) {
				maxLengthTQ = TQ.size();
			}
		}

	}
	public void checkMQ() {
		//checks if there is a possible match between attempts in the queue and masseurs
		Masseur masseur = Masseur.getMasseurQueue().poll();
		if(masseur.isAvailable()&& !MQ.isEmpty()) {
			masseur.setAvailable(false);
			Masseur.getMasseurQueue().add(masseur);
			MassageAttempt attempt = MQ.poll();
			attempt.setMasseur(masseur);
			double waitingTime = time - attempt.getArrivalTime();
			totalWaitingInMQ += waitingTime;
			totalMassageTime += attempt.getDuration();
			totalMassageAttempts++;
			Player player = Player.getAllPlayers().get(attempt.getPlayerID());
			
			player.setWaitingTimeInMQ(player.getWaitingTimeInMQ()+waitingTime);
			if(player.getMassage()==3) {
				if(Math.abs(player.getWaitingTimeInMQ()-leastTimeInMQ)<0.0000000001) {
					IDofLeastTimeInMQ = Math.min(player.getID(), IDofLeastTimeInMQ);
				}
				else if(leastTimeInMQ<0 || leastTimeInMQ > player.getWaitingTimeInMQ()) {
					IDofLeastTimeInMQ = player.getID();
					leastTimeInMQ = player.getWaitingTimeInMQ();
				}}
			EndOfService end = new EndOfService(time+attempt.getDuration(), attempt);
			EndOfService.getEnds().add(end);
		}
		else {
			Masseur.getMasseurQueue().add(masseur);
			if(MQ.size()>maxLengthMQ) {
				maxLengthMQ = MQ.size();
			}
		}

	}
	public void checkPQ() {
		//checks if there is a possible match between attempts in the queue and  physiotherapists
		Physiotherapist phys = Physiotherapist.getPhysQueue().poll();
		if(phys.isAvailable()&& !PQ.isEmpty()) {
			phys.setAvailable(false);
			PhysiotherapyAttempt attempt = PQ.poll();
			attempt.setPhys(phys);
			double waitingTime = time - attempt.getArrivalTime();
			attempt.setDuration(phys.getServiceTime());
			totalWaitingInPQ += waitingTime;
			totalPhysTime += attempt.getDuration();
			totalPhysAttempts++;
			Player player = Player.getAllPlayers().get(attempt.getPlayerID());
			player.setWaitingTimeInPQ(player.getWaitingTimeInPQ()+waitingTime);
			if((player.getWaitingTimeInPQ()-mostTimeInPQ)>0.0000000001) {
				mostTimeInPQ = player.getWaitingTimeInPQ();
				IDofMostTimeInPQ = player.getID();
			}
			else if(Math.abs(player.getWaitingTimeInPQ()-mostTimeInPQ)<0.0000000001) {
				IDofMostTimeInPQ = Math.min(IDofMostTimeInPQ, player.getID());
			}
			EndOfService end = new EndOfService(time+attempt.getDuration(), attempt);
			EndOfService.getEnds().add(end);

		}

		else {
			if(PQ.size()>maxLengthPQ) {
				maxLengthPQ = PQ.size();
			}
		}
		Physiotherapist.getPhysQueue().add(phys);
	}
	public void endAttempt(EndOfService end) {
		// ends services and makes player and related staff available again
		this.setTime(end.getTime());
		Attempt endattempt = end.getAttempt();
		if(endattempt.getType().equals("t")) {
			Coach coach = endattempt.getCoach();
			Coach.getCoachQueue().remove(coach);
			coach.setAvailable(true);
			Coach.getCoachQueue().add(coach);
			PhysiotherapyAttempt attempt2 = new PhysiotherapyAttempt(endattempt.getPlayerID(), end.getTime(),0,endattempt.getDuration());
			
			this.getPQ().add(attempt2);
			this.checkPQ();
			this.checkTQ();
		}
		else if(endattempt.getType().equals("p")) {
			Physiotherapist phys = endattempt.getPhys();
			Physiotherapist.getPhysQueue().remove(phys);
			phys.setAvailable(true);
			Physiotherapist.getPhysQueue().add(phys);
			Player.getAllPlayers().get(endattempt.getPlayerID()).setAvailable(true);
			this.checkPQ();
		}
		else if(endattempt.getType().equals("m")) {
			Masseur masseur = endattempt.getMasseur();
			Masseur.getMasseurQueue().remove(masseur);
			masseur.setAvailable(true);
			Masseur.getMasseurQueue().add(masseur);
			Player.getAllPlayers().get(endattempt.getPlayerID()).setAvailable(true);
			this.checkMQ();
		}
	}
	public void startAttempt(Attempt attempt) {
		// sends player to the related queue
		this.setTime(attempt.getArrivalTime());
		Player player = Player.getAllPlayers().get(attempt.getPlayerID());
		if(player.getMassage()>=3 && attempt.getType().equals("m")) {
			this.setInvalidAttempts(this.getInvalidAttempts()+1);
		}
		else if(player.isAvailable()) {
			player.setAvailable(false);
			if (attempt.getType().equals("t")) {
				TrainingAttempt attempt2 = new TrainingAttempt(attempt);
				this.getTQ().add(attempt2);
				this.checkTQ();
			}
			else if (attempt.getType().equals("m")) {
				MassageAttempt attempt2 = new MassageAttempt(attempt);
				player.setMassage(player.getMassage()+1);
				this.getMQ().add(attempt2);
				this.checkMQ();
			}

		}
		else {
			this.setCanceledAttempts(this.getCanceledAttempts()+1);
		}
	}
	/**
	 * @return the maxLengthTQ
	 */
	public int getMaxLengthTQ() {
		return maxLengthTQ;
	}
	/**
	 * @param maxLengthTQ the maxLengthTQ to set
	 */
	public void setMaxLengthTQ(int maxLengthTQ) {
		this.maxLengthTQ = maxLengthTQ;
	}
	/**
	 * @return the maxLengthPQ
	 */
	public int getMaxLengthPQ() {
		return maxLengthPQ;
	}
	/**
	 * @param maxLengthPQ the maxLengthPQ to set
	 */
	public void setMaxLengthPQ(int maxLengthPQ) {
		this.maxLengthPQ = maxLengthPQ;
	}
	/**
	 * @return the maxLengthMQ
	 */
	public int getMaxLengthMQ() {
		return maxLengthMQ;
	}
	/**
	 * @param maxLengthMQ the maxLengthMQ to set
	 */
	public void setMaxLengthMQ(int maxLengthMQ) {
		this.maxLengthMQ = maxLengthMQ;
	}
	/**
	 * @return the totalWaitingInTQ
	 */
	public double getTotalWaitingInTQ() {
		return totalWaitingInTQ;
	}
	/**
	 * @param totalWaitingInTQ the totalWaitingInTQ to set
	 */
	public void setTotalWaitingInTQ(double totalWaitingInTQ) {
		this.totalWaitingInTQ = totalWaitingInTQ;
	}
	/**
	 * @return the totalWaitingInPQ
	 */
	public double getTotalWaitingInPQ() {
		return totalWaitingInPQ;
	}
	/**
	 * @param totalWaitingInPQ the totalWaitingInPQ to set
	 */
	public void setTotalWaitingInPQ(double totalWaitingInPQ) {
		this.totalWaitingInPQ = totalWaitingInPQ;
	}
	/**
	 * @return the totalWaitingInMQ
	 */
	public double getTotalWaitingInMQ() {
		return totalWaitingInMQ;
	}
	/**
	 * @param totalWaitingInMQ the totalWaitingInMQ to set
	 */
	public void setTotalWaitingInMQ(double totalWaitingInMQ) {
		this.totalWaitingInMQ = totalWaitingInMQ;
	}
	/**
	 * @return the totalTrainingAttempts
	 */
	public int getTotalTrainingAttempts() {
		return totalTrainingAttempts;
	}
	/**
	 * @param totalTrainingAttempts the totalTrainingAttempts to set
	 */
	public void setTotalTrainingAttempts(int totalTrainingAttempts) {
		this.totalTrainingAttempts = totalTrainingAttempts;
	}
	/**
	 * @return the totalPhysAttempts
	 */
	public int getTotalPhysAttempts() {
		return totalPhysAttempts;
	}
	/**
	 * @param totalPhysAttempts the totalPhysAttempts to set
	 */
	public void setTotalPhysAttempts(int totalPhysAttempts) {
		this.totalPhysAttempts = totalPhysAttempts;
	}
	/**
	 * @return the totalMassageAttempts
	 */
	public int getTotalMassageAttempts() {
		return totalMassageAttempts;
	}
	/**
	 * @param totalMassageAttempts the totalMassageAttempts to set
	 */
	public void setTotalMassageAttempts(int totalMassageAttempts) {
		this.totalMassageAttempts = totalMassageAttempts;
	}
	/**
	 * @return the totalTrainingTime
	 */
	public double getTotalTrainingTime() {
		return totalTrainingTime;
	}
	/**
	 * @param totalTrainingTime the totalTrainingTime to set
	 */
	public void setTotalTrainingTime(double totalTrainingTime) {
		this.totalTrainingTime = totalTrainingTime;
	}
	/**
	 * @return the totalPhysTime
	 */
	public double getTotalPhysTime() {
		return totalPhysTime;
	}
	/**
	 * @param totalPhysTime the totalPhysTime to set
	 */
	public void setTotalPhysTime(double totalPhysTime) {
		this.totalPhysTime = totalPhysTime;
	}
	/**
	 * @return the totalMassageTime
	 */
	public double getTotalMassageTime() {
		return totalMassageTime;
	}
	/**
	 * @param totalMassageTime the totalMassageTime to set
	 */
	public void setTotalMassageTime(double totalMassageTime) {
		this.totalMassageTime = totalMassageTime;
	}
	/**
	 * @return the averageTurnaroundTime
	 */
	public double getAverageTurnaroundTime() {
		return averageTurnaroundTime;
	}
	/**
	 * @param averageTurnaroundTime the averageTurnaroundTime to set
	 */
	public void setAverageTurnaroundTime(double averageTurnaroundTime) {
		this.averageTurnaroundTime = averageTurnaroundTime;
	}
	/**
	 * @return the invalidAttempts
	 */
	public int getInvalidAttempts() {
		return invalidAttempts;
	}
	/**
	 * @param invalidAttempts the invalidAttempts to set
	 */
	public void setInvalidAttempts(int invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}
	/**
	 * @return the canceledAttempts
	 */
	public int getCanceledAttempts() {
		return canceledAttempts;
	}
	/**
	 * @param canceledAttempts the canceledAttempts to set
	 */
	public void setCanceledAttempts(int canceledAttempts) {
		this.canceledAttempts = canceledAttempts;
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
	/**
	 * @return the iDofMostTimeInPQ
	 */
	public int getIDofMostTimeInPQ() {
		return IDofMostTimeInPQ;
	}
	/**
	 * @param iDofMostTimeInPQ the iDofMostTimeInPQ to set
	 */
	public void setIDofMostTimeInPQ(int iDofMostTimeInPQ) {
		IDofMostTimeInPQ = iDofMostTimeInPQ;
	}
	/**
	 * @return the mostTimeInPQ
	 */
	public double getMostTimeInPQ() {
		return mostTimeInPQ;
	}
	/**
	 * @param mostTimeInPQ the mostTimeInPQ to set
	 */
	public void setMostTimeInPQ(double mostTimeInPQ) {
		this.mostTimeInPQ = mostTimeInPQ;
	}
	/**
	 * @return the iDofLeastTimeInMQ
	 */
	public int getIDofLeastTimeInMQ() {
		return IDofLeastTimeInMQ;
	}
	/**
	 * @param iDofLeastTimeInMQ the iDofLeastTimeInMQ to set
	 */
	public void setIDofLeastTimeInMQ(int iDofLeastTimeInMQ) {
		IDofLeastTimeInMQ = iDofLeastTimeInMQ;
	}
	/**
	 * @return the leastTimeInPQ
	 */
	public double getLeastTimeInMQ() {
		return leastTimeInMQ;
	}
	/**
	 * @param leastTimeInPQ the leastTimeInPQ to set
	 */
	public void setLeastTimeInMQ(double leastTimeInPQ) {
		this.leastTimeInMQ = leastTimeInPQ;
	}
	/**
	 * @return the pQ
	 */
	public PriorityQueue<PhysiotherapyAttempt> getPQ() {
		return PQ;
	}
	/**
	 * @param pQ the pQ to set
	 */
	public void setPQ(PriorityQueue<PhysiotherapyAttempt> pQ) {
		PQ = pQ;
	}
	/**
	 * @return the mQ
	 */
	public PriorityQueue<MassageAttempt> getMQ() {
		return MQ;
	}
	/**
	 * @param mQ the mQ to set
	 */
	public void setMQ(PriorityQueue<MassageAttempt> mQ) {
		MQ = mQ;
	}
	/**
	 * @return the tQ
	 */
	public PriorityQueue<TrainingAttempt> getTQ() {
		return TQ;
	}
	/**
	 * @param tQ the tQ to set
	 */
	public void setTQ(PriorityQueue<TrainingAttempt> tQ) {
		TQ = tQ;
	}


}
