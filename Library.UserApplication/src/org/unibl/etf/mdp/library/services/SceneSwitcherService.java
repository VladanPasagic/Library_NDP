package org.unibl.etf.mdp.library.services;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcherService {
	private static SceneSwitcherService instance = null;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private SceneSwitcherService()
	{
		
	}
	
	public static SceneSwitcherService getSwitcherService()
	{
		if (instance == null)
			instance = new SceneSwitcherService();
		return instance;
	}
	
	public void switchScene(URL url, ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(url);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
