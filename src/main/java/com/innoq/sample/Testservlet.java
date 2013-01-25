package com.innoq.sample;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.*;
import javax.persistence.Persistence;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import com.innoq.sample.Event;

public class Testservlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String id = req.getParameter("id");
		if (id != null){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAProject");
		EntityManager em = emf.createEntityManager();
		Event e = em.find(Event.class, Long.parseLong(id));
		if (e != null) {
			for (int i = 0; i < e.getBookings().size(); i++) {
				e.getBookings().get(i).deleteEvent();
			}
			try {
				String json = new Gson().toJson(e);
				res.setHeader("Content-Type", "application/json");
				res.getWriter().println(json);
			} catch (JsonParseException ex) {
				System.out.println("Konnte JsonString nicht parsen: "
						+ ex.getMessage());
				res.setStatus(500);
			}
		} else {
			res.setHeader("Content-Type", "text/plain");
			res.getWriter().println("Event nicht vorhanden");
		}
		}
		else {
			res.setStatus(404);
		}
  }
}