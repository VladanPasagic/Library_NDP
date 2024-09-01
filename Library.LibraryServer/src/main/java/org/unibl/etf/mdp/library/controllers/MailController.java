package org.unibl.etf.mdp.library.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.helpers.ZipUtils;
import org.unibl.etf.mdp.library.repositories.BookRepository;
import org.unibl.etf.mdp.library.repositories.UserRepository;
import org.unibl.etf.mdp.library.repositories.interfaces.IBookRepository;
import org.unibl.etf.mdp.library.repositories.interfaces.IUserRepository;
import org.unibl.etf.mdp.library.requests.MultipleBookRequest;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.MailSendService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMailSendService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mail")
public class MailController {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, true,
			getClass().getClassLoader());
	private IMailSendService mailSendService = MailSendService.getInstance(propertyLoaderService);
	private IUserRepository userRepository = UserRepository.getRepository(loggerService, propertyLoaderService);
	private IBookRepository bookRepository = BookRepository.getRepository(propertyLoaderService);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response sendMail(@PathParam("id") String id, MultipleBookRequest bookRequest) {
		UserEntity user = userRepository.find(UUID.fromString(id));
		if (user == null)
			return Response.status(400).entity(Entity.entity(false, MediaType.APPLICATION_JSON)).build();
		List<String> files = new ArrayList<String>();
		StringBuilder content = new StringBuilder();
		for (String bookId : bookRequest.getIds()) {
			BookEntity book = bookRepository.find(UUID.fromString(bookId));
			files.add(book.getContentPath());
			content.append(book + "\n");
		}
		File file = ZipUtils.zipFiles(user.getFirstName() + "_" + user.getLastName() + ".zip", files);
		mailSendService.sendMail(user.getEmail(), propertyLoaderService.getProperty("MAIL_SENDER"), "Online library",
				content.toString(), file);
		return Response.status(200).entity(Entity.entity(true, MediaType.APPLICATION_JSON)).build();
	}
}
