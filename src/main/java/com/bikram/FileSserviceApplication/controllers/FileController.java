package com.bikram.FileSserviceApplication.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikram.FileSserviceApplication.payloads.FileResponse;
import com.bikram.FileSserviceApplication.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	// setting the path where file needs to be uploaded : this path is declared at application.properties in the value of the key 'project.image' i.e. upto the folder
	@Value("${project.image}")
	private String folderPath;

	
	
	// upload file
	@PostMapping("/upload-file")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file){
		
		String uploadedFileName = null;
		
		try {
			uploadedFileName = this.fileService.uploadFile(folderPath, file);
		} catch (IOException e) {
			return new ResponseEntity<FileResponse>(new FileResponse(null, "Unable to Upload File !!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<FileResponse>(new FileResponse(uploadedFileName, "File uploaded successfully."), HttpStatus.OK);
	}
	
	
	
	// download File
	@GetMapping(value = "/download-file/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
		
		// calling the service and getting the file as InputStream
		InputStream resource = this.fileService.downloadFile(folderPath, fileName);
		
		// response
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		// copying resource (got from the service method call) to response (so that when we will fire the url, image should be displayed on the page)
		StreamUtils.copy(resource, response.getOutputStream());
		
	}
}
