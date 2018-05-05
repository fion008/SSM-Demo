package com.test.service;

import org.springframework.web.multipart.MultipartFile;

import com.test.response.AppResponse;

public interface FileUploadService {

	AppResponse fileUpload(String path, MultipartFile[] file);

}
