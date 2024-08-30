package org.unibl.etf.mdp.library.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class Server {
	private static ILoggerService loggerService = LoggerService.getLogger(Server.class.getName());
	private static IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false,
			null);
	private final static String TCP_PORT_PROPERTY = "TCP_PORT";

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(Integer.parseInt(propertyLoaderService.getProperty(TCP_PORT_PROPERTY)));
			while (true) {
				Socket socket = ss.accept();
				new ServerThread(socket, loggerService, propertyLoaderService).start();
			}
		} catch (Exception ex) {
			loggerService.logError("Error occurred", ex);
		}
	}
}
