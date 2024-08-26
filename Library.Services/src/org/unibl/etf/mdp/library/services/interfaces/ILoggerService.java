package org.unibl.etf.mdp.library.services.interfaces;

public interface ILoggerService {
	void logError(String message);
	
	void logInfo(String message);
	
	void logCritical(String message);
	
	void logError(String message, Exception ex);
	
	void logInfo(String message, Exception ex);
	
	void logCritical(String message, Exception ex);
}
