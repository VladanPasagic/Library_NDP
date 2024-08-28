package org.unibl.etf.mdp.library.services.interfaces;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;

public interface ISceneSwitcherService {
	void switchScene(URL url, ActionEvent event, boolean isMenuItem) throws IOException;
}
