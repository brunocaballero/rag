package com.microdoc.rag;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/embed")
public class
EmbeddingsResource {

    @Inject
    EmbeddingsService service;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String embed(String question){
        return "TODO";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/distance/")
    public String distance(String request) {
        return "TODO";
    }
}
