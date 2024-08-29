package org.unibl.etf.mdp.library.controllers;

import org.unibl.etf.mdp.library.services.AuthenticationService;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IAuthenticationService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationController {
	private ILoggerService loggerService;
	private IAuthenticationService authenticationService;
	private IPropertyLoaderService propertyLoaderService;

	public AuthenticationController() {
		loggerService = LoggerService.getLogger(getClass().getName());
		propertyLoaderService = PropertyLoaderService.load(loggerService, true, getClass().getClassLoader());
		authenticationService = AuthenticationService.getInstance(loggerService, propertyLoaderService);
	}

	@GET
	@Path("none")
	public String none() {
		return "none";
	}

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest request) {
		boolean result = authenticationService.login(request);
		if (result) {
			return Response.status(200).build();
		} else {
			return Response.status(401).build();
		}

	}

	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(RegistrationRequest request) {
		System.out.println(request.getEmail());
		boolean result = authenticationService.register(request);
		if (result) {
			return Response.status(200).build();
		} else {
			return Response.status(409).build();
		}
	}

	@GET
	public String something() {
		return "something";
	}
}
