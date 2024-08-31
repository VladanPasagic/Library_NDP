package org.unibl.etf.mdp.library.services.internal;

import java.util.List;

import org.unibl.etf.mdp.library.entities.BookEntity;

public class BooksService {

	private static BooksService instance = null;
	private List<BookEntity> books;

	private BooksService() {

	}

	public static BooksService getBooksService() {
		if (instance == null)
			instance = new BooksService();
		return instance;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}

	public List<BookEntity> getBooks() {
		return books;
	}

}
