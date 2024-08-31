package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.event.ActionEvent;

public class MenuController {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();

	public MenuController() {

	}

	public void switchToChats(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/ChatScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, true);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}

	public void switchToMulticastMessageSend(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/SendMulticastMessageScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, true);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}

	public void switchToBooks(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/BooksScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, true);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}
}
