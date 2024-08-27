package org.unibl.etf.mdp.library.services;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.helpers.XMLUtils;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IRedisService;

public class RedisService implements IRedisService {
	private final ILoggerService loggerService = LoggerService.getLogger(this.getClass().getName(), true,
			this.getClass().getClassLoader());
	private final IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService);
	private final String CONNECTION_STRING = propertyLoaderService.getProperty("REDIS_SERVER");
	private static IRedisService instance = null;
	private List<BookEntity> books = new ArrayList<BookEntity>();

	private RedisService() {

	}

	public static IRedisService getRedisService() {
		if (instance == null)
			instance = new RedisService();
		return instance;
	}

	@Override
	public List<BookEntity> getBooks() {
		loadBooks();
		return books;
	}

	@Override
	public void saveBooks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadBooks() {

	}

}
