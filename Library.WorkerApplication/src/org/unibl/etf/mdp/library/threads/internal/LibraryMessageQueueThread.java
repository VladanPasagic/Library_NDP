package org.unibl.etf.mdp.library.threads.internal;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.entities.OrderEntity;
import org.unibl.etf.mdp.library.entities.OrderItemEntity;
import org.unibl.etf.mdp.library.helpers.HttpUtils;
import org.unibl.etf.mdp.library.requests.NewBookRequest;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.MessageQueueService;
import org.unibl.etf.mdp.library.services.PropertyLoaderService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMessageQueueService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import com.rabbitmq.client.Connection;

public class LibraryMessageQueueThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	IMessageQueueService messageQueueService = new MessageQueueService();

	@Override
	public void run() {
		String host = propertyLoaderService.getProperty("RABBIT_HOST");
		String user = propertyLoaderService.getProperty("RABBIT_USER");
		String pass = propertyLoaderService.getProperty("RABBIT_PASS");
		try {
			Connection connection = messageQueueService.createConnection(host, user, pass);
			do {
				OrderEntity order = messageQueueService.receive(connection, "LIBRARY");
				if (order != null) {
					System.out.println(order.getId());
					for (OrderItemEntity orderItem : order.getItems()) {
						BookEntity book = orderItem.getBook();
						NewBookRequest bookRequest = new NewBookRequest();
						bookRequest.setAuthor(book.getAuthor());
						bookRequest.setContentPath(book.getContentPath());
						bookRequest.setFrontPageLink(book.getFrontPageLink());
						bookRequest.setISBN(book.getISBN());
						bookRequest.setLanguage(book.getLanguage());
						bookRequest.setName(book.getName());
						bookRequest.setReleaseDate(book.getReleaseDate());
						HttpUtils.post(propertyLoaderService.getProperty("LIBRARY_SERVER") + "books", bookRequest);
					}
				}
				Thread.sleep(1000);
			} while (true);
		} catch (IOException | TimeoutException | InterruptedException e) {
			loggerService.logError("Couldn't load rabbit mq", e);
		}

	}

}
