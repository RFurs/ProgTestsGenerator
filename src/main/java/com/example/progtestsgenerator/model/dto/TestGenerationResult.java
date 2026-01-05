package com.example.progtestsgenerator.model.dto;

public class TestGenerationResult {

    private final String testContent;
    private final String classificationResult;
    private final String correctnessResult;
    private final boolean classificationOk;
    private final boolean correctnessOk;
    private final String outputFileName;

    public TestGenerationResult(
            String testContent,
            String classificationResult,
            String correctnessResult,
            boolean classificationOk,
            boolean correctnessOk,
            String outputFileName
    ) {
        this.testContent = testContent;
        this.classificationResult = classificationResult;
        this.correctnessResult = correctnessResult;
        this.classificationOk = classificationOk;
        this.correctnessOk = correctnessOk;
        this.outputFileName = outputFileName;
    }

    public String getTestContent() { return testContent; }
    public String getClassificationResult() { return classificationResult; }
    public String getCorrectnessResult() { return correctnessResult; }
    public boolean isClassificationOk() { return classificationOk; }
    public boolean isCorrectnessOk() { return correctnessOk; }
    public String getOutputFileName() { return outputFileName; }
}
