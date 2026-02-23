package com.gestion_de_reservation_jee.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private String id;
    private String roomId;
    private String clientUsername;
    private LocalDateTime startTime;
    private int durationHours;      
    private int participants;        
    private List<String> extraServices = new ArrayList<>();
    private String status;         
    private double totalCost;

    public Reservation() {}

    public Reservation(String id, String roomId, String clientUsername, LocalDateTime startTime, int durationHours, int participants) {
        this.id = id;
        this.roomId = roomId;
        this.clientUsername = clientUsername;
        this.startTime = startTime;
        this.durationHours = durationHours;
        this.participants = participants;
        this.status = "en_attente"; 
    }

    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getClientUsername() { return clientUsername; }
    public void setClientUsername(String clientUsername) { this.clientUsername = clientUsername; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public int getDurationHours() { return durationHours; }
    public void setDurationHours(int durationHours) { this.durationHours = durationHours; }
    public int getParticipants() { return participants; }
    public void setParticipants(int participants) { this.participants = participants; }
    public List<String> getExtraServices() { return extraServices; }
    public void setExtraServices(List<String> extraServices) { this.extraServices = extraServices; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
}