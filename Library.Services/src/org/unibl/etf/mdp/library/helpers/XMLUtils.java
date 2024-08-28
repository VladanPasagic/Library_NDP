package org.unibl.etf.mdp.library.helpers;

import java.io.File;
import java.io.IOException;

import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IPropertyLoaderService;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class XMLUtils {
	private ILoggerService loggerService;
	private IPropertyLoaderService propertyLoaderService;

	public XMLUtils(ILoggerService loggerService, IPropertyLoaderService propertyLoaderService) {
		this.loggerService = loggerService;
		this.propertyLoaderService = propertyLoaderService;
	}

	public <T> T read(String propertyFileName, Class<T> type) {
		try {
			JAXBContext context = JAXBContext.newInstance(type);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			File file = new File(propertyLoaderService.getProperty(propertyFileName));
			if (file.exists()) {
				T t = (T) unmarshaller.unmarshal(file);
				return t;
			}
			return null;
		} catch (JAXBException ex) {
			loggerService.logError("error occurred", ex);
		}
		return null;
	}

	public <T> void write(String propertyFileName, Class<T> type, T t) {
		try {
			JAXBContext context = JAXBContext.newInstance(type);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File(propertyLoaderService.getProperty(propertyFileName));
			if (file.exists() == false)
				file.createNewFile();
			marshaller.marshal(t, file);
		} catch (JAXBException ex) {
			loggerService.logError("error occurred", ex);
		} catch (IOException ex) {
			loggerService.logError("error occurred, ex");
		}
	}
}
