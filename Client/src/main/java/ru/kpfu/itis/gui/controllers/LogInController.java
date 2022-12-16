package ru.kpfu.itis.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.connection.LogInMessageListenerService;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LogInController {
    @FXML
    public TextField nickname;
    @FXML
    public Button logInButton;
    @FXML
    public Label errorLabel;

    private Stage stage;
    private Connection connection;


    public void logIn(ActionEvent actionEvent) throws IOException {
        String userName = nickname.getText();

        if (!userName.isEmpty()) {
            connection.sendMessage(new Message((byte) 1, userName.getBytes(StandardCharsets.UTF_8)));
        } else {
            errorLabel.setText("Name shouldn't be empty");
            errorLabel.setVisible(true);
        }
    }

    private void changeScene() throws IOException {
        stage.setScene(ScenesManager.getLobbyScene());
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        new LogInMessageListenerService(connection, stage).start();
    }

}
