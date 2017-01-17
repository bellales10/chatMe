package com.backend.mvc.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.mvc.dao.JobDao;
import com.backend.mvc.model.Error;
import com.backend.mvc.model.Job;
import com.backend.mvc.model.User;

@RestController
public class JobController {
	
	@Autowired
	private JobDao jobDao;
	
	//----------- RequestMapping for postJob methods for posting new job (using HTTP POST method) --------------//

	@RequestMapping(value="/postJob",method=RequestMethod.POST)
	public ResponseEntity<?> postJob(@RequestBody Job job,HttpSession session) {
     
	User user=(User)session.getAttribute("user"); 
	  if(user==null)
	   {
		  Error error=new Error(1,"Unauthorized user.. login using valid credentials");
		  return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED); //401
	   }
	  else
	   {
		  System.out.println(" Role of User " + user.getRole());
		  job.setPostedOn(new Date());
		  jobDao.postJob(job);
		  return new ResponseEntity<Void>(HttpStatus.OK);
	   }
	}
	
	//----------- RequestMapping for getAllJob methods to get list/view all jobs (using HTTP GET method) --------------//

    @RequestMapping(value="/getAllJobs",method=RequestMethod.GET)
    public ResponseEntity<?> getAllJobs(HttpSession session){
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		System.out.println("USER is null");
    		Error error=new Error(1,"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	System.out.println("USER OBJECT " + user.getRole());
    	List<Job> jobs=jobDao.getAllJobs();
    	return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
    	//response 
    }
    
	//------ RequestMapping for getJobDetail for getting job details of particular job (using HTTP GET method) --------//

    @RequestMapping(value="/getJobDetail/{jobId}",method=RequestMethod.GET)
    public ResponseEntity<?> getJobDetail(@PathVariable(value="jobId")int jobId,
    		HttpSession session){
    	User user=(User)session.getAttribute("user");
    	if(user==null){
    		System.out.println("USER is null");
    		Error error=new Error(1,"Unauthorized user.. login using valid credentials");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//401
    	}
    	Job jobDetail=jobDao.getJobDetail(jobId);
    	return new ResponseEntity<Job>(jobDetail,HttpStatus.OK);
    }
}
