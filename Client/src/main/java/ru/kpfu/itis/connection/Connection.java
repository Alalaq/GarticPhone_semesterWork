package ru.kpfu.itis.connection;

import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Connection {
    private Socket socket;
    private MessageInputStream inputStream;
    private MessageOutputStream outputStream;

    public Connection(InetAddress address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.inputStream = new MessageInputStream(socket.getInputStream());
            this.outputStream = new MessageOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message){
        //TODO жду writeMessage в стримах
    }

    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            //TODO
        }
    }

}
