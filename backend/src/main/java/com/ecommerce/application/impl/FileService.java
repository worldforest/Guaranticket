package com.ecommerce.application.impl;

import com.ecommerce.application.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

@Service
public class FileService implements IFileService
{
	public static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Override
	public String uploadFile(MultipartFile file) {
		StringTokenizer st = new StringTokenizer(file.getOriginalFilename(), ".");
		String fileName = st.nextToken();
		String extension = st.nextToken();

		Date today = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat time = new SimpleDateFormat("hhmmss");

		String fileFullName = fileName + "_" + date.format(today) + time.format(today) + "." + extension;
	    System.out.println(fileFullName);
		try {
	    	// 서버에서 사용할때
//	    	FileCopyUtils.copy(file.getBytes(), new File("/home/ubuntu/dev/deploy/img/" + fileFullName));
	    	// 로컬에서 테스트할때
			FileCopyUtils.copy(file.getBytes(), new File("C:/"+fileFullName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileFullName;
	}
}
