package org.unibl.etf.mdp.library.controllers;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import org.unibl.etf.mdp.library.services.SceneSwitcherService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
	
	private SceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private void handleLogin(ActionEvent event)
	{
		
	}
	
	@FXML
	private void handleSwitchToRegister(ActionEvent event)
	{
		try {
			URL url = Paths.get("src/org/unibl/etf/mdp/library/scenes/RegisterScene.fxml").toUri().toURL();
			sceneSwitcherService.switchScene(null, event);
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
}
