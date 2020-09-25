package com.ecommerce.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.application.IFileService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api")
public class FileController {
	public static final Logger logger = LoggerFactory.getLogger(FileController.class);

	private IFileService fileService;
	
	@Autowired
	public FileController(IFileService fileService) {
		this.fileService = fileService;
	}
	
	@GetMapping(value = "/file/{imgname}", produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable final String imgname) throws IOException {
		InputStream in = new FileInputStream("/home/ubuntu/deploy/img/"+imgname);
//		InputStream in = new FileInputStream("/C:/"+imgname);
		return IOUtils.toByteArray(in);
	}
	
	@ApiOperation(value = "이미지파일 업로드")
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String uploadImgFile(@RequestBody MultipartFile file){
		return fileService.uploadFile(file);
	}
}
