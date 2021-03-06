package com.skilldistillery.learning.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.skilldistillery.learning.entities.Comment;
import com.skilldistillery.learning.entities.User;

public interface CommentDAO {
	
	Comment findById(int commentId);
	
	List<Comment> findByUser(User user);
	
	List<Comment> findAll();
	
	Comment createComment(Comment newComment, Integer postId);
	
	Comment updateComment(Comment updatedComment, Integer id);
	
	boolean deleteComment(Comment targetComment, Integer commentId);
	
}
