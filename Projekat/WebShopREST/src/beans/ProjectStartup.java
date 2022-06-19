package beans;

import dao.CommentDAO;
import dao.DuesDAO;
import dao.KorisnikDAO;
import dao.LocationDAO;
import dao.SportsFacilityDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
import dao.TypeCustomerDAO;

public class ProjectStartup {
	private static ProjectStartup startupInstance = null;
	
	private ProjectStartup(String contextPath) {
		CommentDAO.getInstance().loadComment(contextPath);
		DuesDAO.getInstance().loadDues(contextPath);
		KorisnikDAO.getInstance().loadKorisnik(contextPath);
		LocationDAO.getInstance().loadLocation(contextPath);
		SportsFacilityDAO.getInstance().loadFacility(contextPath);
		TrainingDAO.getInstance().loadTraining(contextPath);
		TrainingHistoryDAO.getInstance().loadTraining(contextPath);
		TypeCustomerDAO.getInstance().loadCustomer(contextPath);
		
		CommentDAO.getInstance().connectCommentFacility();
		//CommentDAO.getInstance().getconnectCommentKorisnik();
		
		SportsFacilityDAO.getInstance().connectFacilityLocation();
		

		TrainingDAO.getInstance().connectTrainingSportsFacility();
		TrainingDAO.getInstance().connectTrainingCoach();
		
		

		KorisnikDAO.getInstance().connectKorisnikDues();

		KorisnikDAO.getInstance().linkUserAndVisitedObject(contextPath);
		KorisnikDAO.getInstance().connectKorisnikSportsFacility();
		KorisnikDAO.getInstance().connectKorisnikTypeCustomer();
		
		TrainingHistoryDAO.getInstance().connectTrainingHistoryTraining();
		TrainingHistoryDAO.getInstance().connectBuyerKorisnik();
		TrainingHistoryDAO.getInstance().connectCoachKorisnik();

	}
	
	
	
	
	public static ProjectStartup getInstance(String contextPath) {
		if(startupInstance==null) {
			startupInstance = new ProjectStartup(contextPath);
		}
		return startupInstance;
	}
}
