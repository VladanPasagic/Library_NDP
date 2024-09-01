package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.entities.DistributorEntity;
import org.unibl.etf.mdp.library.entities.OrderEntity;
import org.unibl.etf.mdp.library.entities.OrderItemEntity;
import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.helpers.StringUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.MessageQueueService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMessageQueueService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.internal.DistributorService;
import org.unibl.etf.mdp.library.threads.internal.InternalServerThread;

import com.rabbitmq.client.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class OrdersController implements Initializable {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	private MenuController menuController = new MenuController();

	private ObservableList<OrderItemEntity> orders;

	private ObservableList<BookEntity> books;

	private Map<DistributorEntity, InternalServerThread> distributors;

	private ObservableList<DistributorEntity> distributorEntities;

	@FXML
	private TextField quantityField;

	@FXML
	private TableView<OrderItemEntity> tableView;

	@FXML
	private ComboBox<DistributorEntity> distributorComboBox;

	@FXML
	private ComboBox<BookEntity> bookComboBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		books = FXCollections.observableArrayList();
		distributorEntities = FXCollections.observableArrayList();
		orders = FXCollections.observableArrayList();
		tableView.setItems(orders);
		distributors = DistributorService.getDistributorService().getDistributors();
		Set<DistributorEntity> distributorEntities = distributors.keySet();
		for (DistributorEntity distributorEntity : distributorEntities) {
			this.distributorEntities.add(distributorEntity);
		}
		distributorComboBox.setItems(this.distributorEntities);
	}

	@FXML
	private void loadBooksFromDistributor(ActionEvent event) {
		books.clear();
		DistributorEntity distributor = distributorComboBox.getSelectionModel().getSelectedItem();
		if (distributor == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Distributor not selected", null, "Please select a distributor");
			return;
		}
		InternalServerThread distributorThread = distributors.get(distributor);
		List<BookEntity> books = null;
		distributorThread.getFromDistributor();
		do {
			books = DistributorService.getDistributorService().getCurrentBooks();
			if (books != null)
				for (BookEntity bookEntity : books) {
					this.books.add(bookEntity);
				}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		} while (books == null);
		bookComboBox.setItems(this.books);
	}

	@FXML
	private void switchToUsers(ActionEvent event) {
		menuController.switchToUsers(event);
	}

	@FXML
	private void switchToRequests(ActionEvent event) {
		menuController.switchToRequests(event);
	}

	@FXML
	private void switchToBooks(ActionEvent event) {
		menuController.switchToBooks(event);
	}

	@FXML
	private void switchToOrders(ActionEvent event) {
		return;
	}

	@FXML
	private void switchToMulticastMessageSend(ActionEvent event) {
		menuController.switchToMulticastMessageSend(event);
	}

	@FXML
	private void order(ActionEvent event) {
		DistributorEntity distributor = distributorComboBox.getSelectionModel().getSelectedItem();
		OrderEntity orderEntity = new OrderEntity(orders);
		IMessageQueueService messageQueueService = new MessageQueueService();
		String host = propertyLoaderService.getProperty("RABBIT_HOST");
		String user = propertyLoaderService.getProperty("RABBIT_USER");
		String pass = propertyLoaderService.getProperty("RABBIT_PASS");
		try {
			Connection connection = messageQueueService.createConnection(host, user, pass);
			messageQueueService.send(connection, distributor.getName(), orderEntity);
			connection.close();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		distributorComboBox.getSelectionModel().clearSelection();
		books.clear();
		orders.clear();
		tableView.refresh();
	}

	@FXML
	private void removeFromList(ActionEvent event) {
		OrderItemEntity order = tableView.getSelectionModel().getSelectedItem();
		if (order == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Order not selected", null, "Select an order");
			return;
		}
		orders.remove(order);
	}

	@FXML
	private void addToList(ActionEvent event) {
		BookEntity book = bookComboBox.getSelectionModel().getSelectedItem();
		if (book == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Book not selected", null, "Please select a book");
			return;
		}
		if (StringUtils.isNullOrEmpty(quantityField.getText())) {
			AlertUtils.setAlert(AlertType.INFORMATION, "Quantity cannot be empty", null, "Type in a quantity");
			return;
		}
		int value;
		try {
			value = Integer.parseInt(quantityField.getText());
		} catch (Exception e) {
			AlertUtils.setAlert(AlertType.ERROR, "Value must be a number", null, "Value of the field must be null");
			return;
		}
		if (value != 0) {
			boolean found = false;
			for (OrderItemEntity orderItemEntity : orders) {
				if (orderItemEntity.getBookId().equals(book.getId())) {
					orderItemEntity.setQuantity(orderItemEntity.getQuantity() + value);
					tableView.refresh();
					found = true;
				}
			}
			if (found == false) {
				orders.add(new OrderItemEntity(book.getId(), value, book));
			}
		} else {
			AlertUtils.setAlert(AlertType.INFORMATION, "Value cannot be 0", null, "Value cannot be 0");
		}

	}

}
