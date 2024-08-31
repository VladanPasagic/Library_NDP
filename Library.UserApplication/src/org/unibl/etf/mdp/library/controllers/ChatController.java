package org.unibl.etf.mdp.library.controllers;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ChatController implements Initializable {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);
	private MenuController menuController = new MenuController();

	private ObservableList<UserEntity> users;

	@FXML
	private ComboBox<UserEntity> comboBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = FXCollections.observableArrayList();
		UserEntity[] list = HttpUtils.get(propertyLoaderService.getProperty("LIBRARY_SERVER") + "users",
				UserEntity[].class);
		for (UserEntity entity : list) {
			users.add(entity);
		}
		comboBox.setItems(users);
	}

	@FXML
	private void viewMessage(ActionEvent event) {
		UserEntity user = comboBox.getSelectionModel().getSelectedItem();
		if (user == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "User not selected", null, "Select a user");
			return;
		}

		URL url;
		try {
			url = Paths.get("src/org/unibl/etf/mdp/library/scenes/ChatDetailsScene.fxml").toUri().toURL();
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			ChatDetailsController controller = loader.getController();
			controller.getUserMessages(user);
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
			stage.setOnCloseRequest(evnt -> {
				controller.onClose();
			});
		} catch (Exception e) {
			loggerService.logError("Error loading scene", e);
		}
	}

	@FXML
	private void goToBooks(ActionEvent event) {
		menuController.switchToBooks(event);
	}

	@FXML
	private void goToMessageSend(ActionEvent event) {
		menuController.switchToMulticastMessageSend(event);
	}

	@FXML
	private void goToChats(ActionEvent event) {
		return;
	}

}
