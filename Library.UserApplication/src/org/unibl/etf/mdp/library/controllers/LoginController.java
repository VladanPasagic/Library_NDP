package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.helpers.StringUtils;
import org.unibl.etf.mdp.library.requests.LoginRequest;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();
	private ILoggerService logger = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(logger, false, null);
	private String baseUrl = propertyLoaderService.getProperty("BASE_URL");

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private void handleLogin(ActionEvent event) {
		if (StringUtils.isNullOrEmpty(username.getText()) || StringUtils.isNullOrEmpty(password.getText())) {
			AlertUtils.setAlert(AlertType.ERROR, "error occurred", "Fields cannot be empty",
					"Please fill the fields and try again");
			return;
		}
		LoginRequest loginRequest = new LoginRequest(username.getText(), password.getText());
		boolean status = HttpUtils.post(propertyLoaderService.getProperty("BASE_URL") + "auth/login", loginRequest, LoginRequest.class);
		if (status) {
			try {
				URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/BooksScene.fxml").toUri().toURL();
				sceneSwitcherService.switchScene(url, event, false);
			} catch (IOException ex) {
				logger.logError("Couldn't load scene", ex);
			}
		} else {
			AlertUtils.setAlert(AlertType.INFORMATION, "invalid login information", "Invalid login credentials",
					"Please enter valid credentials");
		}
	}

	@FXML
	private void handleSwitchToRegister(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/RegisterScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, false);
		} catch (IOException ex) {
			logger.logError("Couldn't load scene", ex);
		}
	}
}
