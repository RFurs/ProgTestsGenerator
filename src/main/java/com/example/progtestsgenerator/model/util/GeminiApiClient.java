package com.example.progtestsgenerator.model.util;

import com.google.genai.Client;
import com.google.genai.types.*;

public class GeminiApiClient {

    private final Client client;
    private final String model;

    public GeminiApiClient(String apiKey, String model) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
        this.model = model;
    }

    public String generate(String systemPrompt, String userPrompt) {
        Content sysInstruction = Content.fromParts(
                Part.fromText(systemPrompt)
        );

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .systemInstruction(sysInstruction)
                        .build();

        GenerateContentResponse response =
                client.models.generateContent(model, userPrompt, config);

        return response.text();
    }
}
