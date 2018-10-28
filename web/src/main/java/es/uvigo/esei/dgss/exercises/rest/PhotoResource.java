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

import es.uvigo.esei.dgss.exercise.service.PostEJB;
import es.uvigo.esei.dgss.exercises.domain.Photo;

@Path("post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PhotoResource {
	
	@EJB
	private PostEJB postEJB;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("{id}")
	public Response getPhotoDetails(@PathParam("id") int id) {
		return Response.ok(this.postEJB.findPostById(id)).build();
	}
	
	@POST
	public Response createPhoto(Photo photo) {
		this.postEJB.createPhoto(photo);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(photo.getId())).build())
					.build();
	}
	
	@PUT
	@Path("{id}")
	public Response updatePhoto(@PathParam("id") int id, Photo photo) {
		this.postEJB.updatePhoto(photo.getId(), photo.getContent());
		return
				Response.ok(
						uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(photo.getId())).build())
						.build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deletePhoto(@PathParam("id") int id) {
		this.postEJB.removePost(id);
		return
				Response.ok().build();
	}

}
