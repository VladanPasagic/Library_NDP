package org.unibl.etf.mdp.library.services;

import java.rmi.RemoteException;

import org.unibl.etf.mdp.library.entities.OrderEntity;
import org.unibl.etf.mdp.library.services.interfaces.IBookkeepingService;

public class BookKeepingService implements IBookkeepingService {

	private ReceiptService receiptService = new ReceiptService();
	
	public BookKeepingService() throws RemoteException {

	}

	@Override
	public double generateReceipt(OrderEntity entity) throws RemoteException {
		// TODO Auto-generated method stub
		return 2.0;
	}

}
