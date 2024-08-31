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
	private String bookName;
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

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public OrderItemEntity(UUID bookId, int quantity, String bookName) {
		super();
		id = UUID.randomUUID();
		this.bookId = bookId;
		this.quantity = quantity;
		this.bookName = bookName;
	}

	public OrderItemEntity() {

	}
}
