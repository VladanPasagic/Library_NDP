package org.unibl.etf.mdp.library.services.interfaces;

public interface IMulticastService {
	public void sendMessage(String address, int port, String text);
}
