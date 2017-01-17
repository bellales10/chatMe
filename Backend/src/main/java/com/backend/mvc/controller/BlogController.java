package com.backend.mvc.controller;

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

import com.backend.mvc.dao.BlogDao;
import com.backend.mvc.model.BlogComment;
import com.backend.mvc.model.BlogPost;
import com.backend.mvc.model.User;
import com.backend.mvc.model.Error;

@RestController
@RequestMapping("/blog")
public class BlogController {
@Autowired
private BlogDao blogDao;
	
    //-------- RequestMapping for getBlogList method to get All blogs list ------//

	@RequestMapping(value = "/list" ,method = RequestMethod.GET)
	public ResponseEntity<?> getBlogList(HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogPosts=blogDao.getBlogPosts();
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
	}
	
    //-------- RequestMapping for getBlogPost ------//

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlogPost(@PathVariable(value="id") int id,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogPost=blogDao.getBlogPost(id);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
    //-------- RequestMapping for addBlogPost ------//

	@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addBlogPost( @RequestBody BlogPost blogPost,HttpSession session) {
		User user=(User)session.getAttribute("user");
		if(user==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
         BlogPost addedBlogPost= blogDao.addBlogPost(user, blogPost);
         return new ResponseEntity<BlogPost>(addedBlogPost,HttpStatus.OK);
    }
	
    //-------- RequestMapping for getBlogComments ------//

	@RequestMapping(value="/getcomments/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogComments(@PathVariable(value="blogId")int blogId,HttpSession session){
		User user=(User)session.getAttribute("user");
		if(user==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogComment> blogComments=blogDao.getBlogComments(blogId);
		System.out.println("BLOGCOMMENTS::: " + blogComments.size() );
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);		
	}
	
    //-------- RequestMapping for addBlogComment ------//

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseEntity<?> addBlogComment( @RequestBody BlogComment blogComment,HttpSession session) {
    	User user=(User)session.getAttribute("user");
		if(user==null){
			Error error=new Error(1,"Unauthroized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		System.out.println("BLOG COMMENT is " + blogComment);
		System.out.println("BLOG COMMENT BODY " + blogComment.getBody());
		
		System.out.println("BLOG POST FROM BLOGCOMMENT " + blogComment.getBlogPost());
		BlogPost blogPost=blogDao.getBlogPost(blogComment.getBlogPost().getId());
		if(blogPost==null){
			Error error=new Error(2,"Blogpost not found");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
		}
        BlogPost createdBlogPost= blogDao.addBlogPostComment(user, blogComment);
        return new ResponseEntity<BlogPost>(createdBlogPost,HttpStatus.OK);
    }
	
}
