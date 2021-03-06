package com.skilldistillery.learning.entities;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Comment comment;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("LearningPU");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		comment = em.find(Comment.class, 1);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		comment = null;
	}

	@Test
	@DisplayName("Test Comment entity mapping")
	void test1() {
		assertNotNull(comment);
		assertEquals("That is a ridiculous statement!", comment.getContent());
		
	}
	
	@Test
	@DisplayName("Test Comment to user relationship")
	void test2() {
		assertNotNull(comment);
		assertEquals("thwebel", comment.getUser().getUsername());
		assertEquals("Webel", comment.getUser().getLastName());
		assertTrue(comment.getUser().getEnabled());
	}
	
	@Test
	@DisplayName("Test Comment ManyToOne post_id Relationship")
	void test3() {
		assertNotNull(comment);
		assertEquals(1, comment.getPost().getId());
	}
	
	@Test
	@DisplayName("Test Comment OneToMany comment_vote Relationship")
	void test4() {
		assertNotNull(comment);
		assertTrue(comment.getCommentVote().get(0).getVote());
		
	}

}
