package com.innoq.sample;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import com.innoq.sample.Event;


public class Testservlet extends HttpServlet
{

@Override
  protected void doGet( HttpServletRequest req, HttpServletResponse res )
      throws IOException
  {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAProject");
	EntityManager em = emf.createEntityManager();
	String query = "SELECT e FROM Event e";
	Event e = em.createQuery(query, Event.class).getResultList().get(0);
	try{
	String json = new Gson().toJson(e);
	res.setHeader( "Content-Type", "application/json");
    res.getWriter().println(json);
	 } catch (JsonParseException ex) {
	    	System.out.println("Konnte JsonString nicht parsen: "+ex.getMessage());
	    	res.setStatus(500);
	    }
  }
}