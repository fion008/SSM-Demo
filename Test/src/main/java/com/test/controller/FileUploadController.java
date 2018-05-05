package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.common.MediaType;
import com.test.response.AppResponse;
import com.test.service.FileUploadService;

@Controller
@RequestMapping("/file")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
	public AppResponse fileUpload(@RequestParam("path") String path,
			@RequestParam(value = "file", required = false) MultipartFile[] file) {

		return fileUploadService.fileUpload(path, file);
	}

}
