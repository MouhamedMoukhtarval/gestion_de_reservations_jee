package com.gestion_de_reservation_jee.models;

import java.util.List;

public class User {
	    private String id;
	    private String username;
	    private String password;
	    private List<String> sallesIds;
	    private String role; 
	    private String nom;
	    private String email;

	    public User() {}
	    public User(String id,String username, String password, String role, String nom, String email) {
	    	this.id=id;
	        this.username = username;
	        this.password = password;
	        this.role = role;
	        this.nom = nom;
	        this.email = email;
	    }
	    public User(String id,String username,List<String> sallesIds, String password, String role, String nom, String email) {
	    	this.id=id;
	        this.username = username;
	        this.password = password;
	        this.sallesIds=sallesIds;
	        this.role = role;
	        this.nom = nom;
	        this.email = email;
	    }
	    public String getId() {return id;}
	    public void setId(String id) {this.id=id;}
	    public String getUsername() { return username; }
	    public void setUsername(String username) { this.username = username; }
	    public String getPassword() { return password; }
	    public void setPassword(String password) { this.password = password; }
	    public List<String> getSallesIds() { return sallesIds; }
	    public void setSallesIds(List<String> salleId) { this.sallesIds = salleId; }
	    public String getRole() { return role; }
	    public void setRole(String role) { this.role = role; }
	    public String getNom() { return nom; }
	    public void setNom(String nom) { this.nom = nom; }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }
	
}



