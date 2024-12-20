package org.unibl.etf.mdp.library.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ISecureSocketService;

public class SecureSocketService implements ISecureSocketService {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private static SecureSocketService instance = null;
	private IPropertyLoaderService propertyLoaderService;

	private SecureSocketService(IPropertyLoaderService propertyLoaderService) {
		this.propertyLoaderService = propertyLoaderService;
	}

	public static ISecureSocketService getSecureSocketService(IPropertyLoaderService propertyLoaderService) {
		if (instance == null)
			instance = new SecureSocketService(propertyLoaderService);
		return instance;
	}

	@Override
	public Socket getClientSocket(String host, int port) {
		System.setProperty("javax.net.ssl.trustStore", propertyLoaderService.getProperty("KEY_STORE_PATH"));
		System.setProperty("javax.net.ssl.trustStorePassword", propertyLoaderService.getProperty("KEY_STORE_PASSWORD"));
		SocketFactory sf = SocketFactory.getDefault();
		Socket socket;
		try {
			socket = sf.createSocket(host, port);
			return socket;
		} catch (IOException e) {
			loggerService.logError("Couldn't create client socket", e);
		}
		return null;
	}

	@Override
	public ServerSocket getServerSocket(int port) {
		System.setProperty("javax.net.ssl.keyStore", propertyLoaderService.getProperty("KEY_STORE_PATH"));
		System.setProperty("javax.net.ssl.keyStorePassword", propertyLoaderService.getProperty("KEY_STORE_PASSWORD"));
		ServerSocketFactory ssf = ServerSocketFactory.getDefault();
		try {
			ServerSocket socket = ssf.createServerSocket(port);
			return socket;
		} catch (IOException e) {
			loggerService.logError("Couldn't create client socket", e);
		}
		return null;
	}

}
