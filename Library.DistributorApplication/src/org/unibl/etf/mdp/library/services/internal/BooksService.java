package org.unibl.etf.mdp.library.services.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.unibl.etf.mdp.library.entities.BookEntity;

public class BooksService {

	private static BooksService instance = null;
	private List<BookEntity> booksFromServer = new ArrayList<BookEntity>();
	private List<BookEntity> localBooks = new ArrayList<BookEntity>();

	private BooksService() {

	}

	public static BooksService getBooksService() {
		if (instance == null)
			instance = new BooksService();
		return instance;
	}

	public void setBooks(List<BookEntity> books) {
		this.booksFromServer = books;
	}

	public List<BookEntity> getBooks() {
		List<BookEntity> books = new ArrayList<BookEntity>();
		var bookList = Stream.concat(booksFromServer.stream(), localBooks.stream()).toList();
		for (BookEntity bookEntity : bookList) {
			books.add(bookEntity);
		}
		return books;
	}

	public void addBook(BookEntity book) {
		localBooks.add(book);
	}

}
