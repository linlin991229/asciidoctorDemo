package org.acme.rest.json;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import io.vertx.core.http.HttpServerRequest;

@Provider
public class LoggerFilter implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(org.acme.rest.json.LoggerFilter.class);

    @Context
    UriInfo info;

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {

        final String method = context.getMethod();
        final String path = info.getPath();
        final String address = request.remoteAddress().toString();
        LOG.info("================================================================");
        LOG.info("RequestMethod: " + method);
        LOG.info("path: " + path);
        LOG.info("请求地址: " + address);
    }
}
