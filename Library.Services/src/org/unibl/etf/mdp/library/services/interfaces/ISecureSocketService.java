package org.unibl.etf.mdp.library.services.interfaces;

import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public interface ISecureSocketService {

	Socket getClientSocket(String host, int port);

	ServerSocket getServerSocket(int port);

}
