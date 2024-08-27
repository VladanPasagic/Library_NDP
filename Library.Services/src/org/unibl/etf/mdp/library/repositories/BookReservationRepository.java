package org.unibl.etf.mdp.library.repositories;

import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookReservationEntity;
import org.unibl.etf.mdp.library.repositories.interfaces.IBookReservationRepository;

public class BookReservationRepository implements IBookReservationRepository{

	@Override
	public List<BookReservationEntity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookReservationEntity get(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(BookReservationEntity item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BookReservationEntity find(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findIndex(UUID id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean confirm(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deny(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
