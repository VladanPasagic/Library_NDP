package org.unibl.etf.mdp.library.entities;

import java.util.Date;
import java.util.UUID;

public class BookReservationEntity {
	private UUID id;
	private UUID bookId;
	private UUID userId;
	private Date date;

	public UUID getId() {
		return id;
	}

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
