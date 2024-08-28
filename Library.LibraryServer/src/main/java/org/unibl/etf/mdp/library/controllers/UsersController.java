package org.unibl.etf.mdp.library.controllers;

import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.repositories.UserRepository;
import org.unibl.etf.mdp.library.repositories.interfaces.IUserRepository;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UsersController {
	private ILoggerService loggerService;
	private IUserRepository userService;
	private IPropertyLoaderService propertyLoaderService;

	public UsersController() {
		loggerService = LoggerService.getLogger(getClass().getName());
		propertyLoaderService = PropertyLoaderService.load(loggerService, true, getClass().getClassLoader());
		userService = UserRepository.getRepository(loggerService, propertyLoaderService);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserEntity> getUsers() {
		return userService.getAll().stream().filter(entity -> entity.isHandled() == true).toList();
	}

	@GET
	@Path("requests")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserEntity> getRequests() {
		return userService.getAll().stream().filter(entity -> entity.isHandled() == false).toList();
	}

	@PATCH
	@Path("requests/{id}/approve")
	public Response approveRequest(@PathParam("id") String id) {
		UserEntity userEntity = userService.find(UUID.fromString(id));
		if (userEntity == null) {
			return Response.status(409).build();
		}
		userEntity.setActive(true);
		userEntity.setHandled(true);
		userEntity.setApproved(true);
		userService.save();
		return Response.ok().build();
	}

	@PATCH
	@Path("requests/{id}/reject")
	public Response rejectRequest(@PathParam("id") String id) {
		UserEntity userEntity = userService.find(UUID.fromString(id));
		if (userEntity == null) {
			return Response.status(409).build();
		}
		userEntity.setHandled(true);
		userService.save();
		return Response.ok().build();
	}

	@PATCH
	@Path("{id}/block")
	public Response blockUser(@PathParam("id") String id) {
		UserEntity userEntity = userService.find(UUID.fromString(id));
		if (userEntity == null) {
			return Response.status(409).build();
		}
		userEntity.setActive(false);
		userService.save();
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") String id) {
		userService.remove(UUID.fromString(id));
		userService.save();
		return Response.ok().build();
	}
}
