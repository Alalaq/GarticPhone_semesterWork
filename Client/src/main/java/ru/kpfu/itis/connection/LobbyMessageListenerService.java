package ru.kpfu.itis.connection;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LobbyMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private Stage stage;
    private ListView<String> userList;
    private Button startGameButton;
    private Connection connection;
    private Player player;


    public LobbyMessageListenerService(Stage stage, ListView<String> userList, Connection connection, Button startGameButton, Player player) {
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.stage = stage;
        this.userList = userList;
        this.connection = connection;
        this.startGameButton = startGameButton;
        this.player = player;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected()) {
                    Message message = in.readMessage();
                    switch (message.getType()) {
                        case Constants.USERS_CHANGED -> {
                            String[] users = new String(message.getBody(), StandardCharsets.UTF_8).split(",");
                            userList.getItems().clear();
                            userList.getItems().addAll(users);
                        }
                        case Constants.GIVE_ADMIN_PERMISSION -> Platform.runLater(() -> {
                            startGameButton.setVisible(true);
                        });
                        case Constants.GAME_STARTED -> stage.setScene(null);// todo сделать сцену игры где уже можно рисовать
                    }
                }
                return null;
            }
        };
    }
}
