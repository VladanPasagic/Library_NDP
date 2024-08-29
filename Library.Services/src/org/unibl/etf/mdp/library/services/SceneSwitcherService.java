package org.unibl.etf.mdp.library.services;

import java.io.IOException;
import java.net.URL;

import org.unibl.etf.mdp.library.interfaces.IInitializable;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SceneSwitcherService implements ISceneSwitcherService {
	private static SceneSwitcherService instance = null;

	private Stage stage;
	private Scene scene;
	private Parent root;

	private SceneSwitcherService() {

	}

	public static SceneSwitcherService getSwitcherService() {
		if (instance == null)
			instance = new SceneSwitcherService();
		return instance;
	}

	public void switchScene(URL url, ActionEvent event, boolean isMenuItem) throws IOException {
		FXMLLoader loader = new FXMLLoader(url);
		root = loader.load();
		var controller = loader.getController();
		if (isMenuItem) {
			stage = (Stage) (((MenuItem) event.getSource()).getParentPopup().getOwnerWindow());
		} else {
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		}
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		if (controller instanceof IInitializable) {
			((IInitializable) controller).init();
		}
	}
}
