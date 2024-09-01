package org.unibl.etf.mdp.library.main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.threads.MulticastListenerThread;
import org.unibl.etf.mdp.library.threads.internal.LibraryMessageQueueThread;
import org.unibl.etf.mdp.library.threads.internal.ServerThread;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	@Override
	public void start(Stage stage) throws Exception {
		LibraryMessageQueueThread messageQueueThread = new LibraryMessageQueueThread();
		messageQueueThread.setDaemon(true);
		messageQueueThread.start();
		MulticastListenerThread listenerThread = new MulticastListenerThread(
				propertyLoaderService.getProperty("MULTICAST_SERVER"),
				Integer.parseInt(propertyLoaderService.getProperty("MULTICAST_PORT")));
		listenerThread.setDaemon(true);
		listenerThread.start();
		ServerThread thread = new ServerThread();
		thread.setDaemon(true);
		thread.start();
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/UsersScene.fxml").toUri().toURL();
			Parent root = FXMLLoader.load(url);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException ex) {
			loggerService.logCritical("Couldn't load scene", ex);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
