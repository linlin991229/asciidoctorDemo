package org.acme.controller;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.Cat;

@ApplicationScoped
@Path("cat")
public class CatResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @WithTransaction
    public Uni<Cat> getCatById(@PathParam("id") Long id){
        return Cat.findById(id);
    }
}
