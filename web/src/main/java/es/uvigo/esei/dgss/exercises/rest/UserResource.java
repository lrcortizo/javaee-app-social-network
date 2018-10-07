package es.uvigo.esei.dgss.exercises.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.uvigo.esei.dgss.exercise.service.UserEJB;
import es.uvigo.esei.dgss.exercises.domain.User;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@EJB
	private UserEJB userEjb;

	@Context
	private UriInfo uriInfo;
	
	@POST
	public Response createUser(User user) {
		this.userEjb.createUser(user);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(user.getLogin()).build())
					.build();

		// 201 Created
		// Location: http://localhost......./api/user/{login}
	}
	
	@GET
	@Path("{login}")
	public Response getUserDetails(@PathParam("login") String login) {
		return Response.ok(this.userEjb.findUserById(login)).build();
	}
}
