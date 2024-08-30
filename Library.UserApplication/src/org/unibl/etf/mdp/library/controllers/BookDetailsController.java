package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BookDetailsController {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();

	public void getBook(UUID id) {

	}

	@FXML
	private void returnToPreviousScene(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/BooksScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, false);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}
}
