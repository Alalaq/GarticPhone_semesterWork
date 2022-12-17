package ru.kpfu.itis.app;

import ru.kpfu.itis.server.Server;

import java.rmi.ServerException;

public class App {
    public static void main(String[] args) {
        Server server = new Server(11001);
        try {
            server.start();
        }catch (ServerException e) {
            throw new IllegalStateException(e);
        }
    }
}
