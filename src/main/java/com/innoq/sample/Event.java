package com.innoq.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Event")
@NamedQueries({
		@NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
		@NamedQuery(name = "Event.findByUserBooking", query = "SELECT e FROM Event e") })
public class Event implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String title;
	@Temporal(TemporalType.DATE)
	private Date date;
	private boolean vegetarian_opt;
	private int slots;
    
    @Lob
    private String details;
    
    
    @OneToMany(mappedBy="event", cascade=CascadeType.ALL)
    private List<Booking> bookings;
    
    public Event(String title, String details, Date date, int slots, boolean vegetarian) { 
        this.bookings = new ArrayList<Booking>();
        this.title = title;
        this.slots = slots;
        this.details = details;
        this.date = date;
        this.vegetarian_opt = vegetarian;
    }
    
    public Event() { }
    
    public String getTitle() {
    	return this.title;
    }
    
    public Long getID() {
    	return this.id;
    }

	public List<Booking> getBookings() {
		return bookings;
	}

	public void addBooking(Booking booking) {
		this.bookings.add(booking);
	}
	
}
