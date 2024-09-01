package org.unibl.etf.mdp.library.services.interfaces;

import java.net.ServerSocket;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public interface ISecureSocketService {

	SSLSocket getClientSocket(String host, int port);

	SSLServerSocket getServerSocket(int port);

}
