package org.unibl.etf.mdp.library.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Users")
public class UsersEntity {

	private List<UserEntity> users;

	public UsersEntity() {
		users = new ArrayList<UserEntity>();
	}

	@XmlElement(name = "User")
	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}
}
