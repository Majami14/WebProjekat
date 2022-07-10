package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Korisnik;
import beans.ProjectStartup;
import beans.SportsFacility;
import beans.Training;
import dao.KorisnikDAO;
import dao.SportsFacilityDAO;
import dao.TrainingDAO;
import dto.TrainingDTO;
@Path("/training")
public class TrainingService {
	@Context
	ServletContext ctx;
	
	public TrainingService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("trainingDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("trainingDAO", TrainingDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Training> getTrainings() {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Training newTraining(Training training) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.save(training);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Training findOne(@PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.find(id);     
	} 
	
	/*@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Training searchTraining(@QueryParam("name") String name) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.findAll().stream()
				.filter(facility -> facility.getName().equals(name))
				.findFirst()
				.orElse(null);
	} */
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Training changeTraining(Training training, @PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.change(training);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Training deleteTraining(@PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.delete(id);
	}
	@GET
	@Path("/getTrainingsForObject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TrainingDTO> getTrainingsForObject(@PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		ArrayList<Training> foundTrainings = dao.getInstance().getTrainingForSportObject(id);
		ArrayList<TrainingDTO> trainingsDTO = new ArrayList<TrainingDTO>();
		for(Training training : foundTrainings) {
			trainingsDTO.add(new TrainingDTO(training));
		}
		return trainingsDTO;
	}
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changeOne(TrainingDTO trainingDTO) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		Training training = new Training();
		training.setId(trainingDTO.getId());
		training.setName(trainingDTO.getName());
		training.setType(trainingDTO.getType());
		training.setSportFacility(trainingDTO.getSportFacility());
		training.setDuration(trainingDTO.getDuration());
		training.setDescription(trainingDTO.getDescription());
		training.setImage(trainingDTO.getImage());
		Korisnik korisnik = KorisnikDAO.getInstance().find(trainingDTO.getCoachID());
		training.setCoach(korisnik);
		
		dao.change(training);
	}
	@POST
	@Path("/setSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setSelected(TrainingDTO object, @Context HttpServletRequest request) {
		Training objectfound = TrainingDAO.getInstance().find(object.getId());
		request.getSession().setAttribute("selectedTraining", objectfound);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TrainingDTO getSelected( @Context HttpServletRequest request) {
		Training object = (Training)request.getSession().getAttribute("selectedTraining");
		return new TrainingDTO(object);
	}
}
