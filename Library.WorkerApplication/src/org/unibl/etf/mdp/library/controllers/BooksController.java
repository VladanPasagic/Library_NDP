package org.unibl.etf.mdp.library.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class BooksController implements Initializable {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	private MenuController menuController = new MenuController();

	private ObservableList<BookEntity> books;

	@FXML
	private TableView<BookEntity> tableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		books = FXCollections.observableArrayList();
		BookEntity[] list = HttpUtils.get(propertyLoaderService.getProperty("LIBRARY_SERVER") + "books",
				BookEntity[].class);
		for (BookEntity entity : list)
			books.add(entity);
		tableView.setItems(books);
	}

	@FXML
	private void switchToBooks(ActionEvent event) {
		return;
	}

	@FXML
	private void switchToUsers(ActionEvent event) {
		menuController.switchToUsers(event);
	}

	@FXML
	private void switchToOrders(ActionEvent event) {
		menuController.switchToOrders(event);
	}

	@FXML
	private void switchToMulticastMessageSend(ActionEvent event) {
		menuController.switchToMulticastMessageSend(event);
	}

	@FXML
	private void switchToRequests(ActionEvent event) {
		menuController.switchToRequests(event);
	}

}
