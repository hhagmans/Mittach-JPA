package com.innoq.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Booking")
@NamedQueries({
		@NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b")})
public class Booking implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	private Event event;
	
	@ManyToOne
	private User user;
	private boolean vegetarian;
	
	public Booking(){
		
	}

	public Booking(Event event, User user, boolean vegetarian){
		this.setEvent(event);
		this.user = user;
		this.vegetarian = vegetarian;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	public void deleteEvent() {
		this.event = null;
	}

}
