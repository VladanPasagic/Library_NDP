package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.entities.UserEntity;
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
import org.unibl.etf.mdp.library.services.internal.CurrentLoggedInUserService;
import org.unibl.etf.mdp.library.threads.MulticastListenerThread;
import org.unibl.etf.mdp.library.threads.internal.ServerThread;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {

	private static final String MULTICAST_SERVER = "MULTICAST_SERVER";
	private static final String MULTICAST_PORT = "MULTICAST_PORT";
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
		UserEntity entity = HttpUtils.post(propertyLoaderService.getProperty("LIBRARY_SERVER") + "auth/login",
				loginRequest, UserEntity.class);
		if (entity != null) {
			try {
				URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/BooksScene.fxml").toUri().toURL();
				CurrentLoggedInUserService.current = entity;
				ServerThread.getInstance().start();
				sceneSwitcherService.switchScene(url, event, false);
				MulticastListenerThread listenerThread = new MulticastListenerThread(
						propertyLoaderService.getProperty(MULTICAST_SERVER),
						Integer.parseInt(propertyLoaderService.getProperty(MULTICAST_PORT)));
				listenerThread.setDaemon(true);
				listenerThread.start();
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
