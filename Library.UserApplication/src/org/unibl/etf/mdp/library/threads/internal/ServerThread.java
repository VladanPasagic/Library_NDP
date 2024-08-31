package org.unibl.etf.mdp.library.threads.internal;

import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ssl.SSLSocket;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.SecureSocketService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ISecureSocketService;
import org.unibl.etf.mdp.library.services.internal.ChatService;
import org.unibl.etf.mdp.library.services.internal.CurrentLoggedInUserService;

public class ServerThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);
	private ISecureSocketService secureSocketService = SecureSocketService
			.getSecureSocketService(propertyLoaderService);
	private ChatService chatService = ChatService.getChatService();
	private static ServerThread instance = null;

	private ServerThread() {
	}

	public static ServerThread getInstance() {
		if (instance == null)
			instance = new ServerThread();
		return instance;
	}

	@Override
	public void run() {
		ServerSocket socket = secureSocketService.getServerSocket(CurrentLoggedInUserService.current.getPort());
		while (true) {
			try {
				SSLSocket s = (SSLSocket) socket.accept();
				new InternalServerThread(s).start();
			} catch (IOException e) {
				loggerService.logError("Error accepting connection", e);
			}
		}
	}
}
