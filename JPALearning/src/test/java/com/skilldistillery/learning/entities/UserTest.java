package com.skilldistillery.learning.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private User user;
	
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
		user = em.find(User.class, 2);
		
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user = null;
	}

	@Test
	@DisplayName("Test User entity mapping")
	void test1() {
		assertNotNull(user);
		assertEquals("thwebel", user.getUsername());
		assertEquals("password", user.getPassword());
		assertEquals("Thomas", user.getFirstName());
		assertEquals("Webel", user.getLastName());
		assertTrue(user.getEnabled());
		
		
	}
	@Test
	@DisplayName("Test User to Comment Relationship")
	void test2() {
		assertNotNull(user);
		assertEquals("That is a ridiculous statement!", user.getComments().get(0).getContent());
	}

}
