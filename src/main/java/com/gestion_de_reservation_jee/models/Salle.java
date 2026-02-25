package com.gestion_de_reservation_jee.models;

import java.util.List;

public class Salle {
	private String id;
	private String idUtilisateur;
    private String nom;
    private int capacite; 
    private String type; 
    private double prixParHeur; 
    private String location; 
    private List<String> equipments;
    
    public Salle() {}
    
    public Salle(String id,String idUtili,String nom,int capacite,String type,double prixParHeur,String location,List<String> equipments) {
         this.id=id;
         this.idUtilisateur=idUtili;
         this.nom=nom;
         this.capacite=capacite;
         this.type=type;
         this.prixParHeur=prixParHeur;
         this.location=location;
         this.equipments=equipments;
    }
    public String getIdUtili() {return idUtilisateur;}
    public void setIdUtili(String idUtili) {this.idUtilisateur=idUtili;}
    public void setId(String id) {
    	this.id=id;
    }
    public String getId() {
    	return this.id;
    }
    public void setNom(String nom) {
    	this.nom=nom;
    }
    public String getNom() {
    	return this.nom;
    }
    
    public void setCapacite(int capacite) {
    	this.capacite=capacite;
    }
    
    public int getCapacite() {
    	return this.capacite;
    }
    

    public void setType(String type) {
    	this.type=type;
    }
    
    public String getType() {
    	return this.type;
    }
    
    public void setPrixParHeur(double prixParHeur) {
    	this.prixParHeur=prixParHeur;
    }
    
    public double getPrixParHeur() {
    	return this.prixParHeur;
    }
    
    public void setLocation(String location) {
    	this.location=location;
    }
    
    public String getLocation() {
    	return this.location;
    }
    
    public void setEquipments(List<String> equipments) {
    	this.equipments=equipments;
    }
    
    public List<String> getEquipments() {
    	return this.equipments;
    }



    
    
    
    
    
    

}
