package com.gestion_de_reservation_jee.logique;

import java.time.Duration;
import java.time.LocalDateTime;

import com.gestion_de_reservation_jee.models.Reservation;
import com.gestion_de_reservation_jee.stockage.StockageDonnees;

public class LogiqueReservation {

    public static boolean estDureeValide(int heures) {
        return heures <= 8;
    }

    public static boolean peutAnnuler(LocalDateTime dateDebut) {
        return Duration.between(LocalDateTime.now(), dateDebut).toHours() >= 24;
    }

    public static boolean estSalleDisponible(String idSalle, LocalDateTime debut, int heures) {
        LocalDateTime fin = debut.plusHours(heures);
        for (Reservation res : StockageDonnees.reservations.values()) {
            if (res.getRoomId().equals(idSalle) && !"ANNULEE".equals(res.getStatus())) {
                LocalDateTime finRes = res.getStartTime().plusHours(res.getDurationHours());
                if (debut.isBefore(finRes) && fin.isAfter(res.getStartTime())) {
                    return false;
                }
            }
        }
        return true;
    }
}