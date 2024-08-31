package org.unibl.etf.mdp.library.threads.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import org.unibl.etf.mdp.library.entities.DistributorEntity;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.internal.BooksService;
import org.unibl.etf.mdp.library.services.internal.CurrentLoggedInUserService;

public class ClientThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	@Override
	public void run() {
		try {
			InetAddress address = InetAddress.getByName(propertyLoaderService.getProperty("LIBRARY_GUI"));
			Socket socket = new Socket(address,
					Integer.parseInt(propertyLoaderService.getProperty("LIBRARY_GUI_PORT")));
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			DistributorEntity distributorEntity = new DistributorEntity("Distributor" + new Random().nextInt(100000));
			CurrentLoggedInUserService.distributorEntity = distributorEntity;
			output.writeObject(distributorEntity);
			do {
				try {
					String option = (String) input.readObject();
					if ("GET".equals(option)) {
						output.writeObject(BooksService.getBooksService().getBooks());
					}
				} catch (ClassNotFoundException e) {
					loggerService.logError("Error casting to another object", e);
				}
			} while (true);
		} catch (NumberFormatException | IOException e) {
			loggerService.logError("Error setting up connections to server", e);
		}

	}

}
