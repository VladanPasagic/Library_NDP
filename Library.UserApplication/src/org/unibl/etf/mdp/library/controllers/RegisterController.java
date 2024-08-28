package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.helpers.StringUtils;
import org.unibl.etf.mdp.library.requests.RegistrationRequest;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {

	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();
	private ILoggerService logger = LoggerService.getLogger(getClass().getName());

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField address;

	@FXML
	private TextField email;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField password2;

	@FXML
	private void handleRegister(ActionEvent event) {
		if (StringUtils.isNullOrEmpty(firstName.getText()) || StringUtils.isNullOrEmpty(lastName.getText())
				|| StringUtils.isNullOrEmpty(address.getText()) || StringUtils.isNullOrEmpty(email.getText())
				|| StringUtils.isNullOrEmpty(username.getText()) || StringUtils.isNullOrEmpty(password.getText())
				|| StringUtils.isNullOrEmpty(password2.getText())) {
			AlertUtils.setAlert(AlertType.ERROR, "error occurred", "Fields cannot be empty",
					"Please fill the fields and try again");
			return;
		}

		if (password.getText().equals(password2.getText()) == false) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Passwords aren't matching", "Passwords don't match", null);
			return;
		}
		RegistrationRequest request = new RegistrationRequest(firstName.getText(), lastName.getText(),
				address.getText(), email.getText(), username.getText(), password.getText());
		boolean success = HttpUtils.post("", request, RegistrationRequest.class);

	}

	@FXML
	private void handleSwitchToLogin(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/LoginScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event);
		} catch (IOException ex) {
			logger.logError("Couldn't load file", ex);
		}
	}
}
