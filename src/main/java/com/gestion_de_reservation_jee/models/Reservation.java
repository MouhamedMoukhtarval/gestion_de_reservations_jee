package com.gestion_de_reservation_jee.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.bind.annotation.JsonbDateFormat;

public class Reservation {
    private String id;
    private String salleId;
    private String nomClient;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateDebut;
    private int dureeHeurs;      
    private int participants;        
    private List<String> servicesSupplementaires = new ArrayList<>();
    private String statut;         
    private double coutTotal;

 

    public Reservation(String id, String salleId,LocalDateTime dateDebut, String nomClient, int dureeHeurs, int participants) {
        this.id = id;
        this.salleId = salleId;
        this.nomClient = nomClient;
        this.dateDebut = dateDebut;
        this.dureeHeurs = dureeHeurs;
        this.participants = participants;
        this.statut = "en_attente" ;
    }
    public Reservation() {}

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSalleId() { return salleId; }
    public void setSalleId(String salleId) { this.salleId = salleId; }
    public String getNomClient() { return nomClient; }
    public void setNomClient(String clientUsername) { this.nomClient = clientUsername; }
    public LocalDateTime getDateDebut() { return this.dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public int getDureeHeurs() { return dureeHeurs; }
    public void setDureeHeurs(int dureeHeurs) { this.dureeHeurs = dureeHeurs; }
    public int getParticipants() { return participants; }
    public void setParticipants(int participants) { this.participants = participants; }
    public List<String> getServicesSupplementaire() { return servicesSupplementaires; }
    public void getServicesSupplementaire(List<String> servicesSupplementaires) { this.servicesSupplementaires = servicesSupplementaires; }
    public String getStatut() { return statut; }
    public void setStatus(String statut) { this.statut = statut; }
    public double getCoutTotal() { return coutTotal; }
    public void setCoutTotal(double coutTotal) { this.coutTotal = coutTotal; }
}