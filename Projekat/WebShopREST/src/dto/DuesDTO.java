package dto;

import java.time.LocalDate;

import beans.DateHelper;
import beans.Dues;
import beans.DuesStatus;
import beans.DuesType;

public class DuesDTO {
	private String idDues;
	private int id;
	private DuesType type; 
	private LocalDate paymentDate;
	private LocalDate firstDay;
	private LocalDate lastDay;
	private double price;
	private DuesStatus duesStatus;
	private int trainingNumbers;
	
	public DuesDTO() {
		
	}
	

	public DuesDTO(Dues dues) {
		this.idDues = dues.getIdDues();
		this.id = dues.getId();
		this.type = dues.getType();
		this.paymentDate = dues.getPaymentDate();
		this.firstDay = dues.getFirstDay();
		this.lastDay = dues.getLastDay();
		this.price = dues.getPrice();
		this.duesStatus = dues.getDuesStatus();
		this.trainingNumbers = dues.getTrainingNumbers();
		
	}
	
	
	public String getIdDues() {
		return idDues;
	}


	public void setIdDues(String idDues) {
		this.idDues = idDues;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public DuesType getType() {
		return type;
	}


	public void setType(DuesType type) {
		this.type = type;
	}



	public String getPaymentDate() {
		return DateHelper.dateToString(paymentDate);
	}


	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getFirstDay() {
		if(firstDay == null)
		{
			return"" ;
		}
		return DateHelper.dateToString(firstDay);
	}


	public void setFirstDay(LocalDate firstDay) {
		this.firstDay = firstDay;
	}


	public String getLastDay() {
		if(lastDay == null) {
			return "";
		}
		return DateHelper.dateToString(lastDay);
	
	}


	public void setLastDay(LocalDate lastDay) {
		this.lastDay = lastDay;
	}


	public DuesStatus getDuesStatus() {
		return duesStatus;
	}


	public void setDuesStatus(DuesStatus duesStatus) {
		this.duesStatus = duesStatus;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getTrainingNumbers() {
		return trainingNumbers;
	}


	public void setTrainingNumbers(int trainingNumbers) {
		this.trainingNumbers = trainingNumbers;
	}



	
}