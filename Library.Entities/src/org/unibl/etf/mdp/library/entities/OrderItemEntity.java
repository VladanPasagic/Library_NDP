package org.unibl.etf.mdp.library.entities;

import java.io.Serializable;
import java.util.UUID;

public class OrderItemEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8837733804232325292L;
	private UUID id;
	private UUID bookId;
	private BookEntity book;
	private int quantity;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public OrderItemEntity(UUID bookId, int quantity, BookEntity book) {
		super();
		id = UUID.randomUUID();
		this.bookId = bookId;
		this.quantity = quantity;
		this.book = book;
	}

	public OrderItemEntity() {

	}
}
