package ru.kpfu.itis.gui.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.ClientApp;
import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.exceptions.SceneManagerException;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.gui.controllers.GameController;
import ru.kpfu.itis.gui.controllers.LobbyController;
import ru.kpfu.itis.gui.controllers.LogInController;
import ru.kpfu.itis.gui.controllers.ResultController;

import java.io.IOException;

public class ScenesManager {
    private static FXMLLoader loader;
    private static Parent root;

    public static Scene getLobbyScene(Player player, Connection connection, Stage stage) {
        loader = new FXMLLoader(ClientApp.class.getResource("lobby.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new SceneManagerException("Error while loading",e);
        }
        LobbyController controller = loader.getController();
        controller.setStage(stage);
        controller.setPlayer(player);
        controller.setConnection(connection);
        return new Scene(root);
    }

    public static Scene getLogInScene(Stage stage) {
        loader = new FXMLLoader(ClientApp.class.getResource("login.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new SceneManagerException("Error while loading",e);
        }
        LogInController controller = loader.getController();
        controller.setStage(stage);
        return new Scene(root);
    }

    public static Scene getGameScene(Connection connection, Stage stage, Player player) {
        loader = new FXMLLoader(ClientApp.class.getResource("game.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new SceneManagerException("Error while loading",e);
        }
        GameController controller = loader.getController();
        controller.setStage(stage);
        controller.setPlayer(player);
        controller.setConnection(connection);
        return new Scene(root);
    }
    public static Scene getResultScene(Connection connection, Stage stage, Player player) {
        loader = new FXMLLoader(ClientApp.class.getResource("result.fxml"));
        stage.setResizable(true);
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new SceneManagerException("Error while loading",e);
        }
        ResultController controller = loader.getController();
        controller.setPlayer(player);
        controller.setStage(stage);
        controller.setConnection(connection);
        return new Scene(root);
    }
}
