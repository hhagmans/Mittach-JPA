package com.innoq.sample;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "User")
@NamedQueries({
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
		})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	private Long id;
	
	private String shortname;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address address;
	private boolean isAdmin;
	
	public User(String shortname, boolean isAdmin) {
		this.shortname = shortname;
		this.isAdmin = isAdmin;
	}
	
	public User() {
		
	}
	
	public String getShortname() {
    	return this.shortname;
    }
	
	public String getAddress() {
    	return this.address.getFullAddress();
    }
	
	public void setAddress(Address address) {
		this.address = address;
	}
}
