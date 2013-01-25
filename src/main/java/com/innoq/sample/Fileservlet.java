package com.innoq.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Fileservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fileservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ServletContext context = this.getServletContext();
		//String datei = context.getRealPath("Files/");
		String datei = "/Users/hendrik11/Documents/myJPAProject/File";
		String dateiname = "mytext.txt";
		File file = new File(datei+"/"+dateiname);
		InputStream in = new FileInputStream(file);
		                                    
		response.addHeader("Content-Type", "text/plain");
		response.addHeader("Content-Disposition", "attachment; filename="+dateiname);
		            
		ServletOutputStream outs = response.getOutputStream();
		            
		byte textfile[]= new byte[in.available()];
		in.read( textfile );
		            
		outs.write( textfile );          
		outs.flush();
		outs.close();
		in.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
