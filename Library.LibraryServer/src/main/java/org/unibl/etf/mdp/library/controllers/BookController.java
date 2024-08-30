package org.unibl.etf.mdp.library.controllers;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.requests.NewBookRequest;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/books")
public class BookController {

	@GET
	public List<BookEntity> getBooks() {
		return new ArrayList<BookEntity>();
	}

	@GET
	@Path("/{id}")
	public Response getBook(@PathParam("id") String id) {
		return Response.status(200).build();
	}

	@POST
	public Response postBook(NewBookRequest request) {
		return Response.ok().build();
	}

	@PUT
	@Path("/{id}")
	public Response putBook(@PathParam("id") String id, NewBookRequest request) {
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteBook(@PathParam("id") String id) {
		return Response.ok().build();
	}
}
