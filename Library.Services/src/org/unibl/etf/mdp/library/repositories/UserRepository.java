package org.unibl.etf.mdp.library.repositories;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.repositories.interfaces.IUserRepository;

public class UserRepository implements IUserRepository{

	private static String FILE_PATH = "";
	private List<UserEntity> users = new ArrayList<UserEntity>();
	
	@Override
	public void remove(int id) {
		int index = findIndex(id);
		if (index !=-1)
		{
			users.remove(index);
		}
		save();
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(UserEntity item) {
		users.add(item);
		save();
	}

	@Override
	public UserEntity get(int id) {
		UserEntity user = find(id);
		return user;
	}

	@Override
	public List<UserEntity> getAll() {
		load();
		return users;
	}

	@Override
	public UserEntity find(int id) {
		int index = findIndex(id);
		if (index == -1)
		{
			return null;
		}
		else {
			return users.get(index);
		}
	}

	@Override
	public int findIndex(int id) {
		for(int i = 0;i<users.size();i++)
		{
			if (users.get(i).getId()==id)
				return i;
		}
		return -1;
	}

}
