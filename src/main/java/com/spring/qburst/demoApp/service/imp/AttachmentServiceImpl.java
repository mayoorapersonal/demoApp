package com.spring.qburst.demoApp.service.imp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.qburst.demoApp.exception.AttachmentException;
import com.spring.qburst.demoApp.model.Attachment;
import com.spring.qburst.demoApp.repository.AttachmentRepository;
import com.spring.qburst.demoApp.service.AttachmentService;
import com.spring.qburst.demoApp.util.AttachmentUtil;

@Service
public class AttachmentServiceImpl implements AttachmentService{
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Value("${documentRootFolder}")
	private String fileServerPath;
	
	@Override
	public String uploadAttachment(MultipartFile file) throws IOException {
		
		try {
			Attachment attachment =	attachmentRepository.save(Attachment.builder().name(file.getOriginalFilename())
								.size(file.getSize())
								.attachmentType(file.getContentType())
								.attachment(AttachmentUtil.compressData(file.getBytes()))
								.build());
			 
				if(null != attachment) {
					Object[] args = {file.getOriginalFilename()};
					return messageSource.getMessage("SUCCESS_FILE_UPLOADED", args, null) ;
				} 
					
		} catch (AttachmentException e) {
			throw new AttachmentException(e.getMessage());
		} catch (Exception e) {
			throw new AttachmentException(e.getMessage());
		}
	
	return null;
	}

	@Override
	public byte[] downloadAttachment(String fileName) {
		
		byte[] data = new byte[] {};
		Optional<Attachment> attachment = attachmentRepository.findByName(fileName);
		
		
		try {
			if(attachment.isPresent()) {
				data = AttachmentUtil.deCompressData(attachment.get().getAttachment());
			}else 
				throw new AttachmentException(messageSource.getMessage("ATTACHMENT_NOT_FOUND", null, null));
		} catch (Exception e) {
			throw new AttachmentException(e.getMessage());
		}
			
		return data;
	}

	@Override
	public String uploadToFileServer(MultipartFile file) throws IOException {
		
		String filePath = fileServerPath + "/" +  file.getOriginalFilename();
		Attachment attachment = attachmentRepository.save(Attachment.builder().name(file.getOriginalFilename())
									.size(file.getSize())
									.attachmentType(file.getContentType())
									.filePath(filePath)
									.build());
		
		file.transferTo(new File(filePath));
		
		if(null != attachment) {
			Object[] args = {file.getOriginalFilename()};
			return messageSource.getMessage("SUCCESS_FILE_UPLOADED", args, null) ;
		} else
			return null;
	}

	@Override
	public byte[] downloadFromFileServer(String fileName) {
		
		byte[] data = new byte[] {};
		Optional<Attachment> attachment = attachmentRepository.findByName(fileName);
		
		
		try {
			if(attachment.isPresent()) {
				var filePath  = attachment.get().getFilePath();
				data = Files.readAllBytes(new File(filePath).toPath());
			}else 
				throw new AttachmentException(messageSource.getMessage("ATTACHMENT_NOT_FOUND", null, null));
		} catch (Exception e) {
			throw new AttachmentException(e.getMessage());
		}
			
		return data;
		
	}

	
}
