package beans;

public class Location {
	private int id;
	private double length;
	private double width;
	private String street;
	private String number; 
	private String city;
	private String post;
	
	public Location(int id) {
		this.id = id;
	}
	public Location(int id, double length, double width, String street, String number, String city, String post) {
		super();
		this.id = id;
		this.length = length;
		this.width = width;
		this.street = street;
		this.number = number;
		this.city = city;
		this.post = post;
	}	
	
	public Location() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}

	public String fileLine() {
		return id + ";" + length + ";" + width
				+ ";" + street + ";" + number + ";" + city + ";"
				+ post;
	}
	
	
}
