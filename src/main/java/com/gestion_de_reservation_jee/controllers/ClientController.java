package com.gestion_de_reservation_jee.controllers;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gestion_de_reservation_jee.logique.LogiqueReservation;
import com.gestion_de_reservation_jee.models.Reservation;
import com.gestion_de_reservation_jee.models.Salle;
import com.gestion_de_reservation_jee.models.User;
import com.gestion_de_reservation_jee.stockage.StockageDonnees;

@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientController {

    private boolean estClientAuthentifie(String nomUtilisateur, String motDePasse) {
        User user = StockageDonnees.users.get(nomUtilisateur);
        return user != null && user.getPassword().equals(motDePasse) && user.getRole().equals("client");
    }

    @GET
    @Path("/salles")
    public Response consulterCatalogue() {
        return Response.ok(StockageDonnees.salles.values()).build();
    }

    @POST
    @Path("/reserver")
    public Response creerReservation(@HeaderParam("utilisateur") String nomUtilisateur, @HeaderParam("motDePasse") String motDePasse, Reservation requete) {
        if (!estClientAuthentifie(nomUtilisateur, motDePasse)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Salle salle = StockageDonnees.salles.get(requete.getRoomId());
        if (salle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!LogiqueReservation.estDureeValide(requete.getDurationHours())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (requete.getStartTime().isBefore(LocalDateTime.now())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!LogiqueReservation.estSalleDisponible(requete.getRoomId(), requete.getStartTime(), requete.getDurationHours())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        double total = salle.getPrixParHeur() * requete.getDurationHours();
        
        requete.setId(UUID.randomUUID().toString().substring(0, 8));
        requete.setClientUsername(nomUtilisateur);
        requete.setTotalCost(total);
        requete.setStatus("EN_ATTENTE");

        StockageDonnees.reservations.put(requete.getId(), requete);
        return Response.status(Response.Status.CREATED).entity(requete).build();
    }

    @GET
    @Path("/mes-reservations")
    public Response consulterHistorique(@HeaderParam("utilisateur") String nomUtilisateur, @HeaderParam("motDePasse") String motDePasse) {
        if (!estClientAuthentifie(nomUtilisateur, motDePasse)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        List<Reservation> mesReservations = new ArrayList<>();
        for (Reservation r : StockageDonnees.reservations.values()) {
            if (r.getClientUsername().equals(nomUtilisateur)) {
                mesReservations.add(r);
            }
        }
        return Response.ok(mesReservations).build();
    }

    @PUT
    @Path("/annuler/{id}")
    public Response annulerReservation(@HeaderParam("utilisateur") String nomUtilisateur, @HeaderParam("motDePasse") String motDePasse, @PathParam("id") String id) {
        if (!estClientAuthentifie(nomUtilisateur, motDePasse)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Reservation res = StockageDonnees.reservations.get(id);
        if (res == null || !res.getClientUsername().equals(nomUtilisateur)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!LogiqueReservation.peutAnnuler(res.getStartTime())) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        res.setStatus("annellee");
        return Response.ok().build();
    }
}