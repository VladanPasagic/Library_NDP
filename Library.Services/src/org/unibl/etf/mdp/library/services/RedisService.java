package org.unibl.etf.mdp.library.services;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IRedisService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisService implements IRedisService {
	private final ILoggerService loggerService = LoggerService.getLogger(this.getClass().getName());
	private final String CONNECTION_STRING;
	private final String param = "BOOKLIST";
	private static IRedisService instance = null;
	private List<BookEntity> books = new ArrayList<BookEntity>();

	private RedisService(IPropertyLoaderService propertyLoaderService) {
		CONNECTION_STRING = propertyLoaderService.getProperty("REDIS_SERVER");
	}

	public static IRedisService getRedisService(IPropertyLoaderService propertyLoaderService) {
		if (instance == null)
			instance = new RedisService(propertyLoaderService);
		return instance;
	}

	@Override
	public List<BookEntity> getBooks() {
		loadBooks();
		return books;
	}

	@Override
	public void saveBooks() {
		JedisPool pool = new JedisPool(CONNECTION_STRING);
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json = gson.toJson(books);
		try {
			Jedis jedis = pool.getResource();
			jedis.del(param);
			jedis.set(param, json);
			jedis.close();
		} catch (Exception e) {
			loggerService.logError("Couldn't find redis resource", e);
		}
		pool.close();

	}

	@Override
	public void loadBooks() {
		books = new ArrayList<BookEntity>();
		JedisPool pool = new JedisPool(CONNECTION_STRING);
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			Jedis jedis = pool.getResource();
			String json = jedis.get(param);
			var books = gson.fromJson(json, BookEntity[].class);
			for (BookEntity bookEntity : books) {
				this.books.add(bookEntity);
			}
			jedis.close();
		} catch (Exception ex) {
			loggerService.logError("Couldn't find redis resource", ex);
		}
		pool.close();
	}

}
