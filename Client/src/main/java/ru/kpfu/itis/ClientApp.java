package ru.kpfu.itis;

import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.gui.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ClientApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 600);
        stage.setScene(scene);

        LogInController controller = fxmlLoader.getController();
        controller.setStage(stage);
        controller.setConnection(initConnection());
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