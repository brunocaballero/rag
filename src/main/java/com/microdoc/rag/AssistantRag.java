package com.microdoc.rag;

import dev.langchain4j.service.Result;

public interface AssistantRag {
    Result<String> chat(String userMessage);
}