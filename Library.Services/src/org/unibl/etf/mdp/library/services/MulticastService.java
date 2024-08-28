package org.unibl.etf.mdp.library.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMulticastService;

public class MulticastService implements IMulticastService {

	private static IMulticastService instance = null;
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());

	private MulticastService() {

	}

	public static IMulticastService getInstance() {
		if (instance == null)
			instance = new MulticastService();
		return instance;
	}

	@Override
	public void sendMessage(String address, int port, String text) {
		MulticastSocket socket = null;
		byte[] buffer = new byte[1024];
		try {
			socket = new MulticastSocket();
			InetAddress inetAddress = InetAddress.getByName(address);
			SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);
			NetworkInterface netIf = NetworkInterface.getNetworkInterfaces().nextElement();
			socket.joinGroup(socketAddress, netIf);
			buffer = text.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, inetAddress, port);
			socket.send(packet);
		} catch (IOException ex) {
			loggerService.logError("Error sending multicast message", ex);
		}
	}

}
