package org.unibl.etf.mdp.library.repositories.interfaces;

import java.util.List;

public interface IGenericRepository<T> {

	List<T> getAll();

	T get(int id);

	void add(T item);

	void remove(int id);

	void save();

	void load();

	T find(int id);

	int findIndex(int id);
}
