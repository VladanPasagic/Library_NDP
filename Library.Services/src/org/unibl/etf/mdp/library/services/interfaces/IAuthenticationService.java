package org.unibl.etf.mdp.library.services.interfaces;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;

public interface IAuthenticationService {
	
	UserEntity login(LoginRequest loginRequest);

	boolean register(RegistrationRequest registrationRequest);
}
