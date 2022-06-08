package com.webapps2022.restservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Singleton
@Path("/")
public class RSIndex {
    public RSIndex() {

    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getIndex() {
        try {
            URI tempuri = new URI("https://localhost:8181/webapps2022/faces/index.xhtml");
            return Response.temporaryRedirect(tempuri).build();
        } catch (URISyntaxException ex) {
            Logger.getLogger(RSExchange.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    
    @PostConstruct
    public void init() {
        
    }

    @PreDestroy
    public void clean() {
        
    }
}
