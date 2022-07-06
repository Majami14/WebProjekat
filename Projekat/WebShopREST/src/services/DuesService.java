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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Dues;
import beans.ProjectStartup;
import beans.TypeCustomer;
import dao.DuesDAO;
import dao.TypeCustomerDAO;
@Path("/dues")
public class DuesService {
	@Context
	ServletContext ctx;
	
	public DuesService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("duesDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("duesDAO", DuesDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Dues> getDues() {
		DuesDAO dao = (DuesDAO) ctx.getAttribute("duesDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Dues newCustomer(Dues dues) {
		DuesDAO dao = (DuesDAO) ctx.getAttribute("duesDAO");
		return dao.save(dues);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Dues findOne(@PathParam("id") int id) {
		DuesDAO dao = (DuesDAO) ctx.getAttribute("duesDAO");
		return dao.find(id);
	}
	
	// rest/products/search?name=Proizvod2
	/*@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Location search(@QueryParam("name") String city) {
		LocationDAO dao = (LocationDAO) ctx.getAttribute("locationDAO");
		return dao.findAll().stream()
				.filter(location -> location.getCity().equals(city))
				.findFirst()
				.orElse(null);
	} */
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Dues changeOne(Dues dues, @PathParam("id") int id) {
		DuesDAO dao = (DuesDAO) ctx.getAttribute("duesDAO");
		return dao.change(dues);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Dues deleteDues(@PathParam("id") int id) {
		DuesDAO dao = (DuesDAO) ctx.getAttribute("duesDAO");
		return dao.delete(id);
	}
}
