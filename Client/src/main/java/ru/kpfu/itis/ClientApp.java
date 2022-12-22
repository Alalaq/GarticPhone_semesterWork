package ru.kpfu.itis;

import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.gui.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(ScenesManager.getLogInScene(stage));

        stage.setWidth(1600);
        stage.setHeight(900);
        stage.setTitle("Hello!");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}