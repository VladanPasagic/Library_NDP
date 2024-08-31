package org.unibl.etf.mdp.library.services;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.unibl.etf.mdp.library.entities.OrderEntity;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMessageQueueService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MessageQueueService implements IMessageQueueService {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private static OrderEntity orderEntity = null;

	@Override
	public Connection createConnection(String host, String username, String password)
			throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		return factory.newConnection();
	}

	@Override
	public void send(Connection connection, String receiver, OrderEntity order) {
		try {
			Channel channel = connection.createChannel();
			channel.queueDeclare(receiver, false, false, false, null);
			Gson gson = new GsonBuilder().create();
			String json = gson.toJson(order);
			channel.basicPublish("", receiver, null, json.getBytes());
			channel.close();
		} catch (IOException e) {
			loggerService.logError("Error while creating rabbit mq connection", e);
		} catch (TimeoutException e) {
			loggerService.logError("Error while closing rabbit mq connection", e);
		}
	}

	@Override
	public OrderEntity receive(Connection connection, String receiver) {
		orderEntity = null;
		try {
			Channel channel = connection.createChannel();
			channel.queueDeclare(receiver, false, false, false, null);
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String json = new String(body);
					Gson gson = new GsonBuilder().create();
					orderEntity = gson.fromJson(json, orderEntity.getClass());
				}
			};
			channel.basicConsume(receiver, true, consumer);
			channel.close();
		} catch (IOException e) {
			loggerService.logError("Error while creating rabbit mq connection", e);
		} catch (TimeoutException e) {
			loggerService.logError("Error while closing rabbit mq connection", e);
		}
		return orderEntity;
	}

}
