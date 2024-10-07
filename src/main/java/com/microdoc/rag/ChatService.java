package com.microdoc.rag;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChatService {

    Assistant assistant;

    Assistant getAssistant() {
        return assistant;
    }

    void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public String chat(String question) {
        return assistant.chat(question);
    }

}
