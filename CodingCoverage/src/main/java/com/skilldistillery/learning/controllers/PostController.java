package com.skilldistillery.learning.controllers;

import java.util.Iterator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skilldistillery.learning.dao.LanguageDAO;
import com.skilldistillery.learning.dao.PostDAO;
import com.skilldistillery.learning.dao.UserDAO;
import com.skilldistillery.learning.entities.Comment;
import com.skilldistillery.learning.entities.Language;
import com.skilldistillery.learning.entities.Post;
import com.skilldistillery.learning.entities.User;

@Controller
public class PostController {
	
// **||**************************************************************************************************************************************||**
// **||* TEMPLATE TO ANNOTATE CHANGED RETURNS FOR BACKDOOR REDIRECTS ON MAJOR IMPLEMENTATION CHANGES (First Last Initial . 1/1/11 @ 11:11AM)*||**
// **\/**************************************************************************************************************************************\/**
	
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private LanguageDAO langDao;
	
	@Autowired
	private UserDAO userDao;
	
//  redirect:ViewForum.do from createForumPost.do
	@RequestMapping(path="ViewForum.do")
	public String viewForum(Model model) {
		model.addAttribute("posts", postDAO.findAll());
		return "forms/ViewForum";
	}
	
	@RequestMapping(path="searchFilters.do", params= {"keyword", "language_id"})
	public String searchFilters(String keyword, @RequestParam("language_id")Integer languageId, Model model) {
		model.addAttribute("posts", postDAO.filterByLanguageAndKeyword(keyword, languageId));
		return "forms/ViewForum";
	}
	
	@RequestMapping(path="ViewExpertForum.do")
	public String viewExpertForum(Model model) {
		model.addAttribute("posts", postDAO.findAll());
		return "forms/ViewExpertForum";
//		return "results/ViewForumSinglePostSandbox";
	}	
	
	@RequestMapping(path="searchFiltersExpertForum.do", params= {"keyword", "language_id"})
	public String searchFiltersExpertForum(String keyword, @RequestParam("language_id")Integer languageId, Model model) {
		model.addAttribute("posts", postDAO.filterByLanguageAndKeyword(keyword, languageId));
		return "forms/ViewExpertForum";

	}

	@RequestMapping(path="viewForumPost.do", params= "id")
	public String viewForumPost(Model model, @RequestParam("id")Integer postId) {
		model.addAttribute("post", postDAO.viewPost(postId));
		return "results/ViewForumPost";
//		return "results/ViewForumPostSandbox";
	}
	
	@RequestMapping(path="getForumPostForm.do")
	public String getForumPostForm(Model model, Post post) {
		
		return "forms/CreateForumPost";
	}
	// Expert Forum Post
	@RequestMapping(path="getExpertPostForm.do")
	public String getExpertPostForm(Model model, Post post) {
		
		return "forms/CreateExpertForumPost";
	}
	// Personal Post
	@RequestMapping(path="getPersonalPostForm.do")
	public String getPersonalPostForm(Model model, Post post) {
		
		return "forms/CreateBlogPost";
	}
	// Create Forum Post
	@RequestMapping(path="createForumPost.do", method=RequestMethod.POST)
	public String createForumPost(Model model, @Valid Post post, Errors errors, HttpSession session, Integer langId) {
		
		if(errors.getErrorCount()>0) {
			return "forms/CreateForumPost";
		}
		
		User thisUser = (User)session.getAttribute("user");
		post.setUser(thisUser);
		
		Language lang = langDao.findById(langId);
		post.setLanguage(lang);
		
		postDAO.createPost(post);
		session.setAttribute("user", userDao.findById(thisUser.getId()));
		
		if(post.getIsExpert()) {
			return "redirect:ViewExpertForum.do";
		}
		if(post.getIsBlog()) {
			return "redirect:updatePostBlogRedir.do";
		}
		return "redirect:ViewForum.do";
	}
	
// UPDATE POST SECTION
	
	@RequestMapping(path="updatePostForm.do", method=RequestMethod.GET)
	public String getUpdatePostForm(Model model, int postId) {
		
		Post post = postDAO.findById(postId);
		model.addAttribute("post", post);
		if (post.getIsForumVisable()) {
			return "forms/EditForumPost";
			
		} else if(post.getIsBlog()) {
			return "forms/EditBlogPost";
			
		} else if(post.getIsExpert()) {
			return "forms/EditExpertPost";
			
		}
		// Should never be hit
		return "results/ProfilePage";
	}
	
	@RequestMapping(path="updatePost.do", method=RequestMethod.POST)
	public String updatePost(Model model, @Valid Post updatedPost, Errors errors, HttpSession session, Integer langId, RedirectAttributes redir) {
		
		// Set date created
		Post dbPost = postDAO.findById(updatedPost.getId());
		
		// Set lang_id
		Language lang = langDao.findById(langId);
		dbPost.setLanguage(lang);
		
		
		dbPost.setSubject(updatedPost.getSubject());
		dbPost.setContent(updatedPost.getContent());
		
		// Merge new post
		postDAO.updatePost(dbPost);
		
		// Refresh User 
		User thisUser = (User)session.getAttribute("user");
		User user = userDao.findById(thisUser.getId());
		session.setAttribute("user", user);
		
		redir.addFlashAttribute("post", dbPost);
		
		//Redir to proper Forum
		if (dbPost.getIsForumVisable()) {
			return "redirect:ViewForum.do";
			
		} else if(dbPost.getIsBlog()) {
			return "redirect:updatePostBlogRedir.do";
			
		} else if(dbPost.getIsExpert()) {
			return "redirect:ViewExpertForum.do";
		}
		
		return "redirect:updatePostBlogRedir.do";
	}
	
	// Personal Page Redir
	@RequestMapping(path="updatePostBlogRedir.do", method=RequestMethod.GET)
	public String updateBRedir() {
		
		return "results/ProfilePage";
	}
	
// END UPDATE POST SECTION
// DELETE 
	@RequestMapping(path = "deletePost.do" )
	public String deleteComment(Model model, Integer postId, HttpSession session) {
		
		postDAO.deletePost(postId);
		
		//Refresh User
		User thisUser = (User)session.getAttribute("user");
		session.setAttribute("user", userDao.findById(thisUser.getId()));
		
		return "redirect:updatePostBlogRedir.do";
	}

}














