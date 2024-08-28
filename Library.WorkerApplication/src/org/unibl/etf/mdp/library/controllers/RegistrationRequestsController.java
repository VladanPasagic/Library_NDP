package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class RegistrationRequestsController {

	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());

	@FXML
	private void switchToUsers(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/UsersScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, true);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}

	@FXML
	private void switchToRequests(ActionEvent event) {
		return;
	}
}
