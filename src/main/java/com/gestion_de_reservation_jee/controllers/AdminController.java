package com.gestion_de_reservation_jee.controllers;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.gestion_de_reservation_jee.models.Salle;
import com.gestion_de_reservation_jee.models.User;
import com.gestion_de_reservation_jee.stockage.StockageDonnees;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {
	private Map<String,String> reponse = new HashMap<>();
    private boolean validerAdmin(String user, String pass) {
        User admin = StockageDonnees.users.get(user); 
        return admin != null && admin.getPassword().equals(pass) && admin.getRole().equals("admin");
    }

    @GET
    @Path("/users")
    public Response consulterUtilisateurs(@HeaderParam("user") String user, @HeaderParam("password") String password) {
    	reponse.put("Message", "Ressource disponible uniquement aux admins");
        if(!validerAdmin(user, password)) return Response.status(401).entity(reponse).build();
        
        return Response.ok(StockageDonnees.users.values()).build();
    }

    @POST
    @Path("/users")
    public Response ajouterUtilisateur(@HeaderParam("user") String user, @HeaderParam("password") String password, User newUser) {
    	reponse.put("Message", "Ressource disponible uniquement aux admins");

        if(!validerAdmin(user, password)) return Response.status(401).entity(reponse).build();
        reponse.put("Message", "Salle n'existe pas,ne peut etre assigner!");
        if(!StockageDonnees.salles.containsKey(newUser.getSallesIds().getFirst())) return Response.status(404).entity(reponse).build();
        

        StockageDonnees.users.put(newUser.getUsername(), newUser);
    	reponse.put("Message", "Utilisateur ajouter avec succés");

        return Response.status(201).entity(reponse).build();
    }

    @DELETE
    @Path("/users/{username}")
    public Response supperimerUtilisateur(@HeaderParam("user") String user, @HeaderParam("password") String password, @PathParam("username") String username) {
    	reponse.put("Message", "Ressource disponible uniquement aux admins");

        if(!validerAdmin(user, password)) return Response.status(401).entity(reponse).build();
        
        StockageDonnees.users.remove(username);
    	reponse.put("Message", "L'utilisateur a été suprimé");

        return Response.ok().build();
    }


    @POST
    @Path("/salle")
    public Response ajouterSalle(@HeaderParam("user") String user, @HeaderParam("password") String password, Salle salle) {
    	reponse.put("Message", "Ressource disponible uniquement aux admins");
        if(!validerAdmin(user, password)) return Response.status(401).entity(reponse).build();
        
        StockageDonnees.salles.put(salle.getId(), salle); 
    	reponse.put("Message", "Salle a été ajouté");

        return Response.status(201).entity(reponse).build();
    }
    @DELETE
    @Path("/salle/{idSalle}/supprimer")
    public Response supprimerSalle(@HeaderParam("user") String user, @HeaderParam("password") String password, String IdSalle) {
    	reponse.put("Message", "Ressource disponible uniquement aux admins");

        if(!validerAdmin(user, password)) return Response.status(401).entity(reponse).build();
        
        StockageDonnees.salles.remove(IdSalle); 
    	reponse.put("Message", "Salle a été supprimee");

        return Response.status(201).entity(reponse).build();
    }

    @GET
    @Path("/salles")
    public Collection<Salle> consuletrSalles() {
        return StockageDonnees.salles.values(); 
    }
}