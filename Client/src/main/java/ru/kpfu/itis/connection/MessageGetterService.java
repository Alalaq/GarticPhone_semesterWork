package ru.kpfu.itis.connection;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.Socket;

public class MessageGetterService extends Service<Void> {

    private Socket socket;



    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                return null;
            }
        };
    }
}
