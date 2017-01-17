package com.backend.mvc.controller;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.backend.mvc.dao.FileUploadDao;
import com.backend.mvc.model.UploadFile;
import com.backend.mvc.model.User;

//  Handles requests for the file upload page.
 
@RestController
public class FileUploadController {
	@Autowired
	private FileUploadDao fileUploadDao;
	
	@RequestMapping(value="/" , method=RequestMethod.GET)
	public String showUploadForm(HttpServletRequest request) {
		return "Upload";
	}
    
	//----------- RequestMapping for handleFileUpload ---------------//
	
    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request,
    		HttpSession session,
            @RequestParam CommonsMultipartFile fileUpload) throws Exception {
         User user=(User)session.getAttribute("user");
         if(user==null)
        	 throw new RuntimeException("Not logged in");
         System.out.println("USER is " + user.getUsername());
         
         if (fileUpload != null ) {
             CommonsMultipartFile aFile = fileUpload;
            
                System.out.println("Saving file: " + aFile.getOriginalFilename());
                
                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(aFile.getOriginalFilename());
                uploadFile.setData(aFile.getBytes());//image 
                uploadFile.setUsername(user.getUsername());//login details
                fileUploadDao.save(uploadFile);
                
               UploadFile getUploadFile=fileUploadDao.getFile(user.getUsername());
               	String name=getUploadFile.getFileName();
           	    System.out.println(getUploadFile.getData());
           	     byte[] imagefiles=getUploadFile.getData();
            	try{
            		String path="C:/Users/Danish/workspace/Backend/src/main/webapp/WEB-INF/resources/images/" +user.getUsername();
                    File file=new File(path);
            		// file.mkdirs();
            		FileOutputStream fos = new FileOutputStream(file);
            		fos.write(imagefiles);
                    fos.close();
               		}catch(Exception e){
            		e.printStackTrace();
            		}
             }

                return "Successfully uploaded the Profile Picture";
     }	
	
    //------------- RequestMapping for getFile from directory --------------//
    
    	@RequestMapping(value="/getFile",method=RequestMethod.GET)
    	public ResponseEntity<?> getFile(HttpSession session){
	   User user=(User)session.getAttribute("user");
    	UploadFile uploadFile=fileUploadDao.getFile(user.getUsername());
    	String name=uploadFile.getFileName();
    	System.out.println(uploadFile.getData());
    	byte[] imagefiles=uploadFile.getData();

    	return new ResponseEntity<byte[]>(imagefiles,HttpStatus.OK);
    }
	

}
