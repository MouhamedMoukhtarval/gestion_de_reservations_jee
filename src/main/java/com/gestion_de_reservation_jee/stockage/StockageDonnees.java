package com.gestion_de_reservation_jee.stockage;

import java.util.HashMap;
import java.util.Map;

import com.gestion_de_reservation_jee.models.Salle;
import com.gestion_de_reservation_jee.models.User;

public class StockageDonnees {
	public static Map<String, User> users = new HashMap<>();
    public static Map<String, Salle> salles = new HashMap<>();

    static {
        users.put("admin", new User("admin", "admin", "ADMIN", "", "admin@center.com"));
    }
}
