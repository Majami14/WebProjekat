package services;

import java.time.LocalDate;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.DateHelper;
import beans.Dues;
import beans.Korisnik;
import beans.ProjectStartup;
import beans.TypeCustomer;
import dao.DuesDAO;
import dao.KorisnikDAO;
import dao.TypeCustomerDAO;
import dto.DuesDTO;
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
	public DuesDTO newDues(Dues dues,@Context HttpServletRequest request) {
		DuesDAO dao = (DuesDAO) ctx.getAttribute("duesDAO");
		Korisnik logged = (Korisnik) request.getSession().getAttribute("korisnik");
		if(logged == null) {
			return null;
		}
		logged.setDues(dues);
		dues.setBuyer(logged);
		dues = dao.newDuesAdded(dues);
		KorisnikDAO.getInstance().change(logged);
		return new DuesDTO(dues);
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
	@POST
	@Path("/setSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setSelected(Dues dues, @Context HttpServletRequest request) {
		request.getSession().setAttribute("selectedDues", dues);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DuesDTO getSelected( @Context HttpServletRequest request) {
		Dues object = (Dues)request.getSession().getAttribute("selectedDues");
		if(object == null) {
			return null;
		}
		object.setPaymentDate(DateHelper.dateToString( LocalDate.now()));
		return new DuesDTO(object);
	}

	
}
