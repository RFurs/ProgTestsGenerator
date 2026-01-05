package com.example.progtestsgenerator.model.logic;
public abstract class CrossTester
{
    public abstract String testClassification(String prompt, Integer level);
    public abstract String testCorrectness(String prompt);

    protected String removeMarkdowns(String response)
    {
        if (response == null) return "";

        String result = response.trim();

        result = result.replaceAll("(?s)```.*?\\n", "");
        result = result.replaceAll("```", "");
        result = result.trim();

        return result;
    }

}
