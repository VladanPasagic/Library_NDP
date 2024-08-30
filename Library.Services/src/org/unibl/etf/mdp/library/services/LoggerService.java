package org.unibl.etf.mdp.library.services;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

public class LoggerService implements ILoggerService {

	private static ILoggerService instance = null;
	private FileHandler fileHandler;
	private Logger logger;

	private LoggerService(String className) {
		logger = Logger.getLogger(className);
	}

	private void registerFileHandler() {
		try {
			File file = new File(System.getProperty("user.home") + File.separatorChar + "application.log");
			fileHandler = new FileHandler(file.getPath(), true);
			fileHandler.setLevel(Level.INFO);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void closeFileHandler() {
		if (fileHandler != null)
			fileHandler.close();
	}

	public static ILoggerService getLogger(String classname) {
		if (instance == null) {
			instance = new LoggerService(classname);
		}
		return instance;
	}

	@Override
	public void logError(String message) {
		registerFileHandler();
		logger.log(Level.WARNING, message);
		closeFileHandler();
	}

	@Override
	public void logInfo(String message) {
		registerFileHandler();
		logger.log(Level.INFO, message);
		closeFileHandler();
	}

	@Override
	public void logCritical(String message) {
		registerFileHandler();
		logger.log(Level.SEVERE, message);
		closeFileHandler();
	}

	@Override
	public void logError(String message, Exception ex) {
		registerFileHandler();
		logger.log(Level.WARNING, message, ex);
		closeFileHandler();
	}

	@Override
	public void logInfo(String message, Exception ex) {
		registerFileHandler();
		logger.log(Level.INFO, message, ex);
		closeFileHandler();
	}

	@Override
	public void logCritical(String message, Exception ex) {
		registerFileHandler();
		logger.log(Level.SEVERE, message, ex);
		closeFileHandler();
	}

}
