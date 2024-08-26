package org.unibl.etf.mdp.library.services;

import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;
import org.unibl.etf.mdp.library.services.interfaces.IAuthenticationService;

public class AuthenticationService implements IAuthenticationService{

	private static IAuthenticationService instance = null;
	
	private AuthenticationService() {
	}
	
	public static IAuthenticationService getInstance() {
		if (instance == null)
			instance = new AuthenticationService();
		return instance;
	}
	
	@Override
	public boolean login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(RegistrationRequest registrationRequest) {
		// TODO Auto-generated method stub
		return false;
	}

}
