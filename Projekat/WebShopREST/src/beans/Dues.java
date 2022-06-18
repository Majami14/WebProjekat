package beans;

import java.time.LocalDate;

public class Dues {
	private String idDues;
	private int id;
	private DuesType type;
	private LocalDate paymentDate;
	private LocalDate validationDateTime;
	private Double price;
	private Korisnik buyer;
	private int TrainingNumbers;

	public Dues(String idDues, int id, DuesType type, LocalDate paymentDate, LocalDate validationDateTime, Double price,
			Korisnik buyer, int trainingNumbers) {
		super();
		this.idDues = idDues;
		this.id = id;
		this.type = type;
		this.paymentDate = paymentDate;
		this.validationDateTime = validationDateTime;
		this.price = price;
		this.buyer = buyer;
		TrainingNumbers = trainingNumbers;
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

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public LocalDate getValidationDateTime() {
		return validationDateTime;
	}

	public void setValidationDateTime(LocalDate validationDateTime) {
		this.validationDateTime = validationDateTime;
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

	public int getTrainingNumbers() {
		return TrainingNumbers;
	}

	public void setTrainingNumbers(int trainingNumbers) {
		TrainingNumbers = trainingNumbers;
	}

}
