package org.unibl.etf.mdp.library.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

public class LoggerService implements ILoggerService {

	private static ILoggerService instance = null;
	private Logger logger;

	private LoggerService(String className) {
		try {
			logger = Logger.getLogger(className);
			FileHandler fileHandler = null;
			System.out.println(System.getProperty("user.home"));
			File file = new File(System.getProperty("user.home") + File.separatorChar + "application.log");
			fileHandler = new FileHandler(file.getPath(), true);
			fileHandler.setLevel(Level.INFO);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (IOException ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}

	public static ILoggerService getLogger(String classname) {
		if (instance == null) {
			instance = new LoggerService(classname);
		}
		return instance;
	}

	@Override
	public void logError(String message) {
		logger.log(Level.WARNING, message);
	}

	@Override
	public void logInfo(String message) {
		logger.log(Level.INFO, message);
	}

	@Override
	public void logCritical(String message) {
		logger.log(Level.SEVERE, message);
	}

	@Override
	public void logError(String message, Exception ex) {
		logger.log(Level.WARNING, message, ex);
	}

	@Override
	public void logInfo(String message, Exception ex) {
		logger.log(Level.INFO, message, ex);
	}

	@Override
	public void logCritical(String message, Exception ex) {
		logger.log(Level.SEVERE, message, ex);
	}

}
