package dao;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
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
		training.put(trainings.getId(), (TrainingHistory) training);
		return trainings;
	}

	public TrainingHistory find(int id) {
		return training.get(id);
	}

	public void loadTraining(String contextPath) {
		BufferedReader in = null;
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

					LocalDateTime dateTimeCheck = LocalDateTimeHelper.stringToDate(st.nextToken().trim());

					int trainingId = Integer.parseInt(st.nextToken().trim());
					Training tr = new Training(trainingId);

					int bID = Integer.parseInt(st.nextToken().trim());
					Korisnik buy = new Korisnik(bID);

					int cID = Integer.parseInt(st.nextToken().trim());
					Korisnik cou = new Korisnik(cID);

					int id = Integer.parseInt(st.nextToken().trim());

					training.put(id, new TrainingHistory(dateTimeCheck, tr, buy, cou, id));
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
			int id = coach.getCoach().getId();
			for (Korisnik user : korisnici) {
				if (user.getId() == id) {
					coach.setCoach(user);
					break;
				}
			}
		}
	}
}
