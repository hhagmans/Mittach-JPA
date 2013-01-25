package com.innoq.sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * Servlet implementation class UploadFileservlet
 */
public class UploadFileservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<head><title>File Upload</title></head>");
	    out.println("<body>");
	    String body = "<form action\"upload\" method=\"post\" enctype=\"multipart/form-data\"> <input type=\"file\" name=\"file\" /> <input type=\"submit\" /> </form>";
	    out.println(body);
	    out.println("</body></html>");
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
	        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
	        for (FileItem item : items) {
	                // Process form file field (input type="file").
	                String fieldname = item.getFieldName();
	                String filename = FilenameUtils.getName(item.getName());
	                InputStream in = item.getInputStream();
	                byte textfile[]= new byte[in.available()];
	        		in.read(textfile);
	        		in.close();
	        		String decodedString = new String(textfile);
	        		String objtype = null;
	        		try {
		        		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAProject");
		        		EntityManager em = emf.createEntityManager();
	        			if (decodedString.contains("\"bookings\":") && decodedString.contains("\"event\":") == false){
	        				Event obj = new Gson().fromJson(decodedString, Event.class);
	    	        		em.getTransaction().begin();
	    	        		em.persist(obj);
	    	        		em.getTransaction().commit();
	    	        		objtype = "Event";
	        			}
	        			else if (decodedString.contains("\"event\":")){
	        				Booking obj = new Gson().fromJson(decodedString, Booking.class);
	    	        		em.getTransaction().begin();
	    	        		em.persist(obj);
	    	        		em.getTransaction().commit();
	    	        		objtype = "Booking";
	        			}
	        			else {
	        				User obj = new Gson().fromJson(decodedString, User.class);
	    	        		em.getTransaction().begin();
	    	        		em.persist(obj);
	    	        		em.getTransaction().commit();
	    	        		objtype = "User";
	        			}
	        		
	        		}
	        		catch (JsonParseException ex) {
	    				System.out.println("Konnte JsonString nicht parsen: "
	    						+ ex.getMessage());
	    				response.setStatus(500);
	    			}
	        		
	        		ServletOutputStream outs = response.getOutputStream();
	        		outs.println( objtype+" erstellt." );          
	        		outs.flush();
	        		outs.close();
	        }
	    } catch (FileUploadException e) {
	        throw new ServletException("Cannot parse multipart request.", e);
	    }
		
	}

}
