package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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

import beans.ProjectStartup;
import beans.TrainingHistory;
import dao.TrainingHistoryDAO;

@Path("/history")
public class TrainingHistoryService {
	@Context
	ServletContext ctx;

	public TrainingHistoryService() {
	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO(contextPath));
		}
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TrainingHistory> getTrainingHistory() {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.findAll();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TrainingHistory newHistory(TrainingHistory training) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.save(training);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingHistory findOne(@PathParam("id") int id) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.find(id);
	}
	/*
	 * @GET
	 * 
	 * @Path("/search")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public TrainingHistory
	 * searchHistory(@QueryParam("name") String name) { TrainingHistoryDAO dao =
	 * (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO"); return
	 * dao.findAll().stream() .filter(history -> history.getName().equals(name))
	 * .findFirst() .orElse(null); }
	 */

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingHistory changeHistory(TrainingHistory training, @PathParam("id") int id) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.change(training);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingHistory deleteHistory(@PathParam("id") int id) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("TrainingHistoryDAO");
		return dao.delete(id);
	}

}