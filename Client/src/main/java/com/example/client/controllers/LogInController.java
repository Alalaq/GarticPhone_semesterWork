package com.example.client.controllers;

import com.example.client.ClientApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    @FXML
    public TextField nickname;
    @FXML
    public Button logInButton;

    private Stage stage;

    public void logIn(ActionEvent actionEvent) throws IOException {
        if(true){
           changeScene();

        }
    }

    private void changeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(ClientApp.class.getResource("lobby.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
