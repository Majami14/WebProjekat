package beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Korisnik {
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Gender gender;
	private LocalDate birthDate;
	private Role role;
	private ArrayList<TrainingHistory> history;
	private Dues dues;
	private SportsFacility facility;
	// private ArrayList<SportsFacility> viewFacility;
	private int points;
	private TypeName type;
	private int coachId;

	public Korisnik(int id, String userName, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, Role role, ArrayList<TrainingHistory> history, Dues dues, SportsFacility facility,
			int points, TypeName type) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.role = role;
		this.history = history;
		this.dues = dues;
		this.facility = facility;
		this.points = points;
		this.type = type;
	}

	public Korisnik(int coachId) {
		this.coachId = coachId;}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ArrayList<TrainingHistory> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<TrainingHistory> history) {
		this.history = history;
	}

	public Dues getDues() {
		return dues;
	}

	public void setDues(Dues dues) {
		this.dues = dues;
	}

	public SportsFacility getFacility() {
		return facility;
	}

	public void setFacility(SportsFacility facility) {
		this.facility = facility;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public TypeName getType() {
		return type;
	}

	public void setType(TypeName type) {
		this.type = type;
	}

}
