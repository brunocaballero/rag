package com.microdoc.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

import java.util.List;

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
        ChatServiceRag chatServiceRag;

        @Inject
        EmbeddingsService embeddingsService;

        @Override
        public int run(String... args) {

            embeddingsService.init();
            EmbeddingModel embeddingModel = embeddingsService.getEmbeddingModel();

            List<Document> documents = FileSystemDocumentLoader.loadDocuments("docs/");
            InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

            DocumentByParagraphSplitter splitter = new DocumentByParagraphSplitter(380, 0);

            EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                    .documentSplitter(splitter)
                    .embeddingModel(embeddingModel)
                    .embeddingStore(embeddingStore)
                    .build();

            ingestor.ingest(documents);

            ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                    .embeddingStore(embeddingStore)
                    .maxResults(1)
                    .build();

            ChatLanguageModel chatModel = OpenAiChatModel.builder()
                    .apiKey(System.getenv("OPENAI_API_KEY"))
                    .modelName(GPT_4_O_MINI)
                    .build();

            AssistantRag assistantRag = AiServices.builder(AssistantRag.class)
                    .chatLanguageModel(chatModel)
                    .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                    .contentRetriever(contentRetriever)
                    .build();

            chatServiceRag.setAssistant(assistantRag);

            Quarkus.waitForExit();
            return 0;
        }
    }

}
