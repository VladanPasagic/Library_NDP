package org.unibl.etf.mdp.library.services.interfaces;

import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;

public interface IAuthenticationService {
	
	boolean login(LoginRequest loginRequest);

	boolean register(RegistrationRequest registrationRequest);
}
