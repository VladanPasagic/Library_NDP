package org.unibl.etf.mdp.library.threads.internal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class ServerThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	@Override
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(Integer.parseInt(propertyLoaderService.getProperty("TCP_PORT")));
			while (true) {
				Socket socket = ss.accept();
				new InternalServerThread(socket).start();
			}
		} catch (NumberFormatException | IOException e) {
			loggerService.logError("Error setting up server", e);
		}

	}
}
