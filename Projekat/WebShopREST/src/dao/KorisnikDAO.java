package dao;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Korisnik;
import beans.Gender;
import beans.Role;
import beans.SportsFacility;
import beans.TrainingHistory;
import beans.TypeCustomer;
import beans.TypeName;
import beans.DateHelper;
import beans.Dues;

public class KorisnikDAO {
	private Map<Integer, Korisnik> korisnici = new HashMap<>();

	private static KorisnikDAO korisnikInstance = null;

	private KorisnikDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. MoÅ¾e se pristupiti samo
	 *                    iz servleta.
	 */
	public KorisnikDAO(String contextPath) {
		loadKorisnik(contextPath);
	}

	public static KorisnikDAO getInstance() {
		if (korisnikInstance == null) {
			korisnikInstance = new KorisnikDAO();
		}
		return korisnikInstance;
	}

	/**
	 * VraÄ‡a korisnika za prosleÄ‘eno korisniÄ�ko ime i Å¡ifru. VraÄ‡a null ako korisnik
	 * ne postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public Korisnik find(String user_name, String user_password) {
		
		for(Korisnik korisnik : korisnici.values()) {
			if(korisnik.getUserName().equals(user_name)) {
				if(korisnik.getPassword().equals(user_password)) {
					return korisnik;
				}
			}
		}
		
		return null;
	}

	public Korisnik find(int id) {
		return korisnici.get(id);
	}

	public Collection<Korisnik> findAll() {
		return korisnici.values();
	}

	public Korisnik save(Korisnik korisnik) {
		Integer maxId = -1;
		for (int id : korisnici.keySet()) { // da li je dobar id ili treba idk?
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		korisnik.setId(maxId);
		korisnici.put(korisnik.getId(), korisnik);
		return korisnik;
	}

	/**
	 * UÄ�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu
	 * {@link #users}. KljuÄ� je korisniÄ�ko ime korisnika.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadKorisnik(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/korisnici.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

					int id = Integer.parseInt(st.nextToken().trim());
					String userName = st.nextToken().trim();
					String password = st.nextToken().trim();
					String firstName = st.nextToken().trim();
					String lastName = st.nextToken().trim();

					int gender = Integer.parseInt(st.nextToken().trim());
					Gender[] genders = Gender.values();
					Gender genderFromFile = genders[gender];
					LocalDate birthDate = DateHelper.stringToDate(st.nextToken().trim());

					int role = Integer.parseInt(st.nextToken().trim());
					Role[] roles = Role.values();
					Role roleFromFile = roles[role];

					int duesId = Integer.parseInt(st.nextToken().trim());
					Dues dues = new Dues(duesId);
					int sportFacilityId = Integer.parseInt(st.nextToken().trim());
					SportsFacility facility = new SportsFacility(sportFacilityId);

					int points = Integer.parseInt(st.nextToken().trim());

					int idType = Integer.parseInt(st.nextToken().trim());
					TypeCustomer customer = new TypeCustomer(idType);


					korisnici.put(id,
							new Korisnik(id, userName, password, firstName, lastName, genderFromFile, birthDate,
									roleFromFile, new ArrayList<TrainingHistory>(), dues, facility, points,
									customer));
				}
				// int id, String userName, String password, String firstName, String lastName,
				// Gender gender,
				// LocalDate birthDate, Role role, ArrayList<TrainingHistory> history, Dues
				// dues, SportsFacility facility,
				// int points, TypeName type
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public void connectKorisnikDues() {
		ArrayList<Dues> duess=new ArrayList<Dues>(DuesDAO.getInstance().findAll());
		for(Korisnik korisnik : korisnici.values()) {
			int idWanted = korisnik.getDues().getId();
			for(Dues dues : duess) {
				if(dues.getId()==idWanted) {
					korisnik.setDues(dues);
					dues.setBuyer(korisnik);
					break;
				}
			}
		}
	}
	

	public Korisnik change(Korisnik korisnik) {
		korisnici.put(korisnik.getId(), korisnik);
		return korisnik;
	}
	
	public void linkUserAndVisitedObject(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/UserVisitedObjectBound.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					int userId = Integer.parseInt(st.nextToken().trim());
					int objectId = Integer.parseInt(st.nextToken().trim());
					Korisnik user = find(userId);
					SportsFacility sObject= SportsFacilityDAO.getInstance().find(objectId);
					
					user.getViewFacility().add(sObject);
				}
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}

	public void connectKorisnikSportsFacility() {
		ArrayList<SportsFacility> sport=new ArrayList<SportsFacility>(SportsFacilityDAO.getInstance().findAll());
		for(Korisnik korisnik : korisnici.values()) {
			int idWanted = korisnik.getFacility().getId();
			for(SportsFacility sport1 : sport ){
				if(sport1.getId()==idWanted) {
					korisnik.setFacility(sport1);
					
					break;
				}
			}
		}
	}
	public void connectKorisnikTypeCustomer() {
		ArrayList<TypeCustomer> custom=new ArrayList<TypeCustomer>(TypeCustomerDAO.getInstance().findAll());
		for(Korisnik korisnik : korisnici.values()) {
			int idWanted = korisnik.getType().getId();
			for(TypeCustomer person : custom ){
				if(person.getId()==idWanted) {
					korisnik.setType(person);
					
					break;
				}
			}
		}
	}
	public Korisnik delete(int id) {
		return korisnici.remove(id);
	}
}
