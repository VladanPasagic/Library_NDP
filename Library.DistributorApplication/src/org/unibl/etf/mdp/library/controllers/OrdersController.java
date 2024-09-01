package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

import org.unibl.etf.mdp.library.entities.OrderEntity;
import org.unibl.etf.mdp.library.entities.OrderItemEntity;
import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.MessageQueueService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.IBookkeepingService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMessageQueueService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.internal.CurrentLoggedInUserService;

import com.rabbitmq.client.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;

public class OrdersController implements Initializable {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	private MenuController menuController = new MenuController();

	private OrderEntity orderEntity = null;

	private ObservableList<OrderItemEntity> orderItems;

	@FXML
	private TableView<OrderItemEntity> tableView;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderItems = FXCollections.observableArrayList();
		tableView.setItems(orderItems);
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
	private void acceptOrder(ActionEvent event) {
		if (orderEntity == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "No order", null, "NO ORDER");
			return;
		}
		try {
			Registry registry = LocateRegistry
					.getRegistry(Integer.parseInt(propertyLoaderService.getProperty("RMI_PORT")));
			IBookkeepingService bookkeepingService = (IBookkeepingService) registry
					.lookup(propertyLoaderService.getProperty("RMI_NAME"));
			bookkeepingService.generateReceipt(orderEntity);
			System.out.println("Started method invocation");
		} catch (NumberFormatException e) {
			loggerService.logError("Error parsing number", e);
		} catch (RemoteException e) {
			loggerService.logError("Error with RMI", e);
		} catch (NotBoundException e) {
			loggerService.logError("RMI Service not bound", e);
		}
	}

	@FXML
	private void rejectOrder(ActionEvent event) {
		if (orderEntity == null) {
			AlertUtils.setAlert(AlertType.INFORMATION, "No order", null, "NO ORDER");
			return;
		}
		orderItems.clear();
		tableView.refresh();
		orderEntity = null;
	}

	@FXML
	private void downloadOrder(ActionEvent event) {
		IMessageQueueService messageQueueService = new MessageQueueService();
		String host = propertyLoaderService.getProperty("RABBIT_HOST");
		String user = propertyLoaderService.getProperty("RABBIT_USER");
		String pass = propertyLoaderService.getProperty("RABBIT_PASS");
		try {
			Connection connection = messageQueueService.createConnection(host, user, pass);
			orderEntity = messageQueueService.receive(connection,
					CurrentLoggedInUserService.distributorEntity.getName());
			if (orderEntity == null) {
				AlertUtils.setAlert(AlertType.INFORMATION, "Nothing found", null, "No more orders found");
			}
			for (OrderItemEntity orderItemEntity : orderEntity.getItems()) {
				orderItems.add(orderItemEntity);
			}
			connection.close();
		} catch (IOException | TimeoutException e) {
			loggerService.logError("Couldn't load rabbit mq", e);
		}
	}

}
