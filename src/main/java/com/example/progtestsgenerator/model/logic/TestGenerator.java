package com.example.progtestsgenerator.model.logic;

import java.io.*;

public abstract class TestGenerator
{
    protected String Topic;
    protected Integer NumOfQuestions;
    protected String ProgLang;

    protected Integer ComplexityLevel;

    public TestGenerator(String Topic, Integer NumOfQuestions, String ProgLang, Integer ComplexityLevel)
    {
        this.Topic = Topic;
        this.NumOfQuestions = NumOfQuestions;
        this.ProgLang = ProgLang;
        this.ComplexityLevel = ComplexityLevel;
    }

    public String buildPrompt(String promptFile, String examplesFile) throws IOException
    {
        StringBuilder promptBuilder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(promptFile)))
        {
            String line;
            while ((line = br.readLine()) != null)
                promptBuilder.append(line).append("\n");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(examplesFile)))
        {
            String line;
            while ((line = br.readLine()) != null)
                promptBuilder.append(line).append("\n");
        }
        promptBuilder.append("Programavimo kalba: ").append(ProgLang).append("\n")
                .append("Tema: ").append(Topic).append("\n")
                .append("Klausimų skaičius: ").append(NumOfQuestions);

        return promptBuilder.toString();
    }

    protected String removeMarkdowns(String response)
    {
        if (response == null) return "";

        String result = response.trim();

        result = result.replaceAll("(?s)```.*?\\n", "");
        result = result.replaceAll("```", "");
        result = result.trim();

        return result;
    }

    abstract public String generate(String prompt);
}
