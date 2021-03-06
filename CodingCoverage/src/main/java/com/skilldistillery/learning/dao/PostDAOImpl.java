package com.skilldistillery.learning.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.skilldistillery.learning.entities.Comment;
import com.skilldistillery.learning.entities.Language;
import com.skilldistillery.learning.entities.Post;
import com.skilldistillery.learning.entities.User;

@Service
@Transactional
public class PostDAOImpl implements PostDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Post findById(int postId) {
		Post post = em.find(Post.class, postId);
		if (post.getComments() != null) {
			for (Comment comment : post.getComments()) {
				if (comment.getCommentVote() != null) {
					comment.getCommentVote().size();
				}
			}
		}
		return post;
	}

	@Override
	public List<Post> findByUser(User user) {
		String getUsersPost = "SELECT p FROM Post p WHERE p.user.id = :userId";
		
		int id = user.getId();
		
		List<Post> posts = em.createQuery(getUsersPost, Post.class)
				.setParameter("userID", id)
				.getResultList();
		
		return posts;
	}

	@Override
	public List<Post> findAll() {
		String getAllPost = "SELECT p FROM Post p";

		List<Post> allPosts = em.createQuery(getAllPost, Post.class).getResultList();

		return allPosts;
	}

	@Override
	public Post createPost(Post newPost) {
		
		newPost.setDateCreated(LocalDateTime.now());
		newPost.setLastUpdate(LocalDateTime.now());
		
		em.persist(newPost);
		em.flush();
		
		return newPost;
	}

	@Override
	public Post updatePost(Post updatedPost) {
		
		updatedPost.setLastUpdate(LocalDateTime.now());
		
		return em.merge(updatedPost);
	}

	@Override
	public boolean deletePost(Integer postId) {
		boolean deleted = false;
		Post targetPost = em.find(Post.class, postId);
		
		if (targetPost != null) {
			em.remove(targetPost);
		}
		deleted = !em.contains(targetPost);

		return deleted;
	}

	@Override
	public List<Post> filterByLanguageAndKeyword(String keyword, Integer languageId) {
		List<Post> post = null;
		keyword = "%" + keyword + "%";
		if (languageId < 1) {
			String keywordQuery = "SELECT p FROM Post p WHERE p.content LIKE :keyword";
			post = em.createQuery(keywordQuery, Post.class).setParameter("keyword", keyword).getResultList();
			return post;
		}
		else {
			String languageKeywordQuery = "SELECT DISTINCT p FROM Post p JOIN FETCH p.language WHERE p.language.name LIKE :language AND p.content LIKE :keyword";
			Language language = em.find(Language.class, languageId);
			String languageName = "%" + language.getName() + "%";
			post = em.createQuery(languageKeywordQuery, Post.class).setParameter("keyword", keyword).setParameter("language", languageName).getResultList();
			return post;
		}
	}

	@Override
	public Post viewPost(int postId) {
		Post post = em.find(Post.class, postId);
		if (post.getComments() != null) {
			for (Comment comment : post.getComments()) {
				if (comment.getCommentVote() != null) {
					comment.getCommentVote().size();
				}
			}
		}
		return post;
	
	}
}
