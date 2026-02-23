package com.gestion_de_reservation_jee.controllers;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import com.gestion_de_reservation_jee.models.Reservation;
import com.gestion_de_reservation_jee.models.Salle;
import com.gestion_de_reservation_jee.models.User;
import com.gestion_de_reservation_jee.stockage.StockageDonnees;

@Path("/gestionneur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GessionerController {

    private boolean estGestionneur(String user, String pass) {
        User gestionneur = StockageDonnees.users.get(user);
        return gestionneur != null && gestionneur.getPassword().equals(pass) && gestionneur.getRole().equals("gestionneur");
    }

    @GET
    @Path("/reservations")
    public Response consulterReservations(@HeaderParam("user") String utilisateur, @HeaderParam("pass") String password) {
        if (!estGestionneur(utilisateur, password)) return Response.status(401).entity("Acces non autorisee").build();
        return Response.ok(StockageDonnees.reservations.values()).build();
    }

   
    @PUT
    @Path("/reservations/{id}/valider")
    public Response validerReservation(@HeaderParam("user") String utilisateur, @HeaderParam("pass") String password, @PathParam("id") String resId) {
        if (!estGestionneur( utilisateur, password)) return Response.status(401).entity("Acces non autorisee").build();
        
        Reservation res = StockageDonnees.reservations.get(resId);
        if (res == null) return Response.status(404).entity("Reservation n'existe pas").build();
        
        res.setStatus("validee");
        return Response.ok("Reservation est validee").build();
    }
 
    @PUT
    @Path("/reservations/{id}/annuler")
    public Response annulerReservation(@HeaderParam("user") String u, @HeaderParam("pass") String p, @PathParam("id") String resId) {
        if (!estGestionneur(u, p)) return Response.status(401).entity("Acces non autorisee").build();
        
        Reservation res = StockageDonnees.reservations.get(resId);
        if (res == null) return Response.status(404).entity("Reservation n'existe pas").build();
        
        res.setStatus("annulee");
        return Response.ok("Reservation est annuler").build();
    }

    @PUT
    @Path("/reservations/{id}/services")
    public Response ajouterServices(@HeaderParam("user") String utilisateur, @HeaderParam("pass") String password, @PathParam("id") String resId, List<String> nouveauxServices) {
        if (!estGestionneur(utilisateur, password)) return Response.status(401).entity("Acces non autorisee").build();
        
        Reservation res = StockageDonnees.reservations.get(resId);
        if (res == null) return Response.status(404).entity("Reservation n'existe pas").build();
        
        res.getExtraServices().addAll(nouveauxServices);
        return Response.ok("Services sont ajoutees ").build();
    }

    @PUT
    @Path("/salles/{salleId}/rapport-equipments")
    public Response rapportEquipments(@HeaderParam("user") String utilisateur, @HeaderParam("pass") String password, @PathParam("salleId") String salleId, String defectueEquipment) {
        if (!estGestionneur(utilisateur, password)) return Response.status(401).entity("Acces non autorisee").build();
        
        Salle salle = StockageDonnees.salles.get(salleId);
        if (salle == null) return Response.status(404).entity("Salle n'existe pas").build();
        
        salle.getEquipments().remove(defectueEquipment);
        return Response.ok("Le dysfonctionnement de " + defectueEquipment + "ont ete signalee").build();
    }
}