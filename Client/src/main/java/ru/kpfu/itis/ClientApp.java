package ru.kpfu.itis;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.kpfu.itis.gui.helpers.ScenesManager;

import java.io.IOException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(ScenesManager.getLogInScene(stage));

        stage.setWidth(800);
        stage.setHeight(600);
        stage.setTitle("Hello!");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}