package ru.kpfu.itis.connection;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.MessageInputStream;


import java.io.IOException;
import java.net.Socket;

public class LogInMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private Stage stage;


    public LogInMessageListenerService(Connection connection, Stage stage) {
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.stage = stage;

    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println(socket.isConnected());
                while (socket.isConnected()) {
                    System.out.println("iteration");
                    int test = in.read();
                    System.out.println(test);
//                    System.out.println(message);
//                    switch (message.getType()) {
//                        case Constants.ALLOW_JOIN -> {
//                            stage.setScene(ScenesManager.getLobbyScene());
//                            stage.show();
//                        }
//                    }
                }
                return null;
            }
        };
    }
}
