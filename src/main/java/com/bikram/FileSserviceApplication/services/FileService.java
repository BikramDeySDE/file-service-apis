package com.bikram.FileSserviceApplication.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	// upload file
	String uploadFile(String folderPath, MultipartFile file) throws IOException;
	
	// download file
	InputStream downloadFile(String folderPath, String imageName) throws FileNotFoundException;
}
