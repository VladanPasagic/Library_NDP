package org.unibl.etf.mdp.library.services.interfaces;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;
import org.unibl.etf.mdp.library.responses.RegisterResponse;
import org.unibl.etf.mdp.library.responses.Response;

public interface IAuthenticationService {
	
	UserEntity login(LoginRequest loginRequest);

	Response register(RegistrationRequest registrationRequest);
}
