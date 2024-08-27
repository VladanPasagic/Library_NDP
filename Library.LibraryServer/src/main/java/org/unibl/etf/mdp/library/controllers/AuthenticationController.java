package org.unibl.etf.mdp.library.controllers;

import org.unibl.etf.mdp.library.services.AuthenticationService;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IAuthenticationService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

import java.io.File;
import java.net.URISyntaxException;

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

	public AuthenticationController() {
		loggerService = LoggerService.getLogger(getClass().getName(), true, getClass().getClassLoader());
		authenticationService = AuthenticationService.getInstance(loggerService);
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest request) {
		boolean result = authenticationService.login(request);
		if (result)
			return null;
		else {

			return null;
		}
	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(RegistrationRequest request) {
		boolean result = authenticationService.register(request);
		if (result)
			return null;
		else {
			return null;
		}
	}

	@GET
	public String something() {
		return "something";
	}
}
