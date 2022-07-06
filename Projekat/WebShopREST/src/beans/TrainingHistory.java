package beans;

import java.time.LocalDateTime;

public class TrainingHistory {
	private LocalDateTime dateTimeCheck;
	private Training training;
	private Korisnik buyer;
	private Korisnik coach; //Moze biti prazno
	private int id;
	
	
	public TrainingHistory(LocalDateTime dateTimeCheck, Training training, Korisnik buyer, Korisnik coach, int id) {
		super();
		this.dateTimeCheck = dateTimeCheck;
		this.training = training;
		this.buyer = buyer;
		this.coach = coach;
		this.id = id;
	}

	
	
	public TrainingHistory() {

	}



	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public Korisnik getBuyer() {
		return buyer;
	}

	public void setBuyer(Korisnik buyer) {
		this.buyer = buyer;
	}

	public Korisnik getCoach() {
		return coach;
	}

	public void setCoach(Korisnik coach) {
		this.coach = coach;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateTimeCheck() {
		return LocalDateTimeHelper.dateToString(dateTimeCheck);
	}

	public void setDateTimeCheck(String dateTimeCheck) {
		this.dateTimeCheck = LocalDateTimeHelper.stringToDate(dateTimeCheck);
	}
	
	public String fileLine() {
		return  id + ";" + LocalDateTimeHelper.dateToString(dateTimeCheck) + ";" + training.getId()
				+ ";" + buyer.getId() + ";" + ((coach == null) ? -1 : coach.getId());
	}
}
