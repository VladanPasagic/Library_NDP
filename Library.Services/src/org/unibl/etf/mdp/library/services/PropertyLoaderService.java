package org.unibl.etf.mdp.library.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

public class PropertyLoaderService implements IPropertyLoaderService{
	
	private static IPropertyLoaderService instance;
	private Properties properties;
	
	private PropertyLoaderService(Properties properties)
	{
		this.properties = properties;
	}
	
	public static IPropertyLoaderService load(ILoggerService loggerService) {
		if (instance != null)
			return instance;
		
		Properties properties = new Properties();
		try {
			InputStream input = new FileInputStream("properties.prop");
			properties.load(input);
			input.close();
		} catch (IOException ex) {
			loggerService.logCritical("Couldn't load properties", ex);
		}
		instance = new PropertyLoaderService(properties);
		return instance;
	}

	@Override
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
}
