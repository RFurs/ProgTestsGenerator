package com.example.progtestsgenerator.model.service;

import com.example.progtestsgenerator.model.util.FileExporter;

import java.io.IOException;

public class apiKeyService {

    public void saveKeys(String gptKey, String geminiKey) throws IOException
    {
        if (gptKey.isBlank() || geminiKey.isBlank())
        {
            throw new IllegalArgumentException("API raktai tušti");
        }
        FileExporter.export(gptKey, "resources/GPT_API_Key.txt");
        FileExporter.export(geminiKey, "resources/Gemini_API_Key.txt");
    }
}
