package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


import beans.Status;
import beans.User;
import beans.Comment;
import beans.DateHelper;
import beans.Location;

public class LocationDAO {
	private static LocationDAO Instance = null;

	private Map<Integer, Location> locations = new HashMap<>();
	
	
	private LocationDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. MoÅ¾e se pristupiti samo iz servleta.
	 */
	public LocationDAO(String contextPath) {
		loadLocation(contextPath);
	}
	
	public Location find(int id) {
		return locations.get(id);
	}
	
	public static LocationDAO getInstance() {
		if(Instance == null) {
			Instance = new LocationDAO();
		}
		return Instance;
	}
	/**
	 * VraÄ‡a korisnika za prosleÄ‘eno korisniÄ�ko ime i Å¡ifru. VraÄ‡a null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	/*public Membership find(String user_name, String user_password) {
		if (!memberships.containsKey(user_name)) {
			return null;
		}
		Membership membership = memberships.get(user_name);
		if (!membership.getUser_password().equals(user_password)) {
			return null;
		}
		return membership;
	}*/
	
	public Collection<Location> findAll() {
		return locations.values();
	}
	
	
	public Location save(Location location) {
		Integer maxId = -1;
		for (int id : locations.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		location.setId(maxId);
		locations.put(location.getId(), location);
		return location;
	}
	
	/**
	 * UÄ�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * KljuÄ� je korisniÄ�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadLocation(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/location.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

					int id  =Integer.parseInt(st.nextToken().trim());
					double length  =Double.parseDouble(st.nextToken().trim());
					double width  =Double.parseDouble(st.nextToken().trim());
					String street = st.nextToken().trim();
					String number = st.nextToken().trim();
					String city = st.nextToken().trim();
					String post = st.nextToken().trim();
					
					//locations.put(id,length,width,street,number,city,post);
					locations.put(id, new Location(id, length, width, street, number, city,post));
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
	public Location change(Location location) {
		locations.put(location.getId(), location);
		return location;
	}
	
	public Location delete(int id) {
		return locations.remove(id);
	}
	
}
