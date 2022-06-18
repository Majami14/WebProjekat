package beans;

import dao.CommentDAO;
import dao.LocationDAO;
import dao.SportsFacilityDAO;
import dao.TypeCustomerDAO;

public class ProjectStartup {
	private static ProjectStartup startupInstance = null;
	
	private ProjectStartup(String contextPath) {
		CommentDAO.getInstance().loadComment(contextPath);
		LocationDAO.getInstance().loadLocation(contextPath);
		SportsFacilityDAO.getInstance().loadFacility(contextPath);
		TypeCustomerDAO.getInstance().loadCustomer(contextPath);
		
		CommentDAO.getInstance().connectCommentFacility();
		//CommentDAO.getInstance().getconnectCommentKorisnik();
		
		SportsFacilityDAO.getInstance().connectFacilityLocation();
		
		
	}
	
	
	
	
	public static ProjectStartup getInstance(String contextPath) {
		if(startupInstance==null) {
			startupInstance = new ProjectStartup(contextPath);
		}
		return startupInstance;
	}
}
