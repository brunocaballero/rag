package com.microdoc.rag;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

@QuarkusMain
public class Main {

    public static void main(String ... args) {
        Quarkus.run(MyApp.class,
                (exitCode, exception) -> {
                    // do whatever
                },
                args);
    }

    public static class MyApp implements QuarkusApplication {

        @Inject
        ChatService chatService;

        @Inject
        EmbeddingsService embeddingsService;

        @Override
        public int run(String... args) {

            embeddingsService.init();

            ChatLanguageModel chatModel = OpenAiChatModel.builder()
                    .apiKey(System.getenv("OPENAI_API_KEY"))
                    .modelName(GPT_4_O_MINI)
                    .build();

            Assistant assistant = AiServices.builder(Assistant.class)
                    .chatLanguageModel(chatModel)
                    .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                    .build();

            chatService.setAssistant(assistant);

            Quarkus.waitForExit();
            return 0;
        }
    }

}
