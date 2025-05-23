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

import beans.Comment;
import beans.ProjectStartup;
import beans.SportsFacility;
import dao.CommentDAO;

@Path("/comments")

public class CommentService {
	@Context
	ServletContext ctx;

	public CommentService() {
	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("commentDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ProjectStartup.getInstance(contextPath);
			ctx.setAttribute("commentDAO", CommentDAO.getInstance());
		}
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAll();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Comment newComment(Comment comment) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.save(comment);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment findOne(@PathParam("id") int id) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.find(id);
	}

	/*
	 * @GET
	 * 
	 * @Path("/search")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Comment
	 * searchC(@QueryParam("name") String grade) { CommentDAO dao = (CommentDAO)
	 * ctx.getAttribute("ommentDAO"); return dao.findAll().stream() .filter(comment
	 * -> comment.getGrade().equals(grade)) .findFirst() .orElse(null); }
	 */

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment changeComment(Comment comment, @PathParam("id") int id) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.change(comment);
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment deleteComment(@PathParam("id") int id) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.delete(id);
	}

}
