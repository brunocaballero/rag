package com.microdoc.rag;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chat")
public class
ChatResource {

    @Inject
    ChatServiceRag service;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String chat(String question){
        return service.chat(question);
    }
}
