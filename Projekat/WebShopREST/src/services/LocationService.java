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
import javax.ws.rs.core.MediaType;

import beans.Location;
import beans.Product;
import beans.ProjectStartup;
import dao.LocationDAO;
import dao.ProductDAO;
@Path("/location")
public class LocationService {
ServletContext ctx;
	
	public LocationService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("locationDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("locationDAO", new LocationDAO(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Location> getLocations() {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Location newLocation(Location location) {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.save(location);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Location findOne(@PathParam("id") int id) {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.find(id);
	}
	
	// rest/products/search?name=Proizvod2
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Location search(@QueryParam("name") String city) {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.findAll().stream()
				.filter(location -> location.getCity().equals(city))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location changeOne(Location location, @PathParam("id") int id) {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.change(location);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Location deleteLocation(@PathParam("id") int id) {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.delete(id);
	}
}
