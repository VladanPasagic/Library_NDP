package org.unibl.etf.mdp.library.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IMailSendService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import com.sendgrid.SendGrid;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import jakarta.xml.bind.DatatypeConverter;

public class MailSendService implements IMailSendService {
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());
	private static IMailSendService instance = null;
	private IPropertyLoaderService propertyLoaderService;

	private MailSendService(IPropertyLoaderService propertyLoaderService) {
		this.propertyLoaderService = propertyLoaderService;
	}

	public static IMailSendService getInstance(IPropertyLoaderService propertyLoaderService) {
		if (instance == null)
			instance = new MailSendService(propertyLoaderService);
		return instance;
	}

	@Override
	public void sendMail(String receiver, String sender, String title, String content, File files) {
		try {
			Email from = new Email(sender);
			Email to = new Email(receiver);
			Content body = new Content("text/plain", content);
			Mail mail = new Mail(from, title, to, body);
			Attachments attachments = new Attachments();
			String encodedString = DatatypeConverter.printBase64Binary(Files.readAllBytes(files.toPath()));
			attachments.setFilename(files.getName());
			attachments.setContent(encodedString);
			attachments.setType("application/zip");
			attachments.setDisposition("attachment");
			mail.addAttachments(attachments);
			SendGrid sendGrid = new SendGrid(propertyLoaderService.getProperty("API_KEY"));
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			sendGrid.api(request);
		} catch (IOException e) {
			loggerService.logError("Error reading zip file", e);
		}
	}

}
