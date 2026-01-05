package com.example.progtestsgenerator.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/example/progtestsgenerator/main_tabs.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 460, 420);
        stage.setResizable(false);
        stage.setTitle("Tests generator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}