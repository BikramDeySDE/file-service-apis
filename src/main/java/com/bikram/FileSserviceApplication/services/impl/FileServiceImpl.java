package com.bikram.FileSserviceApplication.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bikram.FileSserviceApplication.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	// upload file
	@Override
	public String uploadFile(String folderPath, MultipartFile file) throws IOException { 
		
		// File name ( suppose 'abc.png')
		String fileName = file.getOriginalFilename();
		
		// rename file ('xyz')
		String randomFileName = UUID.randomUUID().toString(); // random name
		
		// new file name ('xyz.png')
		String newFileName = randomFileName.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		// complete Path of the folder where file needs to be uploaded
		String completeFilePath = folderPath + File.separator + newFileName; // '......{Folder-Path}/xyz.png
		
		// create Folder if not created
		File f = new File(folderPath); // here folder object is created, here path is upto the folder (not upto the file) (...{Folder-Path})
		
		if (!f.exists()) { // if the folder is not exist in this path
			f.mkdir();	// create folder
		}
		
		// save the file into the folder (completeFilePath - ../images/xyz.png'') : storing the file into the 'completeFilePath' : the new file name comes from the 'completeFilePath'
		Files.copy(file.getInputStream(), Paths.get(completeFilePath));
		
		// return original file name
		return fileName;
	}

	
	// download file
	@Override
	public InputStream downloadFile(String folderPath, String imageName) throws FileNotFoundException {
		
		// complete filePath
		String completeFilePath = folderPath + File.separator + imageName;
		
		// get the file
		InputStream is = new FileInputStream(completeFilePath);
		
		return is;
	}

}
