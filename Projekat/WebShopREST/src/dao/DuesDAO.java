package dao;

import beans.Comment;
import beans.DateHelper;
import beans.Dues;
import beans.DuesStatus;
import beans.DuesType;
import beans.Korisnik;
import beans.SportsFacility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DuesDAO {
	private Map<Integer, Dues> dues = new HashMap<>();
	private static DuesDAO duesInstance = null;
	private static String contextPath = "";


	private DuesDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. MoÅ¾e se pristupiti samo
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
	 * VraÄ‡a korisnika za prosleÄ‘eno korisniÄ�ko ime i Å¡ifru. VraÄ‡a null ako
	 * korisnik ne postoji
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

	public Dues find(int id) {
		return dues.get(id);
	}
	
	public Collection<Dues> findAll() {
		return dues.values();
	}

	public Dues newDuesAdded(Dues dues) {
		if(dues.getType() == DuesType.DAY) {
			dues.setLastDay(DateHelper.dateToString( dues.getFirstDay().plusDays(1)));
		}else if(dues.getType() == DuesType.MONTH) {
			dues.setLastDay(DateHelper.dateToString( dues.getFirstDay().plusMonths(1)));
		}else {
			dues.setLastDay(DateHelper.dateToString( dues.getFirstDay().plusYears(1)));
		}
		dues.setDuesStatus(DuesStatus.ACTIVE);
		
		dues = save(dues);
		saveToFile();
		
		return dues;
		
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
		dues.put(duess.getId(), duess);
		saveToFile();
		return duess;
	}

	/**
	 * UÄ�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu
	 * {@link #users}. KljuÄ� je korisniÄ�ko ime korisnika.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	

	public void loadDues(String contextPath) {
		BufferedReader in = null;
		this.contextPath = contextPath;
		try {
			File file = new File(contextPath + "/Baza/dues.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

//	private String idDues;
			//		private int id;
				//	private DuesType type;
				//	private LocalDate paymentDate;
					//private LocalDate firstDay;
				//	private LocalDate lastDay;
					//private Double price;
					//private Korisnik buyer;
					//private DuesStatus duesStatus;
					//private int trainingNumbers;
					
					
					
					int id = Integer.parseInt(st.nextToken().trim());
					String idDues = st.nextToken().trim();


					int type = Integer.parseInt(st.nextToken().trim()); 
					DuesType[] tip = DuesType.values();
					DuesType status_typeFromFile = tip[type];

					LocalDate paymentday = DateHelper.stringToDate(st.nextToken().trim());
					LocalDate firstDay = DateHelper.stringToDate(st.nextToken().trim());
					LocalDate lastDay = DateHelper.stringToDate(st.nextToken().trim());
					double price = Double.parseDouble(st.nextToken().trim());

					int buyerId = Integer.parseInt(st.nextToken().trim());
					Korisnik kor = new Korisnik(buyerId);
					
					
					int duesStatus = Integer.parseInt(st.nextToken().trim()); 
					DuesStatus[] type1 = DuesStatus.values();
					DuesStatus dSstatus_typeFromFile = type1[duesStatus];

					int trainingNumbers = Integer.parseInt(st.nextToken().trim());

					dues.put(id, new Dues(idDues, id, status_typeFromFile, paymentday, firstDay, lastDay,price, kor,
							dSstatus_typeFromFile,trainingNumbers));
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

	public Dues change(Dues duess) {
		dues.put(duess.getId(), duess);
		return duess;
	}

	public Dues delete(int id) {
		return dues.remove(id);
	}

	public void saveToFile() {
		BufferedWriter out = null;
		try {

			File file = new File(contextPath + "/Baza/dues.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(Dues duess : dues.values()) {
				out.write(duess.fileLine() + '\n');
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
	}
	
	public void checkDues() {
		for(Dues membership : dues.values()) {
			if(membership.getDuesStatus() == DuesStatus.INACTIVE) {
				continue;
			}
			LocalDate trenutno = LocalDate.now();
			
			int kupljenoTermina = 0;
			if(membership.getType() == DuesType.DAY) {
				kupljenoTermina = 1;
			}else if(membership.getType() == DuesType.MONTH) {
				kupljenoTermina = 30;
			}else {
				kupljenoTermina = 360;
			}
			if(membership.getLastDay().isBefore(trenutno)){
				int iskoristeno = kupljenoTermina - membership.getTrainingNumbers();
				double brojDobijenih = (membership.getPrice() / 1000) * (iskoristeno);
				double izgubljeni = 0;
				if(iskoristeno <= kupljenoTermina / 3) {
					izgubljeni = (membership.getPrice() / 1000) * (133 * 4);
				}
				int poeni = membership.getBuyer().getType().getPoints();
				poeni += (int) Math.round(brojDobijenih - izgubljeni);
				membership.getBuyer().getType().setPoints(poeni);
				membership.setDuesStatus(DuesStatus.INACTIVE);
			}
			
			
		}
	}
}
