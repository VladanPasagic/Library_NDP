package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.requests.MultipleBookRequest;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.internal.CurrentLoggedInUserService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BooksController implements Initializable {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);
	private MenuController menuController = new MenuController();

	private ObservableList<BookEntity> books;

	@FXML
	private TableView<BookEntity> tableView;

	@FXML
	private TableColumn<BookEntity, Void> detailsColumn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		books = FXCollections.observableArrayList();
		BookEntity[] list = HttpUtils.get(propertyLoaderService.getProperty("LIBRARY_SERVER") + "books",
				BookEntity[].class);
		for (BookEntity entity : list)
			books.add(entity);
		tableView.setItems(books);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		Callback<TableColumn<BookEntity, Void>, TableCell<BookEntity, Void>> cellFactory = new Callback<TableColumn<BookEntity, Void>, TableCell<BookEntity, Void>>() {

			@Override
			public TableCell<BookEntity, Void> call(TableColumn<BookEntity, Void> arg0) {
				final TableCell<BookEntity, Void> cell = new TableCell<BookEntity, Void>() {
					private final Button button = new Button("View");
					{
						button.setOnAction((ActionEvent event) -> {
							BookEntity entity = getTableView().getItems().get(getIndex());
							getBookDetails(event, entity);
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty)
							setGraphic(null);
						else {
							setGraphic(button);
						}
					}
				};
				return cell;
			}
		};
		detailsColumn.setCellFactory(cellFactory);

	}

	private void getBookDetails(ActionEvent event, BookEntity entity) {
		if (entity == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Not selected", null, "Book not selected");
		}
		URL url;
		try {
			url = Paths.get("src/org/unibl/etf/mdp/library/scenes/BookDetailsScene.fxml").toUri().toURL();
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
			BookDetailsController controller = loader.getController();
			controller.getBook(entity.getId());
			Stage primary = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Stage stage = new Stage();
			stage.initOwner(primary);
			stage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		} catch (MalformedURLException e) {
			loggerService.logError("Couldn't load scene", e);
		} catch (IOException e) {
			loggerService.logError("Couldn't load scene", e);
		}
	}

	@FXML
	private void goToBooks(ActionEvent event) {
		return;
	}

	@FXML
	private void goToMessageSend(ActionEvent event) {
		menuController.switchToMulticastMessageSend(event);
	}

	@FXML
	private void goToChats(ActionEvent event) {
		menuController.switchToChats(event);
	}

	@FXML
	private void buyBooks(ActionEvent event) {
		ObservableList<BookEntity> selectedBooks = tableView.getSelectionModel().getSelectedItems();
		if (selectedBooks.size() == 0) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Not selected", null, "Please select books");
			return;
		}
		List<String> books = new ArrayList<String>();
		for (BookEntity book : this.books) {
			books.add(book.getId().toString());
		}
		MultipleBookRequest bookRequest = new MultipleBookRequest(books);
		HttpUtils.post(propertyLoaderService.getProperty("LIBRARY_SERVER") + "mail/"
				+ CurrentLoggedInUserService.current.getId(), bookRequest);
	}
}
