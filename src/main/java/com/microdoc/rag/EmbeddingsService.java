package com.microdoc.rag;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.spi.ServiceHelper;
import dev.langchain4j.spi.model.embedding.EmbeddingModelFactory;
import dev.langchain4j.store.embedding.CosineSimilarity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.Iterator;

@ApplicationScoped
public class EmbeddingsService {

    private static EmbeddingModel loadEmbeddingModel() {
        Collection<EmbeddingModelFactory> factories = ServiceHelper.loadFactories(EmbeddingModelFactory.class);
        if (factories.size() > 1) {
            throw new RuntimeException("Conflict: multiple embedding models have been found in the classpath. Please explicitly specify the one you wish to use.");
        } else {
            Iterator var1 = factories.iterator();
            if (var1.hasNext()) {
                EmbeddingModelFactory factory = (EmbeddingModelFactory)var1.next();
                EmbeddingModel embeddingModel = factory.create();
                return embeddingModel;
            } else {
                return null;
            }
        }
    }

    EmbeddingModel embeddingModel;
    public void init(){
        embeddingModel = loadEmbeddingModel();
    }

    public EmbeddingModel getEmbeddingModel() {
        return embeddingModel;
    }

    public String embed(String input) {
        TextSegment segment = TextSegment.from(input);
        Embedding embedding = embeddingModel.embed(segment).content();
        return embedding.toString();
    }

    public double distance(String s1, String s2) {
        Embedding e1 = embeddingModel.embed(s1).content();
        Embedding e2 = embeddingModel.embed(s2).content();
        return CosineSimilarity.between(e1, e2);
    }
}
