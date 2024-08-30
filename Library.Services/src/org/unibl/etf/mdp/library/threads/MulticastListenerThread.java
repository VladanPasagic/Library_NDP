package org.unibl.etf.mdp.library.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

import org.unibl.etf.mdp.library.helpers.AlertUtils;
import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

public class MulticastListenerThread extends Thread {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private String address;
	private int port;

	public MulticastListenerThread(String address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void run() {
		MulticastSocket socket = null;
		byte[] buffer = new byte[1024];
		try {
			socket = new MulticastSocket(port);
			InetAddress address = InetAddress.getByName(this.address);
			/*
			 * SocketAddress socketAddress = new InetSocketAddress(address, port);
			 * NetworkInterface netIf =
			 * NetworkInterface.getNetworkInterfaces().nextElement();
			 * socket.joinGroup(socketAddress, netIf);
			 */
			socket.joinGroup(address);
			while (true) {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				Platform.runLater(() -> AlertUtils.setAlert(AlertType.INFORMATION, "Multicast message",
						"New multicast message", received));
			}
		} catch (IOException ex) {
			loggerService.logError("Error setting up multicast listener", ex);
		}
	}

}
