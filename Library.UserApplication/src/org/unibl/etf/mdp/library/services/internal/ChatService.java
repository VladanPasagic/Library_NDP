package org.unibl.etf.mdp.library.services.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.MessageEntity;

public class ChatService {
	private Map<UUID, ArrayList<MessageEntity>> messages = new HashMap<UUID, ArrayList<MessageEntity>>();

	private static ChatService instance;

	private ChatService() {

	}

	public static ChatService getChatService() {
		if (instance == null)
			instance = new ChatService();
		return instance;
	}

	public Map<UUID, ArrayList<MessageEntity>> getMessages() {
		return messages;
	}

	public ArrayList<MessageEntity> getMessagesFromUUID(UUID uuid) {
		if (messages.containsKey(uuid)) {
			return messages.get(uuid);
		} else {
			return new ArrayList<MessageEntity>();
		}
	}

	public void sendMessage(UUID receiverId, MessageEntity messageEntity) {
		if (messages.containsKey(receiverId)) {
			messages.get(receiverId).add(messageEntity);
		} else {
			ArrayList<MessageEntity> messageEntities = new ArrayList<MessageEntity>();
			messageEntities.add(messageEntity);
			messages.put(receiverId, messageEntities);
		}
	}
}
