package org.unibl.etf.mdp.library.repositories;

import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.entities.UsersEntity;
import org.unibl.etf.mdp.library.helpers.XMLUtils;
import org.unibl.etf.mdp.library.repositories.interfaces.IUserRepository;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class UserRepository implements IUserRepository {

	private static IUserRepository instance = null;
	private final String XML_FILE = "USERS_XML_PATH";
	private UsersEntity users = new UsersEntity();
	private XMLUtils xmlUtils;

	private UserRepository(ILoggerService loggerService, IPropertyLoaderService propertyLoaderService) {
		xmlUtils = new XMLUtils(loggerService, propertyLoaderService);
		load();
	}

	public static IUserRepository getRepository(ILoggerService loggerService,
			IPropertyLoaderService propertyLoaderService) {
		if (instance == null)
			instance = new UserRepository(loggerService, propertyLoaderService);
		return instance;
	}

	@Override
	public void remove(UUID id) {
		int index = findIndex(id);
		if (index != -1) {
			users.getUsers().remove(index);
			save();
		}
	}

	@Override
	public void save() {
		xmlUtils.write(XML_FILE, UsersEntity.class, users);
	}

	@Override
	public void load() {
		users = xmlUtils.read(XML_FILE, UsersEntity.class);
		if (users == null)
			users = new UsersEntity();
	}

	@Override
	public void add(UserEntity item) {
		users.getUsers().add(item);
		save();
	}

	@Override
	public UserEntity get(UUID id) {
		UserEntity user = find(id);
		return user;
	}

	@Override
	public List<UserEntity> getAll() {
		return users.getUsers();
	}

	@Override
	public UserEntity find(UUID id) {
		int index = findIndex(id);
		if (index == -1) {
			return null;
		} else {
			return users.getUsers().get(index);
		}
	}

	@Override
	public int findIndex(UUID id) {
		for (int i = 0; i < users.getUsers().size(); i++) {
			if (users.getUsers().get(i).getId().equals(id.toString()))
				return i;
		}
		return -1;
	}

	@Override
	public UserEntity findByEmail(String email) {
		for (UserEntity userEntity : users.getUsers()) {
			if (userEntity.getEmail().toLowerCase().equals(email.toLowerCase()))
				return userEntity;
		}
		return null;
	}

	@Override
	public UserEntity findByUsername(String username) {
		for (UserEntity userEntity : users.getUsers()) {
			if (userEntity.getUsername().toLowerCase().equals(username.toLowerCase()))
				return userEntity;
		}
		return null;
	}

}
