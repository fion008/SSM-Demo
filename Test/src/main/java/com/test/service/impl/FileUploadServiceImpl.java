package com.test.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.base.exception.BaseException;
import com.test.base.tool.FileUtils;
import com.test.base.tool.ImageUtils;
import com.test.entity.File;
import com.test.response.AppResponse;
import com.test.service.FileUploadService;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

	public AppResponse fileUpload(String path, MultipartFile[] file) {
		return fileUpload1(path, file);
	}

	public AppResponse fileUpload1(String subpath, MultipartFile[] file) {
		try {
			ArrayList<Object> list = new ArrayList<Object>(file.length);
			if (file != null && file.length > 0) {
				for (MultipartFile multipartFile : file) {
					File f = writeUploadFile(multipartFile, subpath);
					String oName = multipartFile.getOriginalFilename();
					Map<String, Object> fileResp = new HashMap<>();
					fileResp.put("oldName", oName);
					fileResp.put("path", f.getPath());
					list.add(fileResp);
				}
			}
			return AppResponse.okData(list);
		} catch (Exception e) {
			throw new BaseException("上传文件出错");
		}
	}

	public File writeUploadFile(MultipartFile mf, String subpath)
			throws IOException {
		byte[] filedata = mf.getBytes();
		File file = null;

		// if (file == null) {
		file = new File();
		file.setSourceName(mf.getOriginalFilename()); // 原始文件名
		// file.setMd5(md5); // 文件md5签名
		// file.setFileType(FileTypeDict.getFileExtension(filedata)); //
		// 文件类型

		String path = "E:/photo/";
		path = path + subpath + "/";
		String originPath = path;
		String filename = FileUtils.writeFile(originPath, filedata);
		int pos;
		String zippedName = (pos = filename.lastIndexOf(".")) >= 0 ? filename
				.substring(0, pos) + "_z." + filename.substring(1 + pos)
				: filename + "_z.";
		ImageUtils.zoomImageScale(new java.io.File(originPath + filename),
				originPath + zippedName, 0);

		file.setPath(path + zippedName); // 文件路径
		// }
		return file;
	}

}
