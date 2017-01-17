package com.backend.mvc.dao;

import com.backend.mvc.model.UploadFile;

public interface FileUploadDao {
	
	void save(UploadFile uploadFile);
	UploadFile getFile(String username);

}
