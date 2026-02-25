package com.gestion_de_reservation_jee.controllers;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	private Map<String, Object> reponse = new HashMap<>();
	
	
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
    public Response creerReservation(@HeaderParam("utilisateur") String nomUtilisateur, @HeaderParam("motDePasse") String motDePasse, Reservation reservation) {
    	if (!estClientAuthentifie(nomUtilisateur, motDePasse)) {
    		reponse.put("Message", "Acces non autorisee, veuillez authentifiez!");
            return Response.status(Response.Status.UNAUTHORIZED).entity(reponse).build();
        }

        Salle salle = StockageDonnees.salles.get(reservation.getSalleId());
        if (salle == null) {
        	reponse.put("Message", "Salle n'existent pas, essayez reservez une salle existant!");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!LogiqueReservation.estDureeValide(reservation.getDureeHeurs())) {
        	reponse.put("Message", "Le duree n'est pas valide!");
            return Response.status(Response.Status.BAD_REQUEST).entity(reponse).build();
        }

        if (reservation.getDateDebut().isAfter(LocalDateTime.now())) {
        	
        	reponse.put("Message", "Le reservation devrait avoir lieu apres maintenant!.");
            return Response.status(Response.Status.BAD_REQUEST).entity(reponse).build();
        }

        if (!LogiqueReservation.estSalleDisponible(reservation.getSalleId(), reservation.getDateDebut(), reservation.getDureeHeurs())) {
        	reponse.put("Message", "La salle indisponible!");
            return Response.status(Response.Status.CONFLICT).entity(reponse).build();
        }

        double total = salle.getPrixParHeur() * reservation.getDureeHeurs();
        reservation.setId(UUID.randomUUID().toString().substring(0, 5));
        reservation.setNomClient(nomUtilisateur);
        reservation.setCoutTotal(total);
        reservation.setStatus("en_attente");

        StockageDonnees.reservations.put(reservation.getId(), reservation);
        reponse.put("Message", "Reservation cree avec succee");
        reponse.put("Reservation",reservation);
        return Response.status(Response.Status.CREATED).entity(reponse).build();
    }

    @GET
    @Path("/mes-reservations")
    public Response consulterHistorique(@HeaderParam("utilisateur") String nomUtilisateur, @HeaderParam("motDePasse") String motDePasse) {
    	if (!estClientAuthentifie(nomUtilisateur, motDePasse)) {
    		reponse.put("Message", "Acces non autorisee, veuillez authentifiez!");
            return Response.status(Response.Status.UNAUTHORIZED).entity(reponse).build();
        }
        
        List<Reservation> mesReservations = new ArrayList<>();
        for (Reservation reservation : StockageDonnees.reservations.values()) {
            if (reservation.getNomClient().equals(nomUtilisateur)) {
                mesReservations.add(reservation);
            }
        }
        return Response.ok(mesReservations).build();
    }

    @PUT
    @Path("/annuler/{id}")
    public Response annulerReservation(@HeaderParam("utilisateur") String nomUtilisateur, @HeaderParam("motDePasse") String motDePasse, @PathParam("id") String id) {
    	if (!estClientAuthentifie(nomUtilisateur, motDePasse)) {
    		reponse.put("Message", "Acces non autorisee, veuillez authentifiez!");
            return Response.status(Response.Status.UNAUTHORIZED).entity(reponse).build();
        }

        Reservation reservation = StockageDonnees.reservations.get(id);
        if (reservation == null || !reservation.getNomClient().equals(nomUtilisateur)) {
        	reponse.put("Message", "L'annulation est impossible, la reservation n'existe pas");
            return Response.status(Response.Status.NOT_FOUND).entity(reponse).build();
        }

        if (!LogiqueReservation.peutAnnuler(reservation.getDateDebut())) {
        	reponse.put("Message", "L'annulation est impossible, le delai d'annulation autorise est depasse");
            return Response.status(Response.Status.BAD_REQUEST).entity(reponse).build();
        }

        reservation.setStatus("annellee");
        reponse.put("Message", "Reservation annulee");
        return Response.ok().entity(reponse).build();
    }
}