module com.example.progtestsgenerator {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.genai;
    requires openai.java.client.okhttp;
    requires openai.java.core;


    exports com.example.progtestsgenerator.view.util;
    opens com.example.progtestsgenerator.view.util to javafx.fxml;
    exports com.example.progtestsgenerator.controller;
    opens com.example.progtestsgenerator.controller to javafx.fxml;
    exports com.example.progtestsgenerator.app;
    opens com.example.progtestsgenerator.app to javafx.fxml;
    exports com.example.progtestsgenerator.model.service;
    opens com.example.progtestsgenerator.model.service to javafx.fxml;
    exports com.example.progtestsgenerator.model.util;
    opens com.example.progtestsgenerator.model.util to javafx.fxml;
    exports com.example.progtestsgenerator.model.dto;
    exports com.example.progtestsgenerator.model.logic;
    opens com.example.progtestsgenerator.model.logic to javafx.fxml;
}