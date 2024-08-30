package org.unibl.etf.mdp.library.controllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class BooksController implements Initializable {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);
	private ObjectInputStream input;
	private ObjectOutputStream output;

	private ObservableList<BookEntity> books;

	@FXML
	private TableView<BookEntity> tableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			InetAddress address = InetAddress.getByName(propertyLoaderService.getProperty("DISTRIBUTOR_SERVER"));
			Socket socket = new Socket(address,
					Integer.parseInt(propertyLoaderService.getProperty("DISTRIBUTOR_PORT")));
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			output.writeObject("GET");
			output.flush();
			ArrayList<BookEntity> books = (ArrayList<BookEntity>) input.readObject();
			for (BookEntity bookEntity : books) {
				this.books.add(bookEntity);
			}
			tableView.setItems(this.books);
			output.close();
			input.close();
			socket.close();
		} catch (Exception e) {
			loggerService.logError("Error fetching data", e);
		}
	}

}
