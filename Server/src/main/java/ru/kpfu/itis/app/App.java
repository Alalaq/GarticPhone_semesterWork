package ru.kpfu.itis.app;

import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.server.Server;

import java.rmi.ServerException;

public class App {
    public static void main(String[] args) {
        int port = Constants.PORT;
        Server server = new Server(port);
        try {
            server.start();
        }catch (ServerException e) {
            throw new IllegalStateException(e);
        }
    }
}
