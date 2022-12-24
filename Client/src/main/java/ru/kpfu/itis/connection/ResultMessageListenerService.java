package ru.kpfu.itis.connection;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.helpers.parsers.DrawingParser;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.gui.controllers.ResultController;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResultMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private ResultController resultController;
    private Connection connection;

    public ResultMessageListenerService(ResultController resultController) {
        this.resultController = resultController;
        this.connection = resultController.getConnection();
        this.socket = connection.getSocket();;
        this.in = connection.getInputStream();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected()){
                    Message message = in.readMessage();
                    switch (message.getType()){
                        case Constants.USERS_CHANGED -> {
                            String[] users = new String(message.getBody(), StandardCharsets.UTF_8).replace("[", "").replace("]", "").split(",");
                            Platform.runLater(()->{
                                resultController.updateUsers(users);
                            });
                        }
                        case Constants.SENDED_ONE_GAME_BRANCH ->{
                            List<Drawing> drawings = DrawingParser.deserializeObjects(message.getBody());
                            Platform.runLater(()->{
                                resultController.showOneGameBranch(drawings);
                            });
                        }
                        case Constants.USER_WINNER -> {
                            String name = PlayerParser.deserializeObject(message.getBody()).getNickname();
                            Platform.runLater(()->{
                                resultController.showUserWinnerAlert(name);
                            });
                        }
                    }
                }
                return null;
            }
        };
    }
}
