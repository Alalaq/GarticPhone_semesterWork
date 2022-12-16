package org.example;

import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            // ну типо сервер работает, надо чтобы где-то вайл тру был чтобы сервер не падал понял
            ServerSocket server = new ServerSocket(8070);
            while (true){
                Socket con = server.accept();

                System.out.println("woke up");
                Thread.sleep(6000);

                new MessageOutputStream(con.getOutputStream()).write(12);
                System.out.println("sleep");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}