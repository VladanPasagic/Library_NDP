package org.unibl.etf.mdp.library.services.interfaces;

import org.unibl.etf.mdp.library.entities.BookEntity;

public interface IGutenbergService {
	BookEntity getBook(String url);
}
