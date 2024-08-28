package org.unibl.etf.mdp.library.entities;

import java.util.UUID;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "User")
@XmlType(propOrder = { "id", "firstName", "lastName", "address", "email", "username", "password", "active", "handled",
		"approved" })
public class UserEntity {
	private UUID id;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String username;
	private String password;
	private boolean active;
	private boolean handled;
	private boolean approved;

	@XmlElement
	public String getId() {
		return id.toString();
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}

	@XmlElement
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@XmlElement
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@XmlElement
	public boolean isHandled() {
		return handled;
	}

	public void setHandled(boolean handled) {
		this.handled = handled;
	}

	@XmlElement
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public UserEntity() {
		super();
	}

	public UserEntity(String firstName, String lastName, String address, String email, String username,
			String password) {
		super();
		this.id = UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.username = username;
		this.password = password;
		this.active = false;
		this.handled = false;
		this.approved = false;
	}

}
