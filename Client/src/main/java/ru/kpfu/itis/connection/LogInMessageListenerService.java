package ru.kpfu.itis.connection;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.gui.controllers.LogInController;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LogInMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private Connection connection;
    private LogInController logInController;
    private boolean flag = true;

    public LogInMessageListenerService(Connection connection,LogInController logInController) {
        this.connection = connection;
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.logInController = logInController;
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
                                logInController.joinRoom(PlayerParser.deserializeObject(message.getBody()));
                            });
                            flag = false;
                        }
                        case Constants.DENY_JOIN -> {
                            Platform.runLater(() -> {
                                logInController.joinDenied(new String(message.getBody(), StandardCharsets.UTF_8));
                            });
                        }
                    }
                }
                return null;
            }
        };
    }
}
