package org.unibl.etf.mdp.library.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import org.unibl.etf.mdp.library.entities.BookEntity;
import org.unibl.etf.mdp.library.services.interfaces.IGutenbergService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

public class GutenbergService implements IGutenbergService {

	private static IGutenbergService instance = null;
	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());

	private GutenbergService() {

	}

	public static IGutenbergService getGutenbergService() {
		if (instance == null)
			instance = new GutenbergService();
		return instance;
	}

	@Override
	public BookEntity getBook(String url) {
		BookEntity entity = new BookEntity();
		String[] urlParts = url.split("/");
		try {
			URL book = new URI(url).toURL();
			BufferedReader reader = new BufferedReader(new InputStreamReader(book.openStream()));
			String workingDirectory = System.getProperty("user.home");
			File dir = new File(workingDirectory + File.separatorChar + "Books");
			if (dir.exists() == false)
				dir.mkdirs();
			File file = new File(dir.getPath() + File.separatorChar + urlParts[urlParts.length - 1]);
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			String line = "";
			while ((line = reader.readLine()) != null) {
				lineChecker(entity, line);
				pw.println(line);
			}
			pw.close();
			entity.setContent(file.getPath());
			entity.setFrontPageLink(getFrontPageLink(url));
			entity.setId(UUID.randomUUID());
		} catch (MalformedURLException ex) {
			loggerService.logError("Malformed URL", ex);
		} catch (URISyntaxException ex) {
			loggerService.logError("Error in URI syntax", ex);
		} catch (IOException ex) {
			loggerService.logError("Exception while opening stream", ex);
		}
		return entity;
	}

	private BookEntity lineChecker(BookEntity entity, String line) {
		if (line.startsWith("Title")) {
			entity.setName(line.substring(7));
		} else if (line.startsWith("Author")) {
			entity.setAuthor(line.substring(8));
		} else if (line.toLowerCase().startsWith("Release date".toLowerCase())) {
			entity.setReleaseDate(line.substring(14));
		} else if (line.startsWith("Language")) {
			entity.setLanguage(line.substring(10));
		} else if (line.startsWith("ISBN")) {
			entity.setISBN(line.substring(6));
		}
		return entity;
	}

	private String getFrontPageLink(String url) {
		String[] urlParts = url.split("\\.");
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < urlParts.length - 1; i++) {
			ret.append(urlParts[i]);
		}
		return ret.append(".cover.medium.jpg").toString();
	}

}
