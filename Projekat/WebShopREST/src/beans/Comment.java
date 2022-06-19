package beans;

public class Comment {
	private int id;
	private Korisnik user;
	private SportsFacility facility;
	private String comment;
	private int grade;
	
	public Comment(int id,Korisnik user,SportsFacility facility, String comment, int grade) {
		super();
		this.id = id;
		this.user = user;
		this.facility = facility;
		this.comment = comment;
		this.grade = grade;
	}
	
	public Korisnik getUser() {
		return user;
	} 
	
	public void setUser(Korisnik user) {
		this.user = user;
	} 
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SportsFacility getFacility() {
		return facility;
	}
	
	public void setFacility(SportsFacility facility) {
		this.facility = facility;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
