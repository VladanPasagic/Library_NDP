package org.unibl.etf.mdp.library.services.interfaces;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.unibl.etf.mdp.library.entities.OrderEntity;

import com.rabbitmq.client.Connection;

public interface IMessageQueueService {

	Connection createConnection(String host, String username, String password) throws IOException, TimeoutException;

	void send(Connection connection, String receiver, OrderEntity order);
	
	OrderEntity receive(Connection connection, String receiver);
}
