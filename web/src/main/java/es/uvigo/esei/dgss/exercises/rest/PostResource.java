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
import es.uvigo.esei.dgss.exercises.domain.Link;
import es.uvigo.esei.dgss.exercises.domain.Photo;
import es.uvigo.esei.dgss.exercises.domain.Video;


@Path("post")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {
	
	@EJB
	private PostEJB postEJB;
	
	@Context
	private UriInfo uriInfo;
	
	@GET
	@Path("{id}")
	public Response getPostDetails(@PathParam("id") int id) {
		return Response.ok(this.postEJB.findPostById(id)).build();
	}
	
	@POST
	@Path("video")
	public Response createVideo(Video video) {
		this.postEJB.createVideoREST(video);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(video.getId())).build())
					.build();
	}
	
	@POST
	@Path("photo")
	public Response createPhoto(Photo photo) {
		this.postEJB.createPhotoREST(photo);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(photo.getId())).build())
					.build();
	}
	
	@POST
	@Path("link")
	public Response createLink(Link link) {
		this.postEJB.createLinkREST(link);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(link.getId())).build())
					.build();
	}
	
	@PUT
	@Path("video/{id}")
	public Response updateVideo(@PathParam("id") int id, Video video) {
		this.postEJB.updateVideo(video.getId(), video.getDuration());
		return
				Response.ok(
						uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(video.getId())).build())
						.build();
	}
	
	@PUT
	@Path("photo/{id}")
	public Response updatePhoto(@PathParam("id") int id, Photo photo) {
		this.postEJB.updatePhoto(photo.getId(), photo.getContent());
		return
				Response.ok(
						uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(photo.getId())).build())
						.build();
	}
	
	@PUT
	@Path("link/{id}")
	public Response updateLink(@PathParam("id") int id, Link link) {
		this.postEJB.updateLink(link.getId(), link.getUrl());
		return
				Response.ok(
						uriInfo.getAbsolutePathBuilder()
						.path(Integer.toString(link.getId())).build())
						.build();
	}
	
	@DELETE
	@Path("{id}")
	public Response deletePost(@PathParam("id") int id) {
		this.postEJB.deletePost(id);
		return
				Response.ok().build();
	}
	
	@GET
	@Path("wall")
	public Response getMyWallPosts() {
		this.postEJB.getMyWallPosts();
		return
				Response.ok().build();
	}
	
	@GET
	@Path("post")
	public Response getMyPosts() {
		this.postEJB.getMyPosts();
		return
				Response.ok().build();
	}
	
	@POST
	@Path("post/{id}")
	public Response giveLike(@PathParam("id") int id) {
		this.postEJB.giveLike(id);
		return
				Response.ok().build();
	}
}
