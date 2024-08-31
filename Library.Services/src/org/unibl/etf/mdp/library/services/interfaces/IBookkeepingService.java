package org.unibl.etf.mdp.library.services.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.unibl.etf.mdp.library.entities.OrderEntity;

public interface IBookkeepingService extends Remote {
	double generateReceipt(OrderEntity entity) throws RemoteException;
}
