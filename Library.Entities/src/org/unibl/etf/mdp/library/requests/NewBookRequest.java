package org.unibl.etf.mdp.library.requests;

public class NewBookRequest {
	private String ISBN;
	private String name;
	private String author;
	private String frontPageLink;
	private String releaseDate;
	private String language;
	private String contentPath;

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

	public String getContentPath() {
		return contentPath;
	}

	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}

	public NewBookRequest(String iSBN, String name, String author, String frontPageLink, String releaseDate,
			String language, String contentPath) {
		super();
		ISBN = iSBN;
		this.name = name;
		this.author = author;
		this.frontPageLink = frontPageLink;
		this.releaseDate = releaseDate;
		this.language = language;
		this.contentPath = contentPath;
	}

	public NewBookRequest() {
		super();
	}

}
