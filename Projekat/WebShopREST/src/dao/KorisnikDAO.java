package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import beans.Training;
import beans.TrainingHistory;
import beans.TypeCustomer;
import beans.TypeName;
import beans.DateHelper;
import beans.Dues;

public class KorisnikDAO {
	private Map<Integer, Korisnik> korisnici = new HashMap<>();

	private static KorisnikDAO korisnikInstance = null;
	private static String contextPath = "";

	private KorisnikDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. MoÅ¾e se pristupiti samo
	 *                    iz servleta.
	 */
	private KorisnikDAO(String contextPath) {
		loadKorisnik(contextPath);
	}

	public static KorisnikDAO getInstance() {
		if (korisnikInstance == null) {
			korisnikInstance = new KorisnikDAO();
		}
		return korisnikInstance;
	}

	/**
	 * VraÄ‡a korisnika za prosleÄ‘eno korisniÄ�ko ime i Å¡ifru. VraÄ‡a null ako
	 * korisnik ne postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */

	public Korisnik find(String user_name, String user_password) {

		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getUserName().equals(user_name)) {
				if (korisnik.getPassword().equals(user_password)) {
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

	/*
	 * public ArrayList<Korisnik> search(String searchValue, String criterion){
	 * ArrayList<Korisnik> find = new ArrayList<Korisnik>();
	 * if(criterion.equals("name")) { for(SportsFacility sp : facilitys.values()) {
	 * if(sp.getName().contains(searchValue)) { find.add(sp); } } } else
	 * if(criterion.equals("type")) { for(SportsFacility sp : facilitys.values()) {
	 * if(sp.getType().toString().contains(searchValue)) { find.add(sp); } } } else
	 * if(criterion.equals("location")){ for(SportsFacility sp : facilitys.values())
	 * { if(sp.getLocation().getCity().contains(searchValue) ||
	 * sp.getLocation().getStreet().contains(searchValue)) { find.add(sp); } } }
	 * else { for(SportsFacility sp : facilitys.values()) { if(sp.getAverage() ==
	 * Double.parseDouble(searchValue)) { //crazy opasno find.add(sp); } } } return
	 * find; }
	 */

	public Korisnik change(Korisnik korisnik) {
		korisnici.put(korisnik.getId(), korisnik);
		if (korisnik.getFacility() != null) {
			int id = korisnik.getFacility().getId();
			SportsFacility object = SportsFacilityDAO.getInstance().find(id);
			korisnik.setFacility(object);
		}
		if (korisnik.getDues() != null) {
			int id = korisnik.getDues().getId();
			Dues mem = DuesDAO.getInstance().find(id);
			korisnik.setDues(mem);
		}
		saveToFile();
		return korisnik;
	}

	public Korisnik save(Korisnik korisnik) {
		if (korisnik.getRole() == Role.CUSTOMER) {
			TypeCustomer type = new TypeCustomer(0);
			type.setType(TypeName.BRONZE);
			type.setDiscount(0);
			type.setPoints(0);
			type = TypeCustomerDAO.getInstance().save(type);
			korisnik.setType(type);
		}
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
		saveToFile();
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
		this.contextPath = contextPath;
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
					Dues dues = null;
					if (duesId != -1) {
						dues = new Dues(duesId);
					}

					int sportFacilityId = Integer.parseInt(st.nextToken().trim());
					SportsFacility facility = null;
					if (sportFacilityId != -1) {
						facility = new SportsFacility(sportFacilityId);
					}

					int points = Integer.parseInt(st.nextToken().trim());

					int idType = Integer.parseInt(st.nextToken().trim());
					TypeCustomer customer = new TypeCustomer(idType);

					korisnici.put(id,
							new Korisnik(id, userName, password, firstName, lastName, genderFromFile, birthDate,
									roleFromFile, new ArrayList<TrainingHistory>(), dues, facility, points, customer));
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

	public Korisnik check(String username, String password) {
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getUserName().equals(username)) {
				if (korisnik.getPassword().equals(password)) {
					return korisnik;
				}
			}
		}
		return null;
	}

	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/korisnici.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for (Korisnik user : korisnici.values()) {
				out.write(user.fileLine() + '\n');
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}

	}

//public Korisnik change(Korisnik korisnik) {
	// korisnici.put(korisnik.getId(), korisnik);
	// return korisnik;
	// }

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
					SportsFacility sObject = SportsFacilityDAO.getInstance().find(objectId);

					user.getViewFacility().add(sObject);
				}

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

	public ArrayList<Korisnik> getAllFreeManagers() {
		ArrayList<Korisnik> freeManagers = new ArrayList<Korisnik>();

		for (Korisnik user : korisnici.values()) {
			if (user.getRole() == Role.MANAGER) {
				if (user.getFacility() == null) {
					freeManagers.add(user);
				}
			}
		}
		return freeManagers;

	}

	public void connectKorisnikDues() {
		ArrayList<Dues> duess = new ArrayList<Dues>(DuesDAO.getInstance().findAll());
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getDues() == null) {
				continue;
			}
			int idWanted = korisnik.getDues().getId();
			for (Dues dues : duess) {
				if (dues.getId() == idWanted) {
					korisnik.setDues(dues);
					dues.setBuyer(korisnik);
					break;
				}
			}
		}
	}

	public void connectKorisnikSportsFacility() {
		ArrayList<SportsFacility> sport = new ArrayList<SportsFacility>(SportsFacilityDAO.getInstance().findAll());
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getFacility() == null) {
				continue;
			}
			int idWanted = korisnik.getFacility().getId();
			for (SportsFacility sport1 : sport) {
				if (sport1.getId() == idWanted) {
					korisnik.setFacility(sport1);

					break;
				}
			}
		}
	}

	public void connectKorisnikTypeCustomer() {
		ArrayList<TypeCustomer> custom = new ArrayList<TypeCustomer>(TypeCustomerDAO.getInstance().findAll());
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getType() == null) {
				continue;
			}
			int idWanted = korisnik.getType().getId();
			for (TypeCustomer person : custom) {
				if (person.getId() == idWanted) {
					korisnik.setType(person);

					break;
				}
			}
		}
	}

	public Korisnik delete(int id) {
		return korisnici.remove(id);
	}

	public boolean existsUsername(String username) {
		for (Korisnik korisnik : korisnici.values()) {
			if (korisnik.getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Korisnik> getCoach() {
		ArrayList<Korisnik> trainers = new ArrayList<Korisnik>();

		for (Korisnik user : korisnici.values()) {
			if (user.getRole() == Role.COACH) {
				trainers.add(user);
			}
		}
		return trainers;

	}

	public void setBuyerTypeNames() {
		for (Korisnik user : korisnici.values()) {
			if (user.getType() == null) {
				continue;
			}

			int poeni = user.getType().getPoints();
			if (poeni < 5000) {
				user.getType().setType(null);
				user.getType().setDiscount(3);
			} else if (poeni < 10000) {
				user.getType().setType(TypeName.SILVER);
				user.getType().setDiscount(5);
			} else {
				user.getType().setType(TypeName.GOLD);
				user.getType().setDiscount(10);
			}
		}
	}

	public boolean existsInList(int id, ArrayList<Korisnik> users) {
		for (Korisnik user : users) {
			if (user.getId() == id) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Korisnik> getPeopleThatVisitedObject(int id) {
		ArrayList<Korisnik> usersVisited = new ArrayList<Korisnik>();

		for (TrainingHistory trainingHistory : TrainingHistoryDAO.getInstance().findAll()) {

			if (trainingHistory.getTraining().getSportFacility().getId() == id) {
				if (!existsInList(trainingHistory.getBuyer().getId(), usersVisited)) {
					usersVisited.add(trainingHistory.getBuyer());
				}
			}

		}
		return usersVisited;
	}

	public ArrayList<Korisnik> getPeopleThatVisitedObject1(int id) {
		ArrayList<Korisnik> usersVisited = new ArrayList<Korisnik>();

		for (Korisnik user : korisnici.values()) {
			for (SportsFacility object : user.getViewFacility()) {
				if (object.getId() == id) {
					if (!existsInList(user.getId(), usersVisited)) {
						usersVisited.add(user);
					}
				}
			}
		}
		return usersVisited;
	}

	public ArrayList<Korisnik> getTrainersForObject(int id) {
		ArrayList<Korisnik> usersVisited = new ArrayList<Korisnik>();

		for (Training training : TrainingDAO.getInstance().findAll()) {
			if (training.getSportFacility().getId() == id) {
				if (training.getCoach() != null) {
					if (!existsInList(training.getCoach().getId(), usersVisited)) {
						usersVisited.add(training.getCoach());
					}
				}
			}
		}
		return usersVisited;
	}
}
