package beans;

import java.time.LocalDate;

public class Dues {
	private String idDues;
	private int id;
	private DuesType type;
	private LocalDate paymentDate;
	private LocalDate firstDay;
	private LocalDate lastDay;
	private Double price;
	private Korisnik buyer;
	private DuesStatus duesStatus;
	private int trainingNumbers;

	public Dues(String idDues, int id, DuesType type, LocalDate paymentDate, LocalDate firstDay, LocalDate lastDay, Double price,
			Korisnik buyer, DuesStatus duesStatus,int trainingNumbers) {
		super();
		this.idDues = idDues;
		this.id = id;
		this.type = type;
		this.paymentDate = paymentDate;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.price = price;
		this.buyer = buyer;
		this.duesStatus = duesStatus;
		this.trainingNumbers = trainingNumbers;
	}

	
	
	public Dues() {
	
	}

	public Dues(int duesId) {
		this.id = duesId;
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

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		if(paymentDate == null || paymentDate.equals("")) {
			return;
		}
		this.paymentDate = DateHelper.stringToDate(paymentDate);
	}
	

	public LocalDate getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(String firstDay) {
		if(firstDay== null   || firstDay.equals("")) {
			return;
		}
		this.firstDay = DateHelper.stringToDate(firstDay);
	}
	public LocalDate getLastDay() {
		return lastDay;
	}

	
	public void setLastDay(String lastDay) {
		if(lastDay == null || lastDay.equals("")) {
			return;
		}
		this.lastDay = DateHelper.stringToDate(lastDay);
	}


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Korisnik getBuyer() {
		return buyer;
	}

	public void setBuyer(Korisnik buyer) {
		this.buyer = buyer;
	}

	public DuesStatus getDuesStatus() {
		return duesStatus;
	}



	public void setDuesStatus(DuesStatus duesStatus) {
		this.duesStatus = duesStatus;
	}



	public int getTrainingNumbers() {
		return trainingNumbers;
	}

	public void setTrainingNumbers(int trainingNumbers) {
		trainingNumbers = trainingNumbers;
	}

	public String fileLine() {
		return id + ";" + idDues + ";" + type.ordinal()
				+ ";" + DateHelper.dateToString(paymentDate) + ";" +DateHelper.dateToString(firstDay) + ";"+ DateHelper.dateToString(lastDay) + ";"
				+ price + ";" + buyer + ";" + duesStatus.ordinal() + ";" + trainingNumbers;
	}
	
}
