package br.com.citcase.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public final class FileIO {

	/**
	 * Read File in test repository 
	 * 
	 * @param file
	 * @return String content
	 * @throws IOException
	 */
	public static String get(String file) throws IOException {

		ClassLoader classLoader = FileIO.class.getClassLoader();
		InputStream inputStream = new FileInputStream(classLoader.getResource(
				file).getFile());
		return IOUtils.toString(inputStream);

	}
}
