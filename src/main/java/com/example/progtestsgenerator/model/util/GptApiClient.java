package com.example.progtestsgenerator.model.util;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class GptApiClient
{

    private final OpenAIClient client;
    private final String model;

    public GptApiClient(String apiKey, String model)
    {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        this.model = model;
    }

    public String generate(String systemPrompt, String userPrompt)
    {
        ResponseCreateParams params =
                ResponseCreateParams.builder()
                        .model(model)
                        .instructions(systemPrompt)
                        .input(userPrompt)
                        .build();

        Response response = client.responses().create(params);

        return extractText(response);
    }

    private String extractText(Response response)
    {
        String raw = response.output().toString();

        String startToken = "text=";
        String endToken = ", type=output_text";

        int start = raw.indexOf(startToken);
        int end = raw.indexOf(endToken);

        if (start == -1 || end == -1) return "";

        start += startToken.length();
        return raw.substring(start, end).trim();
    }
}

