package org.unibl.etf.mdp.library.repositories;

import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.repositories.interfaces.IBookRepository;
import org.unibl.etf.mdp.library.services.RedisService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IRedisService;

public class BookRepository implements IBookRepository {

	private static IBookRepository instance = null;
	private IRedisService redisService;
	private List<BookEntity> books;

	private BookRepository(IPropertyLoaderService propertyLoaderService) {
		this.redisService = RedisService.getRedisService(propertyLoaderService);
		load();
	}

	public static IBookRepository getRepository(IPropertyLoaderService propertyLoaderService) {
		if (instance == null)
			instance = new BookRepository(propertyLoaderService);
		return instance;
	}

	@Override
	public List<BookEntity> getAll() {
		return books;
	}

	@Override
	public BookEntity get(UUID id) {
		BookEntity book = find(id);
		return book;
	}

	@Override
	public void add(BookEntity item) {
		books.add(item);
		save();
	}

	@Override
	public void remove(UUID id) {
		int index = findIndex(id);
		if (index != -1) {
			books.remove(index);
			save();
		}
	}

	@Override
	public void save() {
		redisService.saveBooks();
	}

	@Override
	public void load() {
		redisService.loadBooks();
		books = redisService.getBooks();
	}

	@Override
	public BookEntity find(UUID id) {
		int index = findIndex(id);
		if (index == -1) {
			return null;
		} else {
			return books.get(index);
		}
	}

	@Override
	public int findIndex(UUID id) {
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getId().equals(id))
				return i;
		}
		return -1;
	}

}
