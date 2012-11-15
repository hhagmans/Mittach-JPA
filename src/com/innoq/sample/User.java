package com.innoq.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String shortname;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address address;
	private boolean isAdmin;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	  @JoinTable(
	      name="user_project",
	      joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
	      inverseJoinColumns={@JoinColumn(name="project_id", referencedColumnName="id")})
	  private List<Project> projects;
	

	public User(String shortname, boolean isAdmin) {
		this.shortname = shortname;
		this.isAdmin = isAdmin;
		this.projects = new ArrayList<Project>();
	}
	
	public User() {
		
	}
	
	public String getShortname() {
    	return this.shortname;
    }
	
	public String getAddress() {
    	return this.address.getFullAddress();
    }
	
	public List<Project> getProjects() {
		return projects;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
	}
	
}
