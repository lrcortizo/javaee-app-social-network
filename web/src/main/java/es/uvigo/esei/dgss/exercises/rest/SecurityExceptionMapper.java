package es.uvigo.esei.dgss.exercises.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class SecurityExceptionMapper implements ExceptionMapper<SecurityException>{
	
	public Response toResponse(SecurityException e){
		return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
	}

}
