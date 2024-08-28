package org.unibl.etf.mdp.library.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

public class ServerThread extends Thread {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ILoggerService loggerService;

	public ServerThread(Socket socket, ILoggerService loggerService) {
		this.socket = socket;
		this.loggerService = loggerService;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception ex) {
			loggerService.logError("Error occurred while establishing input and output streams", ex);
		}
	}

	public void run() {
		try {
			String option = "";
			while(!"END".equals(option))
			{
				option = (String) input.readObject();
				if ("GET".equals(option))
				{
					
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}

}
