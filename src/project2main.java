import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class project2main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		ExcelFed excelFed = new ExcelFed();
		//input taking
		int numOfTotalPlayers = in.nextInt();
		for(int i=0;i<numOfTotalPlayers;i++) {
			String[] s = in.nextLine().split(" ");
			while(s.length <= 1 ) {
				s = in.nextLine().split(" ");}
			Player player = new Player(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
			Player.getAllPlayers().add(player);
		}

		int totalAttempts = in.nextInt();
		for (int i = 0; i<totalAttempts ; i++) {
			String[] s = in.nextLine().split(" ");
			while(s.length <= 1 ) {
				s = in.nextLine().split(" ");}
			String type = s[0];
			int ID = Integer.parseInt(s[1]);
			double arrivalTime = Double.parseDouble(s[2]);
			double duration = Double.parseDouble(s[3]);
			if (type.equals("t")) {
				Attempt attempt = new Attempt(ID,arrivalTime,duration,0,"t");
				Attempt.getAllAttempts().add(attempt);
			}
			else if (type.equals("m")){
				Attempt attempt = new Attempt(ID,arrivalTime,duration,0,"m");
				Attempt.getAllAttempts().add(attempt);
			}
		}
		String[] s = in.nextLine().split(" ");
		for(int i=1;i<s.length;i++) {
			double serviceTime = Double.parseDouble(s[i]);
			Physiotherapist physiotherapist = new Physiotherapist(i-1, serviceTime);
			Physiotherapist.getPhysQueue().add(physiotherapist);
		}
		s = in.nextLine().split(" ");
		int numOfCoaches = Integer.parseInt(s[0]);
		int numOfMasseurs = Integer.parseInt(s[1]);
		for(int i=0;i<numOfCoaches;i++) {
			Coach coach = new Coach(i);
			Coach.getCoachQueue().add(coach);
		}
		for(int i=0;i<numOfMasseurs;i++) {
			Masseur masseur = new Masseur(i);
			Masseur.getMasseurQueue().add(masseur);
		}
		/// end of input taking
		//process data
		// process data until all services ended
		while(!Attempt.getAllAttempts().isEmpty() || !EndOfService.getEnds().isEmpty()) {
			Attempt attempt = Attempt.getAllAttempts().peek();
			EndOfService end = EndOfService.getEnds().peek();
			if (attempt == null) {
				EndOfService.getEnds().poll();
				excelFed.endAttempt(end);
			}
			else if(end == null) {
				Attempt.getAllAttempts().poll();
				excelFed.startAttempt(attempt);
			}
			else {
				if(attempt.getArrivalTime()< end.getTime()) {
					Attempt.getAllAttempts().poll();
					excelFed.startAttempt(attempt);
				}
				else {
					EndOfService.getEnds().poll();
					excelFed.endAttempt(end);
				}
			}

		}
		// printing outputs
		out.println(excelFed.getMaxLengthTQ());
		out.println(excelFed.getMaxLengthPQ());
		out.println(excelFed.getMaxLengthMQ());
		out.println(String.format(Locale.ROOT,"%.3f", excelFed.getTotalWaitingInTQ()/excelFed.getTotalTrainingAttempts()));
		out.println(String.format(Locale.ROOT,"%.3f", excelFed.getTotalWaitingInPQ()/excelFed.getTotalPhysAttempts()));
		out.println(String.format(Locale.ROOT,"%.3f",excelFed.getTotalWaitingInMQ()/excelFed.getTotalMassageAttempts()));
		out.println(String.format(Locale.ROOT,"%.3f",excelFed.getTotalTrainingTime()/excelFed.getTotalTrainingAttempts()));
		out.println(String.format(Locale.ROOT,"%.3f",excelFed.getTotalPhysTime()/excelFed.getTotalPhysAttempts()));
		out.println(String.format(Locale.ROOT,"%.3f",excelFed.getTotalMassageTime()/excelFed.getTotalMassageAttempts()));
		out.println(String.format(Locale.ROOT,"%.3f",(excelFed.getTotalTrainingTime()+excelFed.getTotalPhysTime()+excelFed.getTotalWaitingInPQ()+excelFed.getTotalWaitingInTQ())/excelFed.getTotalTrainingAttempts()));
		out.println(excelFed.getIDofMostTimeInPQ()+" "+String.format(Locale.ROOT,"%.3f",excelFed.getMostTimeInPQ()));
		out.println(excelFed.getIDofLeastTimeInMQ()+" "+String.format(Locale.ROOT,"%.3f",excelFed.getLeastTimeInMQ()));
		out.println(excelFed.getInvalidAttempts());
		out.println(excelFed.getCanceledAttempts());
		out.println(String.format(Locale.ROOT,"%.3f",excelFed.getTime()));
	}

}
