package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.DateHelper;
import beans.Korisnik;
import beans.LocalDateTimeHelper;
import beans.Location;
import beans.SportsFacility;
import beans.Training;
import beans.TrainingHistory;

public class TrainingHistoryDAO {

	private static TrainingHistoryDAO trainingInstance = null;
	private static String contextPath = "";

	private Map<Integer, TrainingHistory> training = new HashMap<>();

	private TrainingHistoryDAO() {

	}

	public TrainingHistoryDAO(String contextPath) {
		loadTraining(contextPath);
	}

	public static TrainingHistoryDAO getInstance() {
		if (trainingInstance == null) {
			trainingInstance = new TrainingHistoryDAO();
		}
		return trainingInstance;
	}

	/*
	 * public Membership find(String user_name, String user_password) { if
	 * (!training.containsKey(user_name)) { return null; } Membership membership =
	 * training.get(user_name); if
	 * (!membership.getUser_password().equals(user_password)) { return null; }
	 * return membership; }
	 */

	public Collection<TrainingHistory> findAll() {
		return training.values();
	}

	public TrainingHistory save(TrainingHistory trainings) {
		Integer maxId = -1;
		for (int id : training.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		trainings.setId(maxId);
		training.put(trainings.getId(),  trainings);
		saveToFile();
		return trainings;
	}

	public TrainingHistory find(int id) {
		return training.get(id);
	}

	public void loadTraining(String contextPath) {
		BufferedReader in = null;
		this.contextPath = contextPath;
		try {
			File file = new File(contextPath + "/Baza/trainingHistory.txt");
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
					LocalDateTime dateTimeCheck = LocalDateTimeHelper.stringToDate(st.nextToken().trim());

					int trainingId = Integer.parseInt(st.nextToken().trim());
					Training tr = new Training(trainingId);

					int bID = Integer.parseInt(st.nextToken().trim());
					Korisnik buy = new Korisnik(bID);

					int cID = Integer.parseInt(st.nextToken().trim());
					Korisnik coach = null;
					if (cID != -1) {
						coach = new Korisnik(cID);
					}


					training.put(id, new TrainingHistory(dateTimeCheck, tr, buy, coach, id));
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

	public TrainingHistory change(TrainingHistory trainings) {
		training.put(trainings.getId(), trainings);
		return trainings;
	}

	public TrainingHistory delete(int id) {
		return training.remove(id);
	}

	public void connectTrainingHistoryTraining() {
		ArrayList<Training> trainings = new ArrayList<Training>(TrainingDAO.getInstance().findAll());
		for (TrainingHistory tr : training.values()) {
			int id = tr.getTraining().getId();

			for (Training trs : trainings) {
				if (trs.getId() == id) {
					tr.setTraining(trs);
					break;
				}
			}
		}
	}

	public void connectBuyerKorisnik() {
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>(KorisnikDAO.getInstance().findAll());
		for (TrainingHistory buy : training.values()) {
			int id = buy.getBuyer().getId();

			for (Korisnik user : korisnici) {
				if (user.getId() == id) {
					buy.setBuyer(user);
					break;
				}
			}
		}
	}

	public void connectCoachKorisnik() {
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>(KorisnikDAO.getInstance().findAll());
		for (TrainingHistory coach : training.values()) {
			if(coach.getCoach() == null) {
				continue;
			}
			int id = coach.getCoach().getId();
			for (Korisnik user : korisnici) {
				if (user.getId() == id) {
					coach.setCoach(user);
					user.getHistory().add(coach);
					break;
				}
			}
		}
	}
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {

			File file = new File(contextPath + "/Baza/trainingHistory.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(TrainingHistory history : training.values()) {
				out.write(history.fileLine() + '\n');
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (out != null) {
				try {
					out.close();
				}
				catch (Exception e) { }
			}
		}
	}//vidi da li je collection dobar
	
	public Collection<TrainingHistory> getIstorijaTreningaZaKorisnika(int idKorisnika){
		ArrayList<TrainingHistory> pronadjene = new ArrayList<TrainingHistory>();
		for(TrainingHistory  istorijaTreninga: training.values()) {
			if(istorijaTreninga.getBuyer().getId() == idKorisnika) {
				pronadjene.add(istorijaTreninga);
			}
		}
		return pronadjene;
	}
}
