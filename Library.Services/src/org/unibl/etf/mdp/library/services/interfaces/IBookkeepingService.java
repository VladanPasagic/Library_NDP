package org.unibl.etf.mdp.library.services.interfaces;

import java.rmi.RemoteException;

import org.unibl.etf.mdp.library.entities.OrderEntity;

public interface IBookkeepingService {
	double generateReceipt(OrderEntity entity) throws RemoteException;
}
