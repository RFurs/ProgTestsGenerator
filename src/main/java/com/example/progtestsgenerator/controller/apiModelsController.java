package com.example.progtestsgenerator.controller;

import com.example.progtestsgenerator.view.util.AlertBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;
import com.example.progtestsgenerator.model.service.apiModelService;

public class apiModelsController implements javafx.fxml.Initializable
{
    @FXML
    private ChoiceBox<String> GPT_ModelPicker;

    @FXML
    private ChoiceBox<String> Gemini_ModelPicker;

    private final apiModelService modelService = new apiModelService();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        GPT_ModelPicker.getItems().addAll(modelService.getGptModels());
        Gemini_ModelPicker.getItems().addAll(modelService.getGeminiModels());
        GPT_ModelPicker.setValue(modelService.getGptModels().get(0));
        Gemini_ModelPicker.setValue(modelService.getGeminiModels().get(0));
    }

    @FXML
    public void onSave()
    {
        try
        {
            modelService.saveSelectedModels(
                    GPT_ModelPicker.getValue(),
                    Gemini_ModelPicker.getValue()
            );
            AlertBuilder.build(Alert.AlertType.INFORMATION, "API raktų išsaugojimas",
                    "Pavyko išsaugoti API raktus",
                    "API raktai buvo sėkmingai išsaugoti. Galite pradėti generuoti testus.");
        }
        catch (Exception e)
        {
            AlertBuilder.build(Alert.AlertType.ERROR, "API raktų išsaugojimo klaida",
                    "Nepavyko išsaugoti DI modelius",
                    "Prašome pabandyti dar kartą.");
        }
    }

}
