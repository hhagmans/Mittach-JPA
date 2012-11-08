package com.innoq.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Long id;

	@ManyToOne
	private Event Event;
	
	@ManyToOne
	private User user;
	private boolean vegetarian;


}
