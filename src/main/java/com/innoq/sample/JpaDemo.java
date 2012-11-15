package com.innoq.sample;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaDemo {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAProject");
		EntityManager em = emf.createEntityManager();
		
		try {
			Address address = new Address("Germany","Monheim","Krischerstr.");
			User user = new User("Test",false);
			//Event event1 = new Event("Pommes", "", Date.valueOf("2012-10-17"), 10, false);
			em.getTransaction().begin();
			user.setAddress(address);
			Project project = new Project("Projekt1");
			user.addProject(project);
			em.persist(user);
			//em.persist(event1);
			em.getTransaction().commit();

			String query = "SELECT e FROM Event e";
			for (Event c : em.createQuery(query, Event.class).getResultList())
				System.out.println(c.getTitle());
			
			/*query = "SELECT a FROM Address a";
			for (Address a : em.createQuery(query, Address.class).getResultList())
				System.out.println(a.getFullAddress());*/
			query = "SELECT u FROM User u";
			for (User u : em.createQuery(query, User.class).getResultList()){
				System.out.println(u.getAddress());
				System.out.println(u.getProjects());
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}
}