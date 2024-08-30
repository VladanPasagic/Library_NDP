package org.unibl.etf.mdp.library.controllers;

import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.repositories.BookRepository;
import org.unibl.etf.mdp.library.repositories.interfaces.IBookRepository;
import org.unibl.etf.mdp.library.requests.NewBookRequest;
import org.unibl.etf.mdp.library.services.GutenbergService;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IGutenbergService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/books")
public class BookController {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, true,
			getClass().getClassLoader());
	private IBookRepository bookRepository = BookRepository.getRepository(propertyLoaderService);
	private IGutenbergService gutenbergService = GutenbergService.getGutenbergService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookEntity> getBooks() {
		return bookRepository.getAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBook(@PathParam("id") String id) {
		return Response.status(200).entity(bookRepository.get(UUID.fromString(id))).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postBook(NewBookRequest request) {
		BookEntity book = gutenbergService.getBook(request.getContentPath());
		BookEntity bookEntity = new BookEntity(request.getISBN(), request.getName(), request.getAuthor(),
				request.getFrontPageLink(), request.getReleaseDate(), request.getLanguage(), book.getContentPath());
		bookRepository.add(bookEntity);
		return Response.status(201).build();
	}

	@PUT
	@Path("/{id}")
	public Response putBook(@PathParam("id") String id, NewBookRequest request) {
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteBook(@PathParam("id") String id) {
		bookRepository.remove(UUID.fromString(id));
		return Response.ok().build();
	}
}
