package org.unibl.etf.mdp.library.repositories.interfaces;

import org.unibl.etf.mdp.library.entities.UserEntity;

public interface IUserRepository extends IGenericRepository<UserEntity> {

	UserEntity findByEmail(String email);

	UserEntity findByUsername(String username);
}
