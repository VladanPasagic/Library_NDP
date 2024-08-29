package org.unibl.etf.mdp.library.main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.interfaces.IInitializable;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());

	@Override
	public void start(Stage stage) throws Exception {
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/UsersScene.fxml").toUri().toURL();
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			var controller = loader.getController();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			if (controller instanceof IInitializable) {
				((IInitializable) controller).init();
			}
		} catch (IOException ex) {
			loggerService.logCritical("Couldn't load scene", ex);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
