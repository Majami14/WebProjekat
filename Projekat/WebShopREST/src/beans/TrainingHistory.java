package beans;

import java.time.LocalDateTime;

public class TrainingHistory {
	private LocalDateTime dateTimeCheck;
	private Training training;
	private Korisnik buyer;
	private Korisnik coach;

	public LocalDateTime getDateTimeCheck() {
		return dateTimeCheck;
	}

	public void setDateTimeCheck(LocalDateTime dateTimeCheck) {
		this.dateTimeCheck = dateTimeCheck;
	}
}
