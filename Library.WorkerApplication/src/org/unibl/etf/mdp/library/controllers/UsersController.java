package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;
import org.unibl.etf.mdp.library.interfaces.IInitializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class UsersController implements IInitializable {

	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	private ObservableList<UserEntity> users;

	@FXML
	private TableView<UserEntity> tableView;

	@FXML
	private void switchToUsers(ActionEvent event) {
		return;
	}

	@FXML
	private void switchToRequests(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/RegistrationRequestsScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, true);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}

	@Override
	public void init() {
		users = FXCollections.observableArrayList();
		UserEntity[] list = HttpUtils.get(propertyLoaderService.getProperty("LIBRARY_SERVER") + "users",
				UserEntity[].class);
		for (UserEntity entity : list)
			users.add(entity);
		tableView.setItems(users);
	}

	@FXML
	private void blockUser(ActionEvent event) {
		UserEntity user = tableView.getSelectionModel().getSelectedItem();
		if (user == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "User not selected", null, "Select a user");
			return;
		}
		boolean result = HttpUtils
				.put(propertyLoaderService.getProperty("LIBRARY_SERVER") + "users/" + user.getId() + "/block");
		if (result) {
			// users.remove(tableView.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void deleteUser(ActionEvent event) {
		UserEntity user = tableView.getSelectionModel().getSelectedItem();
		if (user == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "User not selected", null, "Select a user");
			return;
		}
		boolean result = HttpUtils
				.delete(propertyLoaderService.getProperty("LIBRARY_SERVER") + "users/" + user.getId());
		if (result) {
			users.remove(tableView.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void switchToMulticastMessageSend(ActionEvent event) {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/SendMulticastMessageScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(url, event, true);
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}
	}
}
