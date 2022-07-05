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

import beans.ProjectStartup;
import beans.SportsFacility;
import dao.SportsFacilityDAO;
@Path("/facility")
public class SportsFacilityService {

	@Context
	ServletContext ctx;
	
	public SportsFacilityService() {
	}
	
	@PostConstruct
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("sportsFacilityDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("sportsFacilityDAO", SportsFacilityDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportsFacility> getFacility() {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SportsFacility newFacility(SportsFacility facility) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.save(facility);
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility findOne(@PathParam("id") int id) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.find(id);     
	} 
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility searchFacility(@QueryParam("name") String name) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.findAll().stream()
				.filter(facility -> facility.getName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility changeFacility(SportsFacility facility, @PathParam("id") String id) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.change(facility);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility deleteFacility(@PathParam("id") int id) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.delete(id);
	}
	
	@GET
	@Path("/searchFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SportsFacility> searchFacilityWith(@QueryParam("searchValue") String searchValue, @QueryParam("criterion") String criterion) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.search(searchValue, criterion);
	}
	
	@POST
	@Path("/setSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setSelected(SportsFacility object, @Context HttpServletRequest request) {
		object = SportsFacilityDAO.getInstance().find(object.getId());
		request.getSession().setAttribute("selected", object);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SportsFacility getSelected( @Context HttpServletRequest request) {
		SportsFacility object = (SportsFacility)request.getSession().getAttribute("selected");
		return object;
	}
	
	
}
