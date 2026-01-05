package com.example.progtestsgenerator.controller;

import com.example.progtestsgenerator.model.dto.TestGenerationRequest;
import com.example.progtestsgenerator.model.dto.TestGenerationRequest.AiProvider;
import com.example.progtestsgenerator.model.dto.TestGenerationRequest.OutputFormat;
import com.example.progtestsgenerator.model.dto.TestGenerationResult;
import com.example.progtestsgenerator.model.service.TestGenerationService;
import com.example.progtestsgenerator.view.util.AlertBuilder;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TestsGeneratorController implements Initializable
{

    private static final int MIN_QUESTIONS_NUM = 1;
    private static final int MAX_QUESTIONS_NUM = 50;
    private static final int MIN_COMPLEXITY = 1;
    private static final int MAX_COMPLEXITY = 5;

    private static final String[] LANGUAGES =
            {
            "Java", "Python", "C", "C++",
            "C#", "Go", "Javascript", "Ruby", "PHP"
    };

    private final TestGenerationService generationService =
            new TestGenerationService();


    @FXML private TextField Topic;
    @FXML private ChoiceBox<String> ProgrammingLanguage;
    @FXML private Spinner<Integer> NumOfQuestions;
    @FXML private Spinner<Integer> ComplexityLevel;

    @FXML private RadioButton GPT_rb;
    @FXML private RadioButton Gemini_rb;
    @FXML private RadioButton GIFT_rb;
    @FXML private RadioButton XML_rb;

    @FXML private CheckBox CrossTestingComplexity_cb;
    @FXML private CheckBox CrossTestingCorrectness_cb;

    @FXML private Text statusMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        ProgrammingLanguage.getItems().addAll(LANGUAGES);

        SpinnerValueFactory<Integer> questionsFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        MIN_QUESTIONS_NUM, MAX_QUESTIONS_NUM, MIN_QUESTIONS_NUM);
        NumOfQuestions.setValueFactory(questionsFactory);

        SpinnerValueFactory<Integer> complexityFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        MIN_COMPLEXITY, MAX_COMPLEXITY, MIN_COMPLEXITY);
        ComplexityLevel.setValueFactory(complexityFactory);

        statusMessage.setVisible(false);
    }

    @FXML public void onGPT() { Gemini_rb.setSelected(false); }
    @FXML public void onGemini() { GPT_rb.setSelected(false); }
    @FXML public void onGIFT() { XML_rb.setSelected(false); }
    @FXML public void onXML() { GIFT_rb.setSelected(false); }

    @FXML
    public void onClear()
    {
        Topic.clear();
        ProgrammingLanguage.getSelectionModel().clearSelection();
        NumOfQuestions.getValueFactory().setValue(MIN_QUESTIONS_NUM);
        ComplexityLevel.getValueFactory().setValue(MIN_COMPLEXITY);
        GPT_rb.setSelected(false);
        Gemini_rb.setSelected(false);
        GIFT_rb.setSelected(false);
        XML_rb.setSelected(false);
    }
    @FXML
    public void onGenerate()
    {

        if (!validateInput())
        {
            return;
        }

        TestGenerationRequest request = buildRequestFromUI();

        Task<TestGenerationResult> task = new Task<>() {
            @Override
            protected TestGenerationResult call() {
                return generationService.generate(request);
            }
        };

        task.setOnSucceeded(e -> handleResult(task.getValue()));

        task.setOnFailed(e ->
                AlertBuilder.build(
                        Alert.AlertType.ERROR,
                        "Testo klaida",
                        "Nepavyko sugeneruoti testo",
                        task.getException().getMessage()
                )
        );

        new Thread(task).start();
    }
    private boolean validateInput()
    {

        if (Topic.getText().isBlank())
        {
            AlertBuilder.build(
                    Alert.AlertType.WARNING,
                    "Temos įvedimas",
                    "Testo tema nėra įvesta",
                    "Prašome įvesti testo temą."
            );
            return false;
        }

        if (ProgrammingLanguage.getValue() == null)
        {
            AlertBuilder.build(
                    Alert.AlertType.WARNING,
                    "Programavimo kalbos pasirinkimas",
                    "Programavimo kalba nepasirinkta",
                    "Prašome pasirinkti programavimo kalbą."
            );
            return false;
        }

        if (!GPT_rb.isSelected() && !Gemini_rb.isSelected())
        {
            AlertBuilder.build(
                    Alert.AlertType.WARNING,
                    "DI modelio pasirinkimas",
                    "DI modelis nepasirinktas",
                    "Prašome pasirinkti GPT arba Gemini."
            );
            return false;
        }

        if (!GIFT_rb.isSelected() && !XML_rb.isSelected())
        {
            AlertBuilder.build(
                    Alert.AlertType.WARNING,
                    "Išvesties formato pasirinkimas",
                    "Išvesties formatas nepasirinktas",
                    "Prašome pasirinkti GIFT arba XML."
            );
            return false;
        }

        return true;
    }
    private TestGenerationRequest buildRequestFromUI()
    {

        AiProvider provider =
                GPT_rb.isSelected()
                        ? AiProvider.GPT
                        : AiProvider.GEMINI;

        OutputFormat format =
                GIFT_rb.isSelected()
                        ? OutputFormat.GIFT
                        : OutputFormat.XML;

        return new TestGenerationRequest(
                Topic.getText().trim(),
                ProgrammingLanguage.getValue(),
                NumOfQuestions.getValue(),
                ComplexityLevel.getValue(),
                provider,
                format,
                CrossTestingComplexity_cb.isSelected(),
                CrossTestingCorrectness_cb.isSelected()
        );
    }
    private void handleResult(TestGenerationResult result)
    {

        if (result.isClassificationOk() && result.isCorrectnessOk())
        {
            showTemporaryMessage(
                    "Testas sėkmingai sugeneruotas ir išsaugotas!",
                    5
            );
        }
        else
        {
            showCrossTestDialog(
                    result.getClassificationResult(),
                    result.getCorrectnessResult(),
                    this::onGenerate
            );
        }
    }
    private void showTemporaryMessage(String message, int seconds)
    {

        statusMessage.setText(message);
        statusMessage.setOpacity(1.0);
        statusMessage.setVisible(true);

        FadeTransition fadeOut =
                new FadeTransition(Duration.seconds(1), statusMessage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(seconds));

        fadeOut.setOnFinished(e -> {
            statusMessage.setVisible(false);
            statusMessage.setOpacity(1.0);
            statusMessage.setText("");
        });

        fadeOut.play();
    }

    private void showCrossTestDialog(
            String classification,
            String correctness,
            Runnable onRegenerate)
    {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Cross-Testing Results");
        dialog.setHeaderText("AI aptiko galimas problemas.");

        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);

        StringBuilder sb = new StringBuilder();

        if (classification != null)
        {
            sb.append("Sudėtingumo įvertinimas:\n")
                    .append(classification).append("\n\n");
        }

        if (correctness != null)
        {
            sb.append("Teisingumo įvertinimas:\n")
                    .append(correctness).append("\n");
        }

        area.setText(sb.toString());
        dialog.getDialogPane().setContent(area);

        ButtonType closeBtn =
                new ButtonType("Uždaryti", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType regenBtn =
                new ButtonType("Generuoti iš naujo", ButtonBar.ButtonData.YES);

        dialog.getDialogPane().getButtonTypes().addAll(closeBtn, regenBtn);

        dialog.setResultConverter(btn -> {
            if (btn == regenBtn) {
                onRegenerate.run();
            }
            return null;
        });

        dialog.showAndWait();
    }
}
