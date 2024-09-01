package org.unibl.etf.mdp.library.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8203273151158995473L;
	private String content;
	private LocalDateTime dateTime;
	private String sender;
	private UUID senderId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public UUID getSenderId() {
		return senderId;
	}

	public void setSenderId(UUID id) {
		senderId = id;
	}

	public MessageEntity() {
		super();
	}

	public MessageEntity(String content, LocalDateTime dateTime, String sender, UUID id) {
		super();
		this.content = content;
		this.dateTime = dateTime;
		this.sender = sender;
		senderId = id;
	}

}
