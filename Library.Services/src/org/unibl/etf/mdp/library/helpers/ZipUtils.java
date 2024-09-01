package org.unibl.etf.mdp.library.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.unibl.etf.mdp.library.services.LoggerService;
import org.unibl.etf.mdp.library.services.interfaces.ILoggerService;

public class ZipUtils {
	private static ILoggerService loggerService = LoggerService.getLogger(ZipUtils.class.getName());

	public static File zipFiles(String fileName, List<String> files) {
		try {
			String path = System.getProperty("user.home") + "/ZIPS/";
			File dir = new File(path);
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			File file = new File(path + fileName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String src : files) {
				File bookFile = new File(src);
				FileInputStream fis = new FileInputStream(bookFile);
				ZipEntry entry = new ZipEntry(bookFile.getName());
				zos.putNextEntry(entry);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zos.write(bytes, 0, length);
				}

				fis.close();
			}
			zos.close();
			fos.close();
			return file;
		} catch (FileNotFoundException e) {
			loggerService.logError("File not found", e);
		} catch (IOException ex) {
			loggerService.logError("Exception writing to file", ex);
		}
		return null;
	}
}
