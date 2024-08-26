package org.unibl.etf.mdp.library.repositories.interfaces;

import org.unibl.etf.mdp.library.entities.BookReservationEntity;

public interface IBookReservationRepository extends IGenericRepository<BookReservationEntity>{
	boolean confirm(int id);
	
	boolean deny(int id);
}
