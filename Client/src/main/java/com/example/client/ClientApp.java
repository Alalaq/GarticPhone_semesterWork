package com.example.client;

import com.example.client.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 320, 240);
        stage.setScene(scene);

        LogInController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("Hello!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}