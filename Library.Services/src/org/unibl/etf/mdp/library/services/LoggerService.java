package org.unibl.etf.mdp.library.services;

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

	private <T> LoggerService(String className, boolean isWebProject, ClassLoader loader) {
		try {
			logger = Logger.getLogger(className);
			FileHandler fileHandler = null;
			if (isWebProject) {
				System.out.println(loader.getName());
				String logFilePath = loader.getResource("../application.log").getPath();
				fileHandler = new FileHandler(logFilePath.substring(1), true);
			} else {
				fileHandler = new FileHandler("application.log", true);
			}
			fileHandler.setLevel(Level.INFO);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (IOException ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}
	}

	public static <T> ILoggerService getLogger(String classname, boolean isWebProject, ClassLoader loader) {
		if (instance == null) {
			instance = new LoggerService(classname, isWebProject, loader);
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
