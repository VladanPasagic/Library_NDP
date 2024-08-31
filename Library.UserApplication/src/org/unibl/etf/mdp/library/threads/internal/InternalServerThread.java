package org.unibl.etf.mdp.library.threads.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.net.ssl.SSLSocket;

import org.unibl.etf.mdp.library.entities.MessageEntity;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.internal.ChatService;

public class InternalServerThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());

	private ChatService chatService = ChatService.getChatService();
	private SSLSocket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public InternalServerThread(SSLSocket socket) {
		this.chatService = chatService;
		this.socket = socket;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			loggerService.logError("Error opening streams", e);
		}
	}

	@Override
	public void run() {
		String option = "";
		while (!"###".equals(option)) {
			try {
				MessageEntity message = (MessageEntity) input.readObject();
				option = message.getContent();
				if (!"####".equals(option))
					chatService.sendMessage(message.getSenderId(), message);
			} catch (ClassNotFoundException | IOException e) {
				loggerService.logError("Couldn't read as MessageEntity", e);
			}
		}
	}
}
