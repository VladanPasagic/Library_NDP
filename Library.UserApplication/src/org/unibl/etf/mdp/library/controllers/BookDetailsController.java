package org.unibl.etf.mdp.library.controllers;

import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.SceneSwitcherService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ISceneSwitcherService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookDetailsController {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private ISceneSwitcherService sceneSwitcherService = SceneSwitcherService.getSwitcherService();
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	@FXML
	private ImageView imageView;

	@FXML
	private TextArea textArea;

	@FXML
	private Label label;

	public void getBook(UUID id) {
		BookEntity entity = HttpUtils.get(propertyLoaderService.getProperty("BASE_URL") + "books/" + id,
				BookEntity.class);
		imageView.setImage(new Image(entity.getFrontPageLink()));
		textArea.setText(entity.getContentPath());
		label.setText(entity.getName());
	}

}
