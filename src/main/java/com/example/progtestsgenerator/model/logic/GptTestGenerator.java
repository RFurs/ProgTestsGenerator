package com.example.progtestsgenerator.model.logic;

import com.example.progtestsgenerator.model.util.GptApiClient;

public class GptTestGenerator extends TestGenerator {

    private final GptApiClient apiClient;

    public GptTestGenerator(
            String topic,
            Integer numQuestions,
            String progLang,
            Integer complexity,
            GptApiClient apiClient
    ) {
        super(topic, numQuestions, progLang, complexity);
        this.apiClient = apiClient;
    }

    @Override
    public String generate(String prompt) {
        return removeMarkdowns(apiClient.generate(null, prompt));
    }
}
