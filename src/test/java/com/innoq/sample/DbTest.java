package com.innoq.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;



public class DbTest extends DBTestCase{
	EntityManagerFactory emf;
	EntityManager em;
	
	
	 public DbTest(String name) {
		 	super( name );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "sa" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:~/mytest");
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "dbunit" );
	    }
	
	 
	 
	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}



	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	
	 private static final String DRIVER_NAME = "org.h2.Driver";  
	  
	    static  
	    {  
	        try  
	        {  
	            Class.forName(DRIVER_NAME).newInstance();  
	            System.out.println("*** Driver loaded");  
	        }  
	        catch(Exception e)  
	        {  
	            System.out.println("*** Error : "+e.toString());  
	            System.out.println("*** ");  
	            System.out.println("*** Error : ");  
	            e.printStackTrace();  
	        }  
	  
	    }  
	  
	    private static final String URL = "jdbc:h2:~/mytest";  
	    private static final String USER = "sa";  
	    private static final String PASSWORD = "sa";  
	    private static String INSTRUCTIONS = new String();  
	  
	    public static java.sql.Connection getConnection2() throws SQLException  
	    {  
	        return DriverManager.getConnection(URL, USER, PASSWORD);  
	    }  
	  
	    public static void resetDatabase() throws SQLException  
	    {  
	        String s            = new String();  
	        StringBuffer sb = new StringBuffer();  
	  
	        try  
	        {  
	            FileReader fr = new FileReader(new File("table_schema.sql"));  
	            // be sure to not have line starting with "--" or "/*" or any other non alphabetical character  
	  
	            BufferedReader br = new BufferedReader(fr);  
	  
	            while((s = br.readLine()) != null)  
	            {  
	                sb.append(s);  
	            }  
	            br.close();  
	  
	            // here is our splitter ! We use ";" as a delimiter for each request  
	            // then we are sure to have well formed statements  
	            String[] inst = sb.toString().split(";");  
	  
	            java.sql.Connection c = getConnection2();  
	            Statement st = c.createStatement();  
	  
	            for(int i = 0; i<inst.length; i++)  
	            {  
	                // we ensure that there is no spaces before or after the request string  
	                // in order to not execute empty statements  
	                if(!inst[i].trim().equals(""))  
	                {  
	                    st.executeUpdate(inst[i]);  
	                    System.out.println(">>"+inst[i]);  
	                }  
	            }  
	    
	        }  
	        catch(Exception e)  
	        {  
	            System.out.println("*** Error : "+e.toString());  
	            System.out.println("*** ");  
	            System.out.println("*** Error : ");  
	            e.printStackTrace();  
	            System.out.println("################################################");  
	            System.out.println(sb.toString()); 
	        }
	        }  
	
	@Override
	protected void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory("myJPAProject");
		em = emf.createEntityManager();
		resetDatabase();
        
    }
	
	
	public void testOneToOne(){
		try {
		Address address = new Address("Germany","Monheim","Krischerstr.");
		User user = new User("Test",false);
		User user2 = new User("Test2",false);
		User user3 = new User("Test3",false);
		User user4 = new User("Test4",false);
		User user5 = new User("Test5",false);
		
		em.getTransaction().begin();
		
		user.setAddress(address);
		user2.setAddress(address);
		user3.setAddress(address);
		user4.setAddress(address);
		user5.setAddress(address);
		
		em.persist(user);
		em.persist(user2);
		em.persist(user3);
		em.persist(user4);
		em.persist(user5);
		
		em.getTransaction().commit();
		
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		String query = "SELECT u FROM User u";
		for (User u : em.createQuery(query, User.class).getResultList()){
			assertEquals("Germany Monheim Krischerstr.", u.getAddress());
		}
	}
	

	public void testOneToMany() {
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
			
			em.getTransaction().begin();
			
			user.setAddress(address);
			user2.setAddress(address);
			user3.setAddress(address);
			user4.setAddress(address);
			user5.setAddress(address);
			
			event1.addBooking(booking1);
			event1.addBooking(booking2);
			event1.addBooking(booking3);
			
			em.persist(user);
			em.persist(user2);
			em.persist(user3);
			em.persist(event1);
			
			em.getTransaction().commit();
			
			} catch (Exception e) {
				e.printStackTrace();
				em.getTransaction().rollback();
			}
		
		String query = "SELECT e FROM Event e";
		for (Event e : em.createQuery(query, Event.class).getResultList()){
			assertEquals(3, e.getBookings().size());
		}
	}
	

	public void testManyToMany() {
		try {
			Address address = new Address("Germany","Monheim","Krischerstr.");
			User user = new User("Test",false);
			User user2 = new User("Test2",false);
			User user3 = new User("Test3",false);
			User user4 = new User("Test4",false);
			User user5 = new User("Test5",false);
			
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
			
			em.getTransaction().commit();
			
			} catch (Exception e) {
				e.printStackTrace();
				em.getTransaction().rollback();
			}

		
		String query = "SELECT u FROM User u";
		for (User u : em.createQuery(query, User.class).getResultList()){
			assertEquals(2,u.getProjects().size());
		}
		query = "SELECT p FROM Project p";
		for (Project p : em.createQuery(query, Project.class).getResultList()){
			assertEquals(5,p.getUsers().size());
		}
	}

    @Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSet(new FileInputStream("full.xml"));
	}
    
	

}
