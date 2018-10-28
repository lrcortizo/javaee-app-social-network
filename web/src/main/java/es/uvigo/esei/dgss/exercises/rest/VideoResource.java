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
import es.uvigo.esei.dgss.exercises.domain.Video;


@Path("post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VideoResource {
	
	@EJB
	private PostEJB postEJB;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("{id}")
	public Response getVideoDetails(@PathParam("id") int id) {
		return Response.ok(this.postEJB.findPostById(id)).build();
	}
	
	@POST
	public Response createVideo(Video video) {
		this.postEJB.createVideo(video);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(video.getId())).build())
					.build();
	}
	
	@PUT
	@Path("{id}")
	public Response updateVideo(@PathParam("id") int id, Video video) {
		this.postEJB.updateVideo(video.getId(), video.getDuration());
		return
				Response.ok(
						uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(video.getId())).build())
						.build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteVideo(@PathParam("id") int id) {
		this.postEJB.removePost(id);
		return
				Response.ok().build();
	}
}
