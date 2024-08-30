package org.unibl.etf.mdp.library.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.services.GutenbergService;
import org.unibl.etf.mdp.library.services.interfaces.IGutenbergService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class ServerThread extends Thread {

	private final static String BOOK_LIST = "BOOK_LIST";
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ILoggerService loggerService;
	private IPropertyLoaderService propertyLoaderService;
	private IGutenbergService gutenbergService = GutenbergService.getGutenbergService();
	private List<BookEntity> books = new ArrayList<BookEntity>();

	public ServerThread(Socket socket, ILoggerService loggerService, IPropertyLoaderService propertyLoaderService) {
		this.propertyLoaderService = propertyLoaderService;
		this.socket = socket;
		this.loggerService = loggerService;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (Exception ex) {
			loggerService.logError("Error occurred while establishing input and output streams", ex);
		}
	}

	public void run() {
		try {
			String option = "";
			while (!"END".equals(option)) {
				option = (String) input.readObject();
				if ("GET".equals(option)) {
					List<String> lines = Files.readAllLines(Path.of(propertyLoaderService.getProperty(BOOK_LIST)));
					for (String line : lines) {
						books.add(gutenbergService.getBook(line));
					}
					output.writeObject(books);
					output.flush();
				}
			}
		} catch (Exception ex) {
			loggerService.logError("Error running server thread", ex);
		}
	}

}
