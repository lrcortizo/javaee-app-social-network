package es.uvigo.esei.dgss.exercises.rest;

import javax.ejb.EJB;
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
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.uvigo.esei.dgss.exercise.service.UserEJB;
import es.uvigo.esei.dgss.exercises.domain.User;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@EJB
	private UserEJB userEJB;

	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("{login}")
	public Response getUserDetails(@PathParam("login") String login) {
		return Response.ok(this.userEJB.findUserById(login)).build();
	}
	
	@POST
	public Response createUser(User user) {
		this.userEJB.createUser(user);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(user.getLogin()).build())
					.build();

		// 201 Created
		// Location: http://localhost......./api/user/{login}
	}
	
	@PUT
	@Path("{login}")
	public Response updateUser(@PathParam("login") String login, User user) {
		this.userEJB.updateUser(user.getLogin(), user.getName(), user.getPassword(), user.getPicture(), user.getEmail());
		return
				Response.ok(
						uriInfo.getAbsolutePathBuilder()
							.path(user.getLogin()).build())
						.build();
	}
	
	@DELETE
	@Path("{login}")
	public Response deleteUser(@PathParam("login") String login) {
		this.userEJB.deleteUser(login);
		return
				Response.ok().build();
	}
	
	@POST
	@Path("friends/{login}")
	public Response requestFriendship(@PathParam("login") String login) {
		this.userEJB.requestFriendship(login);
		return
				Response.ok().build();
	}
	
	@GET
	@Path("friends")
	public Response getFriendshipRequests() {
		return Response.ok(this.userEJB.getFrienshipRequests()).build();
	}
	
	@PUT
	@Path("friends/{login}")
	public Response acceptFriendship(@PathParam("login") String login) {
		this.userEJB.acceptFriendship(login);
		return
				Response.ok().build();
	}
	
}
