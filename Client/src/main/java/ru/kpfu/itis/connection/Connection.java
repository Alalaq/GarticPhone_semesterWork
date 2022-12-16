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
            e.printStackTrace(); //TODO
        }
    }

    public void sendMessage(Message message){
        try {
            outputStream.writeMessage(message);
        } catch (IOException e) {
            throw new IllegalArgumentException("cant send message"); //TODO
        }
    }
    public Socket getSocket(){
        return socket;
    }
    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            //TODO
        }
    }

    public MessageInputStream getInputStream() {
        return inputStream;
    }
}
