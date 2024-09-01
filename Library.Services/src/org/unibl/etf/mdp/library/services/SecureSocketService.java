package org.unibl.etf.mdp.library.services;

import java.io.IOException;

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
	public SSLSocket getClientSocket(String host, int port) {
		System.setProperty("javax.net.ssl.trustStore", propertyLoaderService.getProperty("KEY_STORE_PATH"));
		System.setProperty("javax.net.ssl.trustStorePassword", propertyLoaderService.getProperty("KEY_STORE_PASSWORD"));
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket socket;
		try {
			socket = (SSLSocket) sf.createSocket(host, port);
			return socket;
		} catch (IOException e) {
			loggerService.logError("Couldn't create client socket", e);
		}
		return null;
	}

	@Override
	public SSLServerSocket getServerSocket(int port) {
		System.setProperty("javax.net.ssl.keyStore", propertyLoaderService.getProperty("KEY_STORE_PATH"));
		System.setProperty("javax.net.ssl.keyStorePassword", propertyLoaderService.getProperty("KEY_STORE_PASSWORD"));
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try {
			SSLServerSocket socket = (SSLServerSocket) ssf.createServerSocket(port);
			return socket;
		} catch (IOException e) {
			loggerService.logError("Couldn't create client socket", e);
		}
		return null;
	}

}
