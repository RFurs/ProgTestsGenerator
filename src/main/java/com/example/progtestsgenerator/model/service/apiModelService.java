package com.example.progtestsgenerator.model.service;

import java.io.IOException;
import java.util.List;
import com.example.progtestsgenerator.model.util.FileExporter;

public class apiModelService
{
    private static final List<String> GPT_MODELS = List.of(
            "gpt-5.1",
            "gpt-5-mini",
            "gpt-5-nano",
            "gpt-5-pro",
            "gpt-4.1",
            "gpt-4.1-mini",
            "gpt-4.1-nano"
    );

    private static final List<String> GEMINI_MODELS = List.of(
            "gemini-3-pro",
            "gemini-2.5-pro",
            "gemini-2.5-flash",
            "gemini-2.5-flash-lite",
            "gemini-2.0-flash"
    );
    public List<String> getGptModels() {return GPT_MODELS;}
    public List<String> getGeminiModels() {return GEMINI_MODELS;}
    public void saveSelectedModels(String gptModel, String geminiModel) throws IOException
    {
        if (gptModel == null || geminiModel == null)
        {
            throw new IllegalArgumentException("Modeliai nepasirinkti");
        }

        FileExporter.export(gptModel, "resources/GPT_Model.txt");
        FileExporter.export(geminiModel, "resources/Gemini_Model.txt");
    }
}
