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

import beans.Korisnik;
import beans.ProjectStartup;
import dao.KorisnikDAO;

@Path("/korisnik")
public class KorisnikService {
	@Context
	ServletContext ctx;

	public KorisnikService() {
	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("korisnikDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
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
	public Korisnik newKorisnik(Korisnik korisnik) {
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korinsikyDAO");
		return dao.save(korisnik);
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
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik changeFacility(Korisnik korisnik, @PathParam("id") String id) {
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

}