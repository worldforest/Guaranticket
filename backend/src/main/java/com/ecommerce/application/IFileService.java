package com.ecommerce.application;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
	@Transactional
	String uploadFile(MultipartFile file);
}