package org.unibl.etf.mdp.library.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.unibl.etf.mdp.library.entities.OrderEntity;
import org.unibl.etf.mdp.library.entities.OrderItemEntity;
import org.unibl.etf.mdp.library.entities.ReceiptEntity;
import org.unibl.etf.mdp.library.entities.ReceiptItemEntity;
import org.unibl.etf.mdp.library.services.interfaces.IBookkeepingService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class BookKeepingService implements IBookkeepingService {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private IPropertyLoaderService propertyLoaderService = PropertyLoaderService.load(loggerService, false, null);

	private ReceiptService receiptService = new ReceiptService();

	public BookKeepingService() throws RemoteException {

	}

	@Override
	public double generateReceipt(OrderEntity entity) throws RemoteException {
		List<ReceiptItemEntity> receiptItems = new ArrayList<ReceiptItemEntity>();
		int bookCount = 0;
		for (OrderItemEntity orderItem : entity.getItems()) {
			bookCount += orderItem.getQuantity();
			receiptItems.add(new ReceiptItemEntity(orderItem.getBook(), orderItem.getQuantity()));
		}
		double rand = new Random().nextDouble() * 5;
		double price = rand * bookCount;
		ReceiptEntity receiptEntity = new ReceiptEntity(receiptItems, price);
		receiptService.saveReceipt(receiptEntity);
		return ((double) Integer.parseInt(propertyLoaderService.getProperty("PDV"))) / 100 * price;
	}

}
