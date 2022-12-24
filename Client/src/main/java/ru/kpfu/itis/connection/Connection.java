package ru.kpfu.itis.connection;

import ru.kpfu.itis.exceptions.ConnectionException;
import ru.kpfu.itis.exceptions.MessageSendingException;
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
            close();
            throw new ConnectionException("Can't init connection",e);
        }
    }


    public void sendMessage(Message message) {
        try {
            outputStream.writeMessage(message);
        } catch (IOException e) {
            close();
            throw new MessageSendingException("Can't send message",e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new ConnectionException("Error closing socket",e);
        }
    }

    public MessageInputStream getInputStream() {
        return inputStream;
    }
}
