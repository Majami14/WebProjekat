package dto;

import java.time.LocalDateTime;

import beans.Korisnik;
import beans.Training;
import beans.TrainingHistory;

public class TrainingHistoryDTO {
	private String dateTimeCheck;
	private TrainingDTO training;
	private int id;
	
	
	
	public TrainingHistoryDTO(TrainingHistory th) {
		this.id = th.getId();
		this.dateTimeCheck = th.getDateTimeCheck();
		this.training = new TrainingDTO(th.getTraining());
	}
	
	
	public TrainingHistoryDTO() {

	}

	public TrainingDTO getTraining() {
		return training;
	}

	public void setTraining(TrainingDTO training) {
		this.training = training;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDateTimeCheck() {
		return dateTimeCheck;
	}

	public void setDateTimeCheck(String dateTimeCheck) {
		this.dateTimeCheck = dateTimeCheck;
	}
	
	
}