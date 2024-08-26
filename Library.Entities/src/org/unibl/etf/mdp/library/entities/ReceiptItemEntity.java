package org.unibl.etf.mdp.library.entities;

public class ReceiptItemEntity {
	private int id;
	private BookEntity book;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

}
