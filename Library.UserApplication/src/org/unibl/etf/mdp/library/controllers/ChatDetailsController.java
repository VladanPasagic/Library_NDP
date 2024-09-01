package org.unibl.etf.mdp.library.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.MessageEntity;
import org.unibl.etf.mdp.library.entities.UserEntity;
import org.unibl.etf.mdp.library.helpers.StringUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.SecureSocketService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ISecureSocketService;
import org.unibl.etf.mdp.library.services.internal.ChatService;
import org.unibl.etf.mdp.library.services.internal.CurrentLoggedInUserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatDetailsController {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);
	private ISecureSocketService secureSocketService = SecureSocketService
			.getSecureSocketService(propertyLoaderService);

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	@FXML
	private Label usernameLabel;

	@FXML
	private TextField messageField;

	@FXML
	private TextArea chatArea;

	@FXML
	private void handleSend(ActionEvent event) {
		UserEntity active = CurrentLoggedInUserService.current;
		if (StringUtils.isNullOrEmpty(messageField.getText()) == false) {
			MessageEntity messageEntity = new MessageEntity(messageField.getText(), LocalDateTime.now(),
					active.getFirstName() + " " + active.getLastName(), UUID.fromString(active.getId()));
			chatService.sendMessage(UUID.fromString(user.getId()), messageEntity);
			messageField.clear();
			getMessages();
			try {
				output.writeObject(messageEntity);
				output.flush();
			} catch (IOException e) {
				loggerService.logError("Message couldn't be sent", e);
			}
		}
	}

	private UserEntity user;

	private ChatService chatService = ChatService.getChatService();

	private void getMessages() {
		ArrayList<MessageEntity> messages = chatService.getMessagesFromUUID(UUID.fromString(user.getId()));
		StringBuilder builder = new StringBuilder();
		for (MessageEntity messageEntity : messages) {
			builder.append(messageEntity.getSender() + "  " + messageEntity.getDateTime().toString() + "\n");
			builder.append(messageEntity.getContent() + "\n");
		}
		chatArea.setText(builder.toString());
		chatArea.setScrollTop(Double.MAX_VALUE);
	}

	@FXML
	private void refresh(ActionEvent event) {
		getMessages();
	}

	public void getUserMessages(UserEntity user) throws IOException {
		this.user = user;
		usernameLabel.setText(user.toString());
		this.socket = secureSocketService.getClientSocket(propertyLoaderService.getProperty("SECURE_SERVER_HOST"),
				user.getPort());
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
		getMessages();
	}

	public void onClose() {
		MessageEntity messageEntity = new MessageEntity("###", null, null, null);
		try {
			output.writeObject(messageEntity);
			output.flush();
		} catch (IOException e) {
			loggerService.logError("Error ending connection", e);
		}
	}
}
