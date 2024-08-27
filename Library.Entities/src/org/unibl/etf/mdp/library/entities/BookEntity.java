package org.unibl.etf.mdp.library.entities;

import java.util.UUID;

public class BookEntity {
	private UUID id;
	private String ISBN;
	private String name;
	private String author;

	public UUID getId() {
		return id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
