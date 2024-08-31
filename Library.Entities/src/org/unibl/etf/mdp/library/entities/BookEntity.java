package org.unibl.etf.mdp.library.entities;

import java.io.Serializable;
import java.util.UUID;

public class BookEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9169527981996260602L;
	private UUID id;
	private String ISBN;
	private String name;
	private String author;
	private String frontPageLink;
	private String releaseDate;
	private String language;
	private String contentPath;

	public UUID getId() {
		return id;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
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

	public String getFrontPageLink() {
		return frontPageLink;
	}

	public void setFrontPageLink(String frontPageLink) {
		this.frontPageLink = frontPageLink;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContentPath() {
		return contentPath;
	}

	public void setContent(String contentPath) {
		this.contentPath = contentPath;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BookEntity() {
		super();
	}

	public BookEntity(String ISBN, String name, String author, String frontPageLink, String releaseDate,
			String language, String contentPath) {
		super();
		this.id = UUID.randomUUID();
		this.ISBN = ISBN;
		this.name = name;
		this.author = author;
		this.frontPageLink = frontPageLink;
		this.releaseDate = releaseDate;
		this.language = language;
		this.contentPath = contentPath;
	}

	@Override
	public String toString() {
		return name + ", " + author;
	}

}
