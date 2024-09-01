package org.unibl.etf.mdp.library.main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.threads.MulticastListenerThread;
import org.unibl.etf.mdp.library.threads.internal.ServerThread;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private static final String MULTICAST_SERVER = "MULTICAST_SERVER";
	private static final String MULTICAST_PORT = "MULTICAST_PORT";
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	@Override
	public void start(Stage stage) throws Exception {
		ServerThread thread = ServerThread.getInstance();
		thread.setDaemon(true);
		MulticastListenerThread listenerThread = new MulticastListenerThread(
				propertyLoaderService.getProperty(MULTICAST_SERVER),
				Integer.parseInt(propertyLoaderService.getProperty(MULTICAST_PORT)));
		listenerThread.setDaemon(true);
		listenerThread.start();
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/LoginScene.fxml").toUri().toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			loggerService.logError("Couldn't load scene", ex);
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
