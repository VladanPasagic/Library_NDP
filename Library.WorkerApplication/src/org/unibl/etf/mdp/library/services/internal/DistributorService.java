package org.unibl.etf.mdp.library.services.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.entities.DistributorEntity;
import org.unibl.etf.mdp.library.threads.internal.InternalServerThread;

public class DistributorService {
	private static DistributorService instance = null;
	private Map<DistributorEntity, InternalServerThread> entities = new HashMap<DistributorEntity, InternalServerThread>();
	private List<BookEntity> currentBooks = new ArrayList<BookEntity>();

	private DistributorService() {

	}

	public static DistributorService getDistributorService() {
		if (instance == null)
			instance = new DistributorService();
		return instance;
	}

	public Map<DistributorEntity, InternalServerThread> getDistributors() {
		return entities;
	}

	public void addDistributor(DistributorEntity distributorEntity, InternalServerThread thread) {
		entities.put(distributorEntity, thread);
	}

	public void setCurrentBooks(List<BookEntity> books) {
		currentBooks = books;
	}

	public List<BookEntity> getCurrentBooks() {
		return currentBooks;
	}
}
