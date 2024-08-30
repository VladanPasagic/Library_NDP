package org.unibl.etf.mdp.library.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class BooksController implements Initializable {

	private MenuController menuController = new MenuController();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
