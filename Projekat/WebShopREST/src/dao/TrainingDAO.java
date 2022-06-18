package dao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Dues;
import beans.Korisnik;
import beans.SportsFacility;
import beans.Training;
import beans.TrainingType;
import beans.TypeName;
public class TrainingDAO {
private Map<Integer, Training> training = new HashMap<>();
	
	
	public TrainingDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public TrainingDAO(String contextPath) {
		loadTraining(contextPath);
	}
	
	/**
	 * Vraća korisnika za prosleđeno korisničko ime i šifru. Vraća null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	/*public Membership find(String user_name, String user_password) {
		if (!training.containsKey(user_name)) {
			return null;
		}
		Membership membership = training.get(user_name);
		if (!membership.getUser_password().equals(user_password)) {
			return null;
		}
		return membership;
	}*/
	
	public Collection<Training> findAll() {
		return training.values();
	}
	
	
	public Training save(Training trainings) {
		Integer maxId = -1;
		for (int id : training.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		trainings.setId(maxId);
		training.put(trainings.getId(), (Training) training);
		return trainings;
	}
	
	/**
	 * Učitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Ključ je korisničko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadTraining(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/training.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					
					
					String name = st.nextToken().trim();
					
					int type = Integer.parseInt(st.nextToken().trim());
					TrainingType[] types = TrainingType.values();
					TrainingType typeFromFile = types[type] ;
					
					
					
					int sportFacilityId = Integer.parseInt(st.nextToken().trim());
					SportsFacility facility = new SportsFacility(sportFacilityId);
					int duration  =Integer.parseInt(st.nextToken().trim());
					int coachId = Integer.parseInt(st.nextToken().trim());
					Korisnik coach = new Korisnik(coachId);
					String description = st.nextToken().trim();
					String image = st.nextToken().trim();
					int id = Integer.parseInt(st.nextToken().trim());
					
	//(String name, Training type, SportsFacility sportFacility, int duration, Korisnik coach,
		//String description, String image, int id			
					
			
					training.put(id, new Training(name,typeFromFile,facility,duration,coach,description,image,id));
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
	public Training change(Training trainings) {
		training.put(trainings.getId(), trainings);
		return trainings;
	}
	
	public Training delete(int id) {
		return training.remove(id);
	}
}
