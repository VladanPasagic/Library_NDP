package org.unibl.etf.mdp.library.entities;

import java.util.UUID;

public class ReceiptItemEntity{
	private UUID id;
	private BookEntity book;
	private int quantity;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ReceiptItemEntity(BookEntity book, int quantity) {
		super();
		id = UUID.randomUUID();
		this.book = book;
		this.quantity = quantity;
	}

	public ReceiptItemEntity() {

	}

}
