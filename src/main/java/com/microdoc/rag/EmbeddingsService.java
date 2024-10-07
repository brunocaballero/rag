package com.microdoc.rag;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.spi.ServiceHelper;
import dev.langchain4j.spi.model.embedding.EmbeddingModelFactory;
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
        // TODO
        return null;
    }

    public double distance(String s1, String s2) {
        //TODO
        return 0.0;
    }
}
