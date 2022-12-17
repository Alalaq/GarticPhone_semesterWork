package org.example;

import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            // ну типо сервер работает, надо чтобы где-то вайл тру был чтобы сервер не падал понял
            ServerSocket server = new ServerSocket(8070);
            while (true){
                Socket con = server.accept();
                Thread.sleep(1000);
                MessageOutputStream out = new MessageOutputStream(con.getOutputStream());
                new MessageOutputStream(con.getOutputStream()).writeMessage(new Message(Constants.ALLOW_JOIN));
                new MessageOutputStream(con.getOutputStream()).writeMessage(new Message(Constants.USERS_CHANGED,"213,123,321".getBytes(StandardCharsets.UTF_8)));
                out.writeMessage(new Message(Constants.GIVE_ADMIN_PERMISSION));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}