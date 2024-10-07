package com.microdoc.rag;

import dev.langchain4j.rag.content.Content;
import dev.langchain4j.service.Result;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ChatServiceRag {

    AssistantRag assistant;

    AssistantRag getAssistant() {
        return assistant;
    }

    void setAssistant(AssistantRag assistant) {
        this.assistant = assistant;
    }

    public String chat(String question) {
        Result<String> result = assistant.chat(question);
        String answer = result.content();
        List<Content> sources = result.sources();
        return answer;
    }

}
