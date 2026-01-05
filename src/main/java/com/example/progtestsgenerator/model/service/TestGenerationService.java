package com.example.progtestsgenerator.model.service;

import com.example.progtestsgenerator.model.dto.TestGenerationRequest;
import com.example.progtestsgenerator.model.dto.TestGenerationResult;
import com.example.progtestsgenerator.model.logic.*;
import com.example.progtestsgenerator.model.util.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestGenerationService
{

    public TestGenerationResult generate(TestGenerationRequest req)
    {

        try {
            GptApiClient gptClient = new GptApiClient(
                    apiConfigLoader.loadFirstLine("resources/GPT_API_Key.txt"),
                    apiConfigLoader.loadFirstLine("resources/GPT_Model.txt")
            );

            GeminiApiClient geminiClient = new GeminiApiClient(
                    apiConfigLoader.loadFirstLine("resources/Gemini_API_Key.txt"),
                    apiConfigLoader.loadFirstLine("resources/Gemini_Model.txt")
            );

            TestGenerator generator =
                    req.getProvider() == TestGenerationRequest.AiProvider.GPT
                            ? new GptTestGenerator(
                            req.getTopic(),
                            req.getNumQuestions(),
                            req.getLanguage(),
                            req.getComplexity(),
                            gptClient)
                            : new GeminiTestGenerator(
                            req.getTopic(),
                            req.getNumQuestions(),
                            req.getLanguage(),
                            req.getComplexity(),
                            geminiClient);

            String promptFile =
                    req.getFormat() == TestGenerationRequest.OutputFormat.GIFT
                            ? "resources/GIFT_Prompt.txt"
                            : "resources/XML_Prompt.txt";

            String examples =
                    req.getFormat() == TestGenerationRequest.OutputFormat.GIFT
                            ? "resources/Level" + req.getComplexity() + "_GIFT.txt"
                            : "resources/Level" + req.getComplexity() + "_XML.txt";

            String extension =
                    req.getFormat() == TestGenerationRequest.OutputFormat.GIFT
                            ? ".gift"
                            : ".xml";

            String response = generator.generate(
                    generator.buildPrompt(promptFile, examples)
            );

            CrossTester tester =
                    req.getProvider() == TestGenerationRequest.AiProvider.GPT
                            ? new GeminiCrossTester(geminiClient)
                            : new GptCrossTester(gptClient);

            String classification = null;
            String correctness = null;

            if (req.isCheckComplexity())
            {
                int bloomLevel = req.getComplexity() == 5
                        ? req.getComplexity() + 1
                        : req.getComplexity();
                classification = tester.testClassification(response, bloomLevel);
            }

            if (req.isCheckCorrectness())
            {
                correctness = tester.testCorrectness(response);
            }

            String expected = req.getNumQuestions() + "/" + req.getNumQuestions();

            boolean classificationOk =
                    classification == null || classification.startsWith(expected);
            boolean correctnessOk =
                    correctness == null || correctness.startsWith(expected);

            String fileName =
                    req.getTopic() + "_test_level" +
                            req.getComplexity() + "_" +
                            LocalDateTime.now().format(
                                    DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
                            ) + extension;

            FileExporter.export(response, fileName);

            return new TestGenerationResult(
                    response,
                    classification,
                    correctness,
                    classificationOk,
                    correctnessOk,
                    fileName
            );

        } catch (IOException e) {
            throw new RuntimeException("Klaida generuojant testą", e);
        }
    }
}
