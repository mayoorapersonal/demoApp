package com.spring.qburst.demoApp.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

	public String uploadAttachment(MultipartFile file) throws IOException ;
	
	public byte[] downloadAttachment(String fileName);
	
	public String uploadToFileServer(MultipartFile file) throws IOException ;
	
	public byte[] downloadFromFileServer(String fileName);
}
