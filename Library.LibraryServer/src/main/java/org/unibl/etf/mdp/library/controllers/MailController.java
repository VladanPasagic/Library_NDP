package org.unibl.etf.mdp.library.controllers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mail")
public class MailController {
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response sendMail() {
		return Response.ok().build();
	}
}
