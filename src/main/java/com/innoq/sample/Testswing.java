package com.innoq.sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
class JavaSwing1{
	
	public String getHTML(String urlToRead) {
	      URL url;
	      HttpURLConnection conn;
	      BufferedReader rd;
	      String line;
	      String result = "";
	      try {
	         url = new URL(urlToRead);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      return result;
	   }
	
	public JFrame initializeFrame(String name,Dimension size, Point location){
		final JFrame jfrm=new JFrame(name);

		jfrm.setSize(size);
		
		jfrm.setLocation(location);
		
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jfrm.setLayout(new FlowLayout());
		return jfrm;
	}
	
	public void home(JFrame jfrm, JFrame jfrm2){
			jfrm.setVisible(true);
	    	jfrm2.setVisible(false);
	}
	
	public void showEvent(String eventID, JFrame oldjfrm, JFrame newjfrm){
		JList auswahl = listEvent(eventID);
		if (newjfrm.getContentPane().getComponentCount() > 1) {
		newjfrm.getContentPane().remove(1);
		}
        newjfrm.getContentPane().add(auswahl);
        oldjfrm.setVisible(false);
    	newjfrm.setVisible(true);
	}
	
	public JList listEvent(String eventID){
		String e = getHTML("http://localhost:8080/myJPAProject/" + eventID);
		if (e.startsWith("{",0)) {
			Event event = new Gson().fromJson(e, Event.class);
			String[] eventdetails = new String[7];
			eventdetails[0] = "ID: " + event.getID().toString();
			eventdetails[1] = "Titel: " + event.getTitle();
			eventdetails[2] = "Date: " + event.getDate();
			eventdetails[3] = "Details: " + event.getDetails();
			eventdetails[4] = "Slots: " + event.getSlots();
			eventdetails[5] = "Vegetarian: " + event.isVegetarian_opt();
			String book = "Bookings: ";
			for (int i = 0; i < event.getBookings().size(); i++) {
				book = book + event.getBookings().get(i).getUser().getShortname() + ", ";
			}
			eventdetails[6] = book;
			JList auswahl = new JList(eventdetails);
			return auswahl;
		}
		else {
			String[] err = {"Event nicht vorhanden"};
			JList auswahl = new JList(err);
			return auswahl;
		}
	}
	
	public void fileUpload(JButton component, JFrame frame) throws ClientProtocolException, IOException{
		final JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(component);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://localhost:8080/myJPAProject/App/fileservlet/upload");

            MultipartEntity entity = new MultipartEntity();
            entity.addPart("file", new FileBody(file));
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            HttpEntity responseEntity = response.getEntity();
            String body = "";
            if(responseEntity!=null) {
                body = EntityUtils.toString(responseEntity);
            }
            JLabel statuslabel = new JLabel(body);
            if (frame.getContentPane().getComponentCount() > 4) {
        		frame.getContentPane().remove(4);
        		}
            frame.getContentPane().add(statuslabel);
            frame.setVisible(true);
        } else {
            
        }
	}
	
	public void saveFile(JButton component, JFrame frame) throws MalformedURLException, IOException{
		JFileChooser c = new JFileChooser();
	    c.setSelectedFile(new File("~/mytext.txt"));
	      int rVal = c.showSaveDialog(component);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        String filename = c.getSelectedFile().getName();
	        String dir = c.getCurrentDirectory().toString();
	        try {
	        FileUtils.copyURLToFile(new URL("http://localhost:8080/myJPAProject/App/fileservlet/mytext.txt"), new File(dir + "/" + filename));
	        JLabel statuslabel = new JLabel("Datei gespeichert");
            if (frame.getContentPane().getComponentCount() > 4) {
        		frame.getContentPane().remove(4);
        		}
            frame.getContentPane().add(statuslabel);
            frame.setVisible(true);
	        }
	        catch (IOException e1) {
				e1.printStackTrace();
			}
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	}
	}
	
	public void display(){
		
		
		final JFrame jfrm=initializeFrame("Mittach Application Client", new Dimension(400,100), new Point(500, 350));
        final JFrame jfrm2=initializeFrame("Event", new Dimension(400,170), new Point(500, 350));

		final JTextField eventIDField = new JTextField();
		eventIDField.setPreferredSize(new Dimension(50,20));
		
        JButton eventButton = new JButton("Zeige Event");
        ActionListener eventlist = new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
            	showEvent(eventIDField.getText(), jfrm, jfrm2);
            }
          };
          eventButton.addActionListener( eventlist );
          
          final JButton uploadButton = new JButton("File Upload");
          ActionListener fileUpload = new ActionListener() {
              public void actionPerformed( ActionEvent e ) {
            	  try {
					fileUpload(uploadButton, jfrm);
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
              }
            };
            uploadButton.addActionListener( fileUpload );
            
          JButton homeButton = new JButton("Zurueck zu Home");
          ActionListener home = new ActionListener() {
              public void actionPerformed( ActionEvent e ) {
              	home(jfrm, jfrm2);
              }
            };
          homeButton.addActionListener( home );
          
          final JButton saveButton = new JButton("Speichere File");
          ActionListener saveFile = new ActionListener() {
              public void actionPerformed( ActionEvent e ) {
              	try {
					saveFile(saveButton, jfrm);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
              }
            };
          saveButton.addActionListener( saveFile );
        
        jfrm.getContentPane().add(eventIDField);
        jfrm.getContentPane().add(eventButton);
        jfrm.getContentPane().add(uploadButton);
        jfrm.getContentPane().add(saveButton);
		
		jfrm2.getContentPane().add(homeButton);
		
		jfrm2.setVisible(false);
		jfrm.setVisible(true);

	}
	public static void main(String j[]){
		//creating thread
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JavaSwing1 obj=new JavaSwing1();
				obj.display();
				
			}
		});
	}
}


