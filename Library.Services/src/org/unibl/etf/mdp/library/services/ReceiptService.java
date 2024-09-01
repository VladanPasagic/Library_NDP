package org.unibl.etf.mdp.library.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Random;

import org.unibl.etf.mdp.library.entities.ReceiptEntity;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;
import org.unibl.etf.mdp.library.services.interfaces.IReceiptService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReceiptService implements IReceiptService {

	private ILoggerService loggerService = LoggerService.getLogger(getClass().getName());

	public ReceiptService() {
	}

	@Override
	public void saveReceipt(ReceiptEntity receipt) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		String json = gson.toJson(receipt);
		String path = System.getProperty("user.dir") + "/Receipts/";
		File folder = new File(path);
		if (folder.exists() == false)
			folder.mkdirs();
		File file = new File(path + "Receipt" + new Random().nextInt(1_000_000)+".json");
		try {
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			pw.append(json);
			pw.close();
		} catch (FileNotFoundException e) {
			loggerService.logError("File not found", e);
		} catch (IOException ex) {
			loggerService.logError("Error accessing file", ex);
		}

	}

}
