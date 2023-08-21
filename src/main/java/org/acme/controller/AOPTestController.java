package org.acme.controller;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.aop.WebLog;
import org.acme.commont.LinResult;

@ApplicationScoped
@Path("aop")
public class AOPTestController {

    @GET
    @WebLog
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public LinResult getAOPInfo(@PathParam("id") String id) {
        System.out.println("aopDemo:" + id);
        return new LinResult().success().setData(id);
    }
}
