package org.example;

import ru.kpfu.itis.server.Server;

import java.rmi.ServerException;

public class App {
    public static void main(String[] args) {
        int port = 8102;
        Server server = new Server(port);
        try {
            server.start();
        }catch (ServerException e) {
            throw new IllegalStateException(e);
        }
    }
}
