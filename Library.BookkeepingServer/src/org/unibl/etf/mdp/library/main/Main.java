package org.unibl.etf.mdp.library.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.unibl.etf.mdp.library.services.BookKeepingService;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IBookkeepingService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class Main {

	private static ILoggerService loggerService = LoggerService.getLogger(Main.class.getName());
	private static IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false,
			null);

	public static void main(String args[]) {
		System.setProperty("java.security.policy", propertyLoaderService.getProperty("POLICY_FILE"));
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			BookKeepingService service = new BookKeepingService();
			IBookkeepingService stub = (IBookkeepingService) UnicastRemoteObject.exportObject(service, 0);
			Registry registry = LocateRegistry
					.createRegistry(Integer.parseInt(propertyLoaderService.getProperty("REGISTRY_PORT")));
			registry.rebind(propertyLoaderService.getProperty("RMI_NAME"), stub);
		} catch (Exception e) {
			loggerService.logError("Errors occurred while setting up RMI", e);
		}
	}
}
