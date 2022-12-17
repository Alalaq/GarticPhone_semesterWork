package ru.kpfu.itis.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.connection.LobbyMessageListenerService;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;


public class LobbyController {
    @FXML
    public Button startGameButton;
    @FXML
    public ListView<String> usersList;
    private Stage stage;

    private Player player;
    private Connection connection;

    public void startGame(ActionEvent actionEvent) {
        //TODO
        new Alert(Alert.AlertType.ERROR,"213123").show();
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
        new LobbyMessageListenerService(stage,usersList,connection,startGameButton,player).start();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPlayer(Player player){
        this.player = player;
        startGameButton.setVisible(player.getIsAdmin());
    }
    public void leaveRoom(ActionEvent actionEvent) {
        connection.sendMessage(new Message(Constants.EXIT_ROOM)); //todo add user to message
        stage.setScene(ScenesManager.getLogInScene(connection,stage));
    }
}
