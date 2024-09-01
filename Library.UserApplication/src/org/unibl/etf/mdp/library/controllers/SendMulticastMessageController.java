package org.unibl.etf.mdp.library.controllers;

import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.StringUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.MulticastService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMulticastService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class SendMulticastMessageController {

	private MenuController menuController = new MenuController();
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);
	private IMulticastService multicastService = MulticastService.getInstance();
	private static final String MULTICAST_SERVER = "MULTICAST_SERVER";
	private static final String MULTICAST_PORT = "MULTICAST_PORT";

	@FXML
	private TextArea content;

	@FXML
	private void goToBooks(ActionEvent event) {
		menuController.switchToBooks(event);
	}

	@FXML
	private void goToChats(ActionEvent event) {
		menuController.switchToChats(event);
	}

	@FXML
	private void goToMessageSend(ActionEvent event) {
		return;
	}

	@FXML
	private void sendMessage(ActionEvent event) {
		if (StringUtils.isNullOrEmpty(content.getText())) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Field empty", null, "Message cannot be empty");
			return;
		}

		multicastService.sendMessage(propertyLoaderService.getProperty(MULTICAST_SERVER),
				Integer.parseInt(propertyLoaderService.getProperty(MULTICAST_PORT)), content.getText());
	}
}
