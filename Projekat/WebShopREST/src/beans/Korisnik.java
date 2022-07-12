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
	private ArrayList<SportsFacility> viewFacility;
	private int points;
	private TypeCustomer type;

	public Korisnik() {
		this.history = new ArrayList<TrainingHistory>();
		this.viewFacility = new ArrayList<SportsFacility>();
	}
	
	public Korisnik(int id) {
		this.id = id;
		this.history = new ArrayList<TrainingHistory>();
		this.viewFacility = new ArrayList<SportsFacility>();
	}
	
	
	public Korisnik(int id, String userName, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, Role role, ArrayList<TrainingHistory> history, Dues dues, SportsFacility facility,
			int points, TypeCustomer type) {
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
		this.viewFacility = new ArrayList<SportsFacility>();
	}

	
	

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

	public String getBirthDate() {
		return DateHelper.dateToString(birthDate);
	}

	public void setBirthDate1(LocalDate dateOfBirth) {
		this.birthDate = dateOfBirth;
	}

	public void setBirthDate(String dateOfBirth) {
		this.birthDate = DateHelper.stringToDate(dateOfBirth);
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

	public TypeCustomer getType() {
		return type;
	}

	public void setType(TypeCustomer type) {
		this.type = type;
	}

	public ArrayList<SportsFacility> getViewFacility() {
		return viewFacility;
	}

	public void setViewFacility(ArrayList<SportsFacility> viewFacility) {
		this.viewFacility = viewFacility;
	}
	
	
	
	

	public String fileLine() {
		return id + ";" + userName + ";" + password + ";" + firstName + ";" + lastName + ";" + ((gender == null)?-1:gender.ordinal()) + ";"
				+ DateHelper.dateToString(birthDate) + ";" + ((role == null)?-1:role.ordinal()) + ";" + ((dues == null)?-1:dues.getId()) + ";"
				+ ((facility == null)?-1:facility.getId()) + ";" + points + ";" + ((type == null)?-1:type.getId());
	}
	
}
