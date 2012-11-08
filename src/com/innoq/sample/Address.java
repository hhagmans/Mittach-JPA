package com.innoq.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Address")
@NamedQueries({
		@NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
		})
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String country;
	private String town;
	private String street;
	
	@OneToOne(fetch=FetchType.EAGER, mappedBy="address")
	private User user;
	
	public Address(String country, String town, String street){
		this.country = country;
		this.town = town;
		this.street = street;
	}

	public Address(){
		
	}
	
	public String getFullAddress() {
    	return this.country + ' ' + this.town + ' ' + this.street;
    }
	
	public Long getId() {
    	return this.id;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
