package beans;

public class TypeCustomer {
	private int id;
	private TypeName type;
	private double discount;
	private int points;
	
	public TypeCustomer(int id) {
		this.id = id;
	}
	public TypeCustomer(int id, TypeName type, double discount, int points) {
		super();
		this.id = id;
		this.type = type;
		this.discount = discount;
		this.points = points;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TypeName getType() {
		return type;
	}
	public void setType(TypeName type) {
		this.type = type;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
