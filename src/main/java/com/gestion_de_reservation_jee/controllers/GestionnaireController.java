package com.gestion_de_reservation_jee.controllers;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gestion_de_reservation_jee.models.Reservation;
import com.gestion_de_reservation_jee.models.User;
import com.gestion_de_reservation_jee.stockage.StockageDonnees;

@Path("/gestionneur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GestionnaireController {
	private Map<String,String> reponse = new HashMap<>();
    private boolean estGestionnaire(String user, String password) {
        User gestionneur = StockageDonnees.users.get(user);
        return gestionneur != null && gestionneur.getPassword().equals(password) && gestionneur.getRole().equals("gestionnaire");
    }

    @GET
    @Path("/reservations")
    public Response consulterReservations(@HeaderParam("user") String utilisateur, @HeaderParam("password") String password) {
        reponse.put("Message", "Acces non autorisee");

        if (!estGestionnaire(utilisateur, password)) return Response.status(401).entity(reponse).build();
        return Response.ok(StockageDonnees.reservations.values()).build();
    }

   
    @PUT
    @Path("/reservations/{id}/valider")
    public Response validerReservation(@HeaderParam("user") String utilisateur, @HeaderParam("password") String password, @PathParam("id") String resId) {
        reponse.put("Message", "Acces non autorisee");

    	if (!estGestionnaire( utilisateur, password)) return Response.status(401).entity(reponse).build();
        
        Reservation res = StockageDonnees.reservations.get(resId);
        reponse.put("Message", "Reservation n'existe pas");
        if (res == null) return Response.status(404).entity(reponse).build();
        
        reponse.put("Message", "Reservation validee");

        res.setStatus("validee");
        return Response.ok(reponse).build();
    }
 
    @PUT
    @Path("/reservations/{id}/annuler")
    public Response annulerReservation(@HeaderParam("user") String utilisateur, @HeaderParam("password") String password, @PathParam("id") String resId) {
        reponse.put("Message", "Acces non autorisee");

    	if (!estGestionnaire(utilisateur, password)) return Response.status(401).entity(reponse).build();
        
        Reservation res = StockageDonnees.reservations.get(resId);
        reponse.put("Message", "Reservation n'existe pas");

        if (res == null) return Response.status(404).entity(reponse).build();
        
        reponse.put("Message", "Reservation est annulee");

        res.setStatus("annulee");
        return Response.ok(reponse).build();
    }

    @PUT
    @Path("/reservations/{id}/services")
    public Response ajouterServices(@HeaderParam("user") String utilisateur, @HeaderParam("password") String password, @PathParam("id") String reservationId, List<String> nouveauxServices) {
        reponse.put("Message", "Acces non autorisee");

    	if (!estGestionnaire(utilisateur, password)) return Response.status(401).entity(reponse).build();
        
        Reservation res = StockageDonnees.reservations.get(reservationId);
        reponse.put("Message", "Reservation n'existe pas,et pas possible d'ajout des services");

        if (res == null) return Response.status(404).entity(reponse).build();
        
        res.getServicesSupplementaire().addAll(nouveauxServices);
        reponse.put("Message", "Services sont ajoutees a la reservation");

        return Response.ok(reponse).build();
    }
}