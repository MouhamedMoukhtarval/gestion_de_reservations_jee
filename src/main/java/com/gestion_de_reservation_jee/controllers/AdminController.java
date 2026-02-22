package com.gestion_de_reservation_jee.controllers;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Collection;

import com.gestion_de_reservation_jee.models.Salle;
import com.gestion_de_reservation_jee.models.User;
import com.gestion_de_reservation_jee.stockage.StockageDonnees;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminController {

    private boolean validerAdmin(String user, String pass) {
        User admin = StockageDonnees.users.get(user); 
        return admin != null && admin.getPassword().equals(pass) && admin.getRole().equals("ADMIN");
    }

    @GET
    @Path("/users")
    public Response consulterUtilisateurs(@HeaderParam("user") String u, @HeaderParam("pass") String p) {
        if(!validerAdmin(u, p)) return Response.status(401).entity("Acces non autorisé").build();

        return Response.ok(StockageDonnees.users.values()).build();
    }

    @POST
    @Path("/users")
    public Response ajouterUtilisateur(@HeaderParam("user") String u, @HeaderParam("pass") String p, User newUser) {
        if(!validerAdmin(u, p)) return Response.status(401).entity("Acces non autorisé").build();
        

        StockageDonnees.users.put(newUser.getUsername(), newUser);
        return Response.status(201).entity("Utilisateur ajouter avec succés").build();
    }

    @DELETE
    @Path("/users/{username}")
    public Response supperimerUtilisateur(@HeaderParam("user") String u, @HeaderParam("pass") String p, @PathParam("username") String username) {
        if(!validerAdmin(u, p)) return Response.status(401).entity("Accés non autorisé").build();
        
        StockageDonnees.users.remove(username);
        return Response.ok("L'utilisateur a été suprimé").build();
    }


    @POST
    @Path("/salle")
    public Response ajouterSalle(@HeaderParam("user") String u, @HeaderParam("pass") String p, Salle room) {
        if(!validerAdmin(u, p)) return Response.status(401).entity("Accés non autorisé").build();
        
        StockageDonnees.salles.put(room.getId(), room); 
        return Response.status(201).entity("Salle a été ajouté").build();
    }

    @GET
    @Path("/salle")
    public Collection<Salle> consuletrSalles() {
        return StockageDonnees.salles.values(); 
    }
}