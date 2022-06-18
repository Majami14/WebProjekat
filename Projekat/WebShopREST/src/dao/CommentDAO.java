package dao;

import beans.Comment;
import beans.Location;
import beans.SportsFacility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CommentDAO {
	private static CommentDAO Instance = null;
	private Map<Integer, Comment> comments = new HashMap<>();
	
	private CommentDAO() {
		
	}
	
	private CommentDAO(String contextPath) {
		loadComment(contextPath);
	}
	
	public Collection<Comment> findAll() {
		return comments.values();
	}
	
	public static CommentDAO getInstance() {
		if(Instance == null) {
			Instance = new CommentDAO();
		}
		return Instance;
	}
	

	public Comment find(int id) {
		return comments.get(id);
	}
	
	public Comment save(Comment comment) {
		Integer maxId = -1;
		for (int id : comments.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		comment.setId(maxId);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public void loadComment(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/comments.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String comment = st.nextToken().trim();
					int id  =Integer.parseInt(st.nextToken().trim());
					int sportFacilityId = Integer.parseInt(st.nextToken().trim());
					SportsFacility facility = new SportsFacility(sportFacilityId);
					int grade  =Integer.parseInt(st.nextToken().trim());
					
					//comments.put(id,sportFacilityId,facility,grade);
	
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
	
	public Comment change(Comment comment) {
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment delete(int id) {
		return comments.remove(id);
	}
	
	public void connectCommentFacility() {
		ArrayList<SportsFacility> facilitys = (ArrayList<SportsFacility>) SportsFacilityDAO.getInstance().findAll();
		for(Comment comment : comments.values()) {
			int id = comment.getFacility().getId();
			
			for(SportsFacility facility : facilitys) {
				if(facility.getId()== id) {
					comment.setFacility(facility);
				}
			}
		}
	}
	
	/*public void connectCommentKorisnik() {
		ArrayList<Korisnik> facilitys = (ArrayList<Korisnik>) KorisnikDAO.getInstance().findAll();
		for(Comment comment : comments.values()) {
			int id = comment.getKorisnik().getId();
			
			for(Korisnik korisnik : korisnici) {
				if(korisnik.getId()== id) {
					comment.setFacility(korisnik);
				}
			}
		} 
	} */
}
