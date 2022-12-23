package ru.kpfu.itis.connection;

import com.google.gson.internal.bind.util.ISO8601Utils;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.MessageInputStream;


import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LogInMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private Stage stage;
    private Label errorLabel;
    private boolean flag = true;
    private Connection connection;

    public LogInMessageListenerService(Connection connection, Stage stage, Label label) {
        this.connection = connection;
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.stage = stage;
        this.errorLabel = label;

    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected() && flag) {
                    Message message = in.readMessage();
                    switch (message.getType()) {
                        case Constants.ALLOW_JOIN -> {
                            Platform.runLater(() -> {
                                stage.setScene(ScenesManager.getLobbyScene(
                                        PlayerParser.deserializeObject(message.getBody()),
                                        connection,
                                        stage
                                ));
                                stage.setTitle(PlayerParser.deserializeObject(message.getBody()).getNickname());
                                stage.show();
                            });
                            flag = false;
                        }
                        case Constants.DENY_JOIN -> {
                            Platform.runLater(() -> {
                                errorLabel.setText(new String(message.getBody(), StandardCharsets.UTF_8));
                                errorLabel.setVisible(true);
                            });
                        }
                    }
                }
                return null;
            }
        };
    }
}
