package com.innoq.sample;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Project")
@NamedQueries({
		@NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project P")
		})
public class Project {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy="projects")
	private List<User> users;
	
	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
	}

	public Project(String name){
		this.name = name;
		this.users = new ArrayList<User>();
	}
	
	public Project(){
		
	}
}
