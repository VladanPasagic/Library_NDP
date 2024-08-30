package org.unibl.etf.mdp.library.services.interfaces;

import java.io.File;

public interface IMailSendService {
	void sendMail(String receiver, String sender, String title, String content, File files);
}
