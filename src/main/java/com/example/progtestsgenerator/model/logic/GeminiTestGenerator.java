package com.example.progtestsgenerator.model.logic;

import com.example.progtestsgenerator.model.util.GeminiApiClient;

public class GeminiTestGenerator extends TestGenerator {

    private final GeminiApiClient apiClient;

    public GeminiTestGenerator(
            String topic,
            Integer numQuestions,
            String progLang,
            Integer complexity,
            GeminiApiClient apiClient
    ) {
        super(topic, numQuestions, progLang, complexity);
        this.apiClient = apiClient;
    }

    @Override
    public String generate(String prompt) {
        return removeMarkdowns(apiClient.generate(null, prompt));
    }
}
