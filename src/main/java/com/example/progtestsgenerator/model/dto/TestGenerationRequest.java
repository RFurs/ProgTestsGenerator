package com.example.progtestsgenerator.model.dto;

public class TestGenerationRequest {

    public enum AiProvider { GPT, GEMINI }
    public enum OutputFormat { GIFT, XML }

    private final String topic;
    private final String language;
    private final int numQuestions;
    private final int complexity;
    private final AiProvider provider;
    private final OutputFormat format;
    private final boolean checkComplexity;
    private final boolean checkCorrectness;

    public TestGenerationRequest(
            String topic,
            String language,
            int numQuestions,
            int complexity,
            AiProvider provider,
            OutputFormat format,
            boolean checkComplexity,
            boolean checkCorrectness
    ) {
        this.topic = topic;
        this.language = language;
        this.numQuestions = numQuestions;
        this.complexity = complexity;
        this.provider = provider;
        this.format = format;
        this.checkComplexity = checkComplexity;
        this.checkCorrectness = checkCorrectness;
    }

    public String getTopic() { return topic; }
    public String getLanguage() { return language; }
    public int getNumQuestions() { return numQuestions; }
    public int getComplexity() { return complexity; }
    public AiProvider getProvider() { return provider; }
    public OutputFormat getFormat() { return format; }
    public boolean isCheckComplexity() { return checkComplexity; }
    public boolean isCheckCorrectness() { return checkCorrectness; }
}
