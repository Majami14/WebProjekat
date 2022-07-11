package dto;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.DateHelper;
import beans.Dues;
import beans.Gender;
import beans.Korisnik;
import beans.Role;
import beans.SportsFacility;
import beans.TypeCustomer;

public class KorisnikDTO {
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private Gender gender;
	private LocalDate birthDate;
	private Role role;
	private DuesDTO dues;
	private SportsFacility facility;
	private int points;
	private TypeCustomer type;
	private DuesDTO duess;
	public KorisnikDTO() {
		
	}
	/*public KorisnikDTO(int id, String userName, String password, String firstName, String lastName, Gender gender,
			LocalDate birthDate, Role role, Dues dues, SportsFacility facility, int points, TypeCustomer type) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.role = role;
		this.dues = dues;
		this.facility = facility;
		this.points = points;
		this.type = type;
	} */
	
	public KorisnikDTO(Korisnik user) {
		super();
		this.id = user.getId();
		this.userName = user.getUserName();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.birthDate = DateHelper.stringToDate(user.getBirthDate());
		this.role = user.getRole();
		this.password = user.getPassword();
		this.gender = user.getGender();
		this.dues = (user.getDues() == null)?null:(new DuesDTO(user.getDues()));
		this.facility = user.getFacility();
		this.points = user.getPoints();
		this.type = user.getType();
		
		
	}
	
	
	public DuesDTO getDuess() {
		return duess;
	}

	public void setDuess(DuesDTO duess) {
		this.duess = duess;
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
	public DuesDTO getDues() {
		return dues;
	}
	public void setDues(DuesDTO dues) {
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
	
	
	
}
