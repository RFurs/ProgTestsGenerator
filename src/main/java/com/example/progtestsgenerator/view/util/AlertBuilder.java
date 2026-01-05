package com.example.progtestsgenerator.view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertBuilder
{
    public static void build(Alert.AlertType type, String title, String header, String content)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
}
