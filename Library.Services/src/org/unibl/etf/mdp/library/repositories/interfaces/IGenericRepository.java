package org.unibl.etf.mdp.library.repositories.interfaces;

import java.util.List;
import java.util.UUID;

public interface IGenericRepository<T> {

	List<T> getAll();

	T get(UUID id);

	void add(T item);

	void remove(UUID id);

	void save();

	void load();

	T find(UUID id);

	int findIndex(UUID id);
}
