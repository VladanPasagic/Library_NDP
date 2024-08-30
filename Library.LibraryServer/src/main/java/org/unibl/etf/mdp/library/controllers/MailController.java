package org.unibl.etf.mdp.library.controllers;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.MailSendService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMailSendService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mail")
public class MailController {
	
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, true, getClass().getClassLoader());
	private IMailSendService mailSendService = MailSendService.getInstance(propertyLoaderService);
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response sendMail() {
		return Response.ok().build();
	}
}
