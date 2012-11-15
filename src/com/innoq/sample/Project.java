package com.innoq.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	public Project(String name){
		this.name = name;
	}
	
	public Project(){
		
	}
}
