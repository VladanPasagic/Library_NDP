package org.unibl.etf.mdp.library.threads.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.entities.DistributorEntity;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.internal.DistributorService;

public class InternalServerThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private DistributorService distributorService = DistributorService.getDistributorService();

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	private boolean run = false;

	public InternalServerThread(Socket socket) {
		this.socket = socket;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e) {
			loggerService.logError("Error creating streams in internal thread", e);
		}
	}

	@Override
	public void run() {
		try {
			DistributorEntity distributorEntity = (DistributorEntity) input.readObject();
			distributorService.addDistributor(distributorEntity, this);
			while (true) {
				while (run == false) {
					Thread.sleep(1000);
				}
				output.writeObject("GET");
				output.flush();
				ArrayList<BookEntity> books = (ArrayList<BookEntity>) input.readObject();
				distributorService.setCurrentBooks(books);
				run = false;
			}
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			loggerService.logError("Error running internal thread", e);
		}
	}

	public void getFromDistributor() {
		run = true;
	}
}
