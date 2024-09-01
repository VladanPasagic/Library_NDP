package org.unibl.etf.mdp.library.services;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.repositories.UserRepository;
import org.unibl.etf.mdp.library.repositories.interfaces.IUserRepository;
import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;
import org.unibl.etf.mdp.library.responses.RegisterResponse;
import org.unibl.etf.mdp.library.responses.Response;
import org.unibl.etf.mdp.library.services.interfaces.IAuthenticationService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class AuthenticationService implements IAuthenticationService {

	private static IAuthenticationService instance = null;
	private IUserRepository userRepository;

	private AuthenticationService(ILoggerService loggerService, IPropertyLoaderService propertyLoaderService) {
		userRepository = UserRepository.getRepository(loggerService, propertyLoaderService);
	}

	public static IAuthenticationService getInstance(ILoggerService loggerService,
			IPropertyLoaderService propertyLoaderService) {
		if (instance == null)
			instance = new AuthenticationService(loggerService, propertyLoaderService);
		return instance;
	}

	@Override
	public UserEntity login(LoginRequest loginRequest) {
		UserEntity entity = userRepository.findByUsername(loginRequest.getUsername());
		if (entity == null)
			return null;
		if (entity.isActive() == false)
			return null;
		if (entity.getPassword().equals(loginRequest.getPassword()))
			return entity;
		return null;
	}

	@Override
	public Response register(RegistrationRequest registrationRequest) {
		if (userRepository.findByEmail(registrationRequest.getEmail()) != null
				|| userRepository.findByUsername(registrationRequest.getUsername()) != null) {
			return new Response(false, "Conflict detected");
		}

		userRepository.add(new UserEntity(registrationRequest.getFirstName(), registrationRequest.getLastName(),
				registrationRequest.getAddress(), registrationRequest.getEmail(), registrationRequest.getUsername(),
				registrationRequest.getPassword()));
		return new Response(true, "");
	}

}
