package com.spring.qburst.demoApp.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * http://www.java2s.com/example/java-book/compressing-byte-arrays.html
 * @author mayoora
 *
 */

public class AttachmentUtil {


	public static byte[] compressData(byte[] data) {
		
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4*1024];
		while(!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		
		try {
			outputStream.close();
		} catch (Exception ignored) {
			// TODO: handle exception
		}
		
		deflater.end();
		return outputStream.toByteArray();
		
	}
	
	public static byte[] deCompressData(byte[] data) {
		
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] tmp = new byte[4*1024];
		
		try {
			
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);
			}
			outputStream.close();
			
		}catch (Exception ignored) {
			// TODO: handle exception
		}
		
		return outputStream.toByteArray();
	}
	
}
