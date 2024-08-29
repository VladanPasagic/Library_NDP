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
			FileOutputStream fos = new FileOutputStream(fileName);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for (String src : files) {
				File file = new File(src);
				FileInputStream fis = new FileInputStream(file);
				ZipEntry entry = new ZipEntry(file.getName());
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ex) {
		}

		File file = new File(fileName);
		return file;
	}
}
