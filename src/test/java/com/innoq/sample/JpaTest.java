/**
 * 
 */
package com.innoq.sample;

import static org.junit.Assert.*;

import java.sql.Date;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

/**
 * @author oberschulte
 *
 */
public class JpaTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAProject");
		EntityManager em = emf.createEntityManager();
		
		try {
		Address address = new Address("Germany","Monheim","Krischerstr.");
		User user = new User("Test",false);
		User user2 = new User("Test2",false);
		User user3 = new User("Test3",false);
		User user4 = new User("Test4",false);
		User user5 = new User("Test5",false);
		
		Event event1 = new Event("Pommes", "", Date.valueOf("2012-10-17"), 10, false);
		
		Booking booking1 = new Booking(event1, user, false);
		Booking booking2 = new Booking(event1, user2, false);
		Booking booking3 = new Booking(event1, user3, false);
		
		event1.addBooking(booking1);
		event1.addBooking(booking2);
		event1.addBooking(booking3);
		
		em.getTransaction().begin();
		
		user.setAddress(address);
		user2.setAddress(address);
		user3.setAddress(address);
		user4.setAddress(address);
		user5.setAddress(address);
		
		Project project = new Project("Projekt1");
		Project project2 = new Project("Projekt2");
		
		user.addProject(project);
		user2.addProject(project2);
		user3.addProject(project);
		user4.addProject(project2);
		user5.addProject(project);
		user.addProject(project2);
		user2.addProject(project);
		user3.addProject(project2);
		user4.addProject(project);
		user5.addProject(project2);
		
		project.addUser(user);
		project.addUser(user2);
		project.addUser(user3);
		project.addUser(user4);
		project.addUser(user5);
		project2.addUser(user);
		project2.addUser(user2);
		project2.addUser(user3);
		project2.addUser(user4);
		project2.addUser(user5);
		
		em.persist(user);
		em.persist(user2);
		em.persist(user3);
		em.persist(user4);
		em.persist(user5);
		em.persist(event1);
		
		em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	@Test
	public void oneToOneTest() {
		String query = "SELECT u FROM User u";
		for (User u : em.createQuery(query, User.class).getResultList()){
			assertEquals("Germany Monheim Krischerstr.", u.getAddress());
		}
	}
	
	@Test
	public void oneToManyTest() {
		String query = "SELECT e FROM Event e";
		for (Event e : em.createQuery(query, Event.class).getResultList()){
			assertEquals(3, e.getBookings().size());
		}
	}
	
	@Test
	public void manyToManyTest() {
		String query = "SELECT u FROM User u";
		for (User u : em.createQuery(query, User.class).getResultList()){
			assertEquals(2,u.getProjects().size());
		}
		query = "SELECT p FROM Project p";
		for (Project p : em.createQuery(query, Project.class).getResultList()){
			assertEquals(5,p.getUsers().size());
		}
	}

}
