package org.acme.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.commont.LinResult;
import org.acme.exception.LinException;
import org.jboss.resteasy.reactive.RestResponse;

@Path("exception")
@ApplicationScoped
public class ExceptionTestController {


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<LinResult> getException() {
        System.out.println("你好啊");
        throw new LinException(4000, "我是自定义异常");
    }
}
