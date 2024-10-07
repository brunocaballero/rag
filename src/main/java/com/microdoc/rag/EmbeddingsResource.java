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
        return service.embed(question);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/distance/")
    public String distance(String request) {
        String[] splitted = request.split(",");
        if (splitted.length != 2) {
            return "Use: 2 words separated by comma. Example: \"dog,cat\"";
        }
        double distance = service.distance(splitted[0], splitted[1]);
        return "Distance = " + distance;
    }
}
