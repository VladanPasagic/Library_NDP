package org.unibl.etf.mdp.library.repositories.interfaces;

import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookReservationEntity;

public interface IBookReservationRepository extends IGenericRepository<BookReservationEntity> {
	boolean confirm(UUID id);

	boolean deny(UUID id);
}
