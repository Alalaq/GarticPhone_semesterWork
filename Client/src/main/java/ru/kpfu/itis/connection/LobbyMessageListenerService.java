package ru.kpfu.itis.connection;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.TextParser;
import ru.kpfu.itis.gui.controllers.LobbyController;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LobbyMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private LobbyController lobbyController;
    private boolean flag = true;

    public LobbyMessageListenerService(Connection connection, LobbyController lobbyController) {
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.lobbyController = lobbyController;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected() && flag) {
                    Message message = in.readMessage();
                    switch (message.getType()) {
                        case Constants.USERS_CHANGED -> {
                            String input = new String(message.getBody(),StandardCharsets.UTF_8);
                            String[] users = input.substring(1,input.length()-1).split(",");
                            Platform.runLater(() -> {
                                lobbyController.updateUsers(users);
                            });
                        }
                        case Constants.GIVE_ADMIN_PERMISSION -> Platform.runLater(() -> {
                            lobbyController.changeAdminPermission();
                        });
                        case Constants.GAME_STARTED -> {
                            flag = false;
                            Platform.runLater(() -> {
                                lobbyController.startGame();
                            });
                        }
                        case Constants.GAME_START_DENIED -> Platform.runLater(() -> {
                            new Alert(Alert.AlertType.ERROR, TextParser.deserializeMessage(message.getBody())).show();
                        });
                    }
                }
                return null;
            }
        };
    }
}
