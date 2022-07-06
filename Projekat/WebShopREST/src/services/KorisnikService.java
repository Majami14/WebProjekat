package services;

import java.util.ArrayList;
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
import javax.ws.rs.core.Response;

import beans.Korisnik;
import beans.ProjectStartup;
import beans.SportsFacility;
import dao.KorisnikDAO;
import dao.SportsFacilityDAO;

@Path("/korisnik")
public class KorisnikService {
	@Context
	ServletContext ctx;

	public KorisnikService() {
	}

	@PostConstruct
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("korisnikDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("korisnikDAO", KorisnikDAO.getInstance());
		}
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Korisnik> getKorisnik() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.findAll();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Korisnik newObject(Korisnik korisnik) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		boolean retVal = dao.existsUsername(korisnik.getUserName());
		if(retVal) {
			return null;		
		}
		Korisnik korisnik1=dao.save(korisnik);
		return korisnik1;
		
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik findOne(@PathParam("id") int id) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.find(id);
	}

	/*
	 * @GET
	 * 
	 * @Path("/search")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Korisnik
	 * searchKorisnik(@QueryParam("name") String name) { KorisnikDAO dao =
	 * (KorisnikDAO) ctx.getAttribute("korisnikDAO"); return dao.findAll().stream()
	 * .filter(korisnik -> korisnik.getUserName().equals(name)) .findFirst()
	 * .orElse(null); }
	 */
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik changeFacility(Korisnik korisnik) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.change(korisnik);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik deleteKorisnik(@PathParam("id") int id) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.delete(id);
	}

	@GET
	@Path("/freeManagers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Korisnik> getFreeManagers() {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		return dao.getAllFreeManagers();
	}
	
}