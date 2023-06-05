package com.spring.qburst.demoApp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.qburst.demoApp.exception.AttachmentException;
import com.spring.qburst.demoApp.service.AttachmentService;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;
	
	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws java.io.IOException  {
		
		String uploadedFile  = attachmentService.uploadAttachment(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadedFile);
				
	}
	
	@GetMapping("/downloadFile/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) {
		
		byte[] fileData = attachmentService.downloadAttachment(fileName);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(fileData);
	}
	
	@PostMapping("/uploadToFileServer")
	public ResponseEntity<?> uploadToFileServer(@RequestParam("file") MultipartFile file) throws java.io.IOException  {
		
		String uploadedFile  = attachmentService.uploadToFileServer(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadedFile);
				
	}
	
	@GetMapping("/downloadFromFileServer/{fileName}")
	public ResponseEntity<?> downloadFromFileServer(@PathVariable("fileName") String fileName) {
		
		byte[] fileData = attachmentService.downloadFromFileServer(fileName);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(fileData);
	}
	
	 @PostMapping("/uploadFiles")
	  public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
	   
		String message = "";
	    
	    try {
	    	
	      Arrays.asList(files).stream().forEach(file -> {
	    	  try {
				uploadToFileServer(file);
			} catch (IOException e) {
				throw new AttachmentException(e.getMessage());
			}
		});

	      return ResponseEntity.status(HttpStatus.OK).body(message);
	    } catch (Exception e) {
	      message = "Fail to upload files!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
	    }
	  }

	
}


