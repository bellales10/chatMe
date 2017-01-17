package com.backend.mvc.dao;

import java.util.List;

import com.backend.mvc.model.BlogComment;
import com.backend.mvc.model.BlogPost;
import com.backend.mvc.model.User;

public interface BlogDao {

	List<BlogPost> getBlogPosts();
	BlogPost getBlogPost(int id);
	BlogPost addBlogPost(User user,BlogPost blogPost);
	List<BlogComment> getBlogComments(int blogId);
	BlogPost addBlogPostComment(User user,BlogComment blogComment);

}
