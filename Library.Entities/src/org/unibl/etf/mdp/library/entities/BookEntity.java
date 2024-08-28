package org.unibl.etf.mdp.library.entities;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class BookEntity {
	private UUID id;
	private String ISBN;
	private String name;
	private String author;
	private String frontPageLink;
	private String releaseDate;
	private String language;
	private File content;

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

	public File getContent() {
		return content;
	}

	public void setContent(File content) {
		this.content = content;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
