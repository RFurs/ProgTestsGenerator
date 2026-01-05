package com.example.progtestsgenerator.controller;

import com.example.progtestsgenerator.view.util.AlertBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import com.example.progtestsgenerator.model.service.apiKeyService;

@SuppressWarnings("unused")
public class apiKeysController
{

    @FXML
    private TextField GPT_Key_tf;
    @FXML
    private TextField Gemini_Key_tf;

    private final apiKeyService keyService = new apiKeyService();

    @FXML
    public void onSave()
    {
        try
        {
            keyService.saveKeys(GPT_Key_tf.getText(), Gemini_Key_tf.getText());
            AlertBuilder.build(Alert.AlertType.INFORMATION, "API raktų išsaugojimas",
                    "API raktai buvo sėkmingai išsaugoti", "");
        }
        catch (Exception e)
        {
            AlertBuilder.build(Alert.AlertType.WARNING, "API raktų išsaugojimas",
                    "Nepavyko išsaugoti API raktų",
                    "Prašome patikrinti, ar visi API raktai įvesti ir pabandyti dar kartą.");
        }
    }
}