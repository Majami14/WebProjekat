package dao;

import beans.DateHelper;
import beans.Dues;
import beans.DuesType;
import beans.Korisnik;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DuesDAO {
	private Map<Integer, Dues> dues = new HashMap<>();
	private static DuesDAO duesInstance = null;
	private DuesDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo
	 *                    iz servleta.
	 */
	private DuesDAO(String contextPath) {
		loadDues(contextPath);
	}
	public static DuesDAO getInstance() {
		if (duesInstance == null) {
			duesInstance = new DuesDAO();
		}
		return duesInstance;
	}
	/**
	 * Vraća korisnika za prosleđeno korisničko ime i šifru. Vraća null ako korisnik
	 * ne postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	/*
	 * public Membership find(String user_name, String user_password) { if
	 * (!memberships.containsKey(user_name)) { return null; } Membership membership
	 * = memberships.get(user_name); if
	 * (!membership.getUser_password().equals(user_password)) { return null; }
	 * return membership; }
	 */

	public Collection<Dues> findAll() {
		return dues.values();
	}

	public Dues save(Dues duess) {
		Integer maxId = -1;
		for (int id : dues.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		duess.setId(maxId);
		dues.put(duess.getId(), (Dues) dues);
		return duess;
	}

	/**
	 * Učitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Ključ je korisničko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadDues(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/memberships.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					
//String idDues, int id, DuesType type, LocalDate paymentDate, LocalDate validationDateTime,
		//Double price,Korisnik buyer, int trainingNumbers	
					String duesId = st.nextToken().trim();
				
					int id  =Integer.parseInt(st.nextToken().trim());
					
					int status_type = Integer.parseInt(st.nextToken().trim()); // Za enum jel dobro?
					DuesType[] status_types = DuesType.values();
					DuesType status_typeFromFile = status_types[status_type];
					
					
					LocalDate paymentday = DateHelper.stringToDate(st.nextToken().trim());
					LocalDate validationDateTime = DateHelper.stringToDate(st.nextToken().trim());
					double price  =Double.parseDouble(st.nextToken().trim());
					
					int buyerId = Integer.parseInt(st.nextToken().trim());
					Korisnik kor = new Korisnik(buyerId);
					
					int trainingNumbers = Integer.parseInt(st.nextToken().trim());
					
					
				

					dues.put(id,new Dues(duesId, id, status_typeFromFile, paymentday,validationDateTime, price,kor,trainingNumbers));
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

	public Dues change(Dues duess) {
		dues.put(duess.getId(), duess);
		return duess;
	}

	public Dues delete(int id) {
		return dues.remove(id);
	}

}
