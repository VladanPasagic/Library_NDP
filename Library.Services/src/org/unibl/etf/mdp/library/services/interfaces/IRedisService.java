package org.unibl.etf.mdp.library.services.interfaces;

import java.util.List;

import org.unibl.etf.mdp.library.entities.BookEntity;

public interface IRedisService {
	
	List<BookEntity> getBooks();
	
	void saveBooks();
	
	void loadBooks();
	
}
