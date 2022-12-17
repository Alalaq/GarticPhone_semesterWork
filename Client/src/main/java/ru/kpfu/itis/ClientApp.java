package ru.kpfu.itis;

import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.gui.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.gui.helpers.ScenesManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(ScenesManager.getLogInScene(initConnection(),stage));

        stage.setWidth(500);
        stage.setHeight(500);
        stage.setTitle("Hello!");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public Connection initConnection() {
        try {
            return new Connection(InetAddress.getLocalHost(),8070);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException();
        }
    }
}