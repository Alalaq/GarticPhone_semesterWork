package ru.kpfu.itis.server;

import lombok.Data;
import ru.kpfu.itis.exceptions.IllegalMessageTypeException;
import ru.kpfu.itis.exceptions.IllegalProtocolVersionException;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.listeners.general.ServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.IOException;
import java.net.Socket;

@Data
public class Connection implements Runnable {
    private static int count = 0;
    protected int id;

    protected Server server;
    protected Socket socket;

    protected MessageInputStream inputStream;
    protected MessageOutputStream outputStream;

    protected Player player;

    public Connection(Server server, Socket socket) throws IOException {
        this.id = count++;
        this.server = server;
        this.socket = socket;
        inputStream = new MessageInputStream(socket.getInputStream());
        outputStream = new MessageOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        Message message;

        try {
            try {
                while ((message = inputStream.readMessage()) != null) {
                    ServerEventListener listener = AbstractServerEventListener.getEventListener(
                            message.getType());
                    listener.init(server);

                    if (player != null || message.getType() == Constants.JOIN_ROOM) {
                        listener.handle(this, message);
                    }
                }
            } catch (IllegalProtocolVersionException | IllegalMessageTypeException e) {
                message = new Message(Constants.ERROR, e.getMessage().getBytes());
                outputStream.writeMessage(message);
            }
        } catch (IOException e) {
            Server.removeConnection(this);
        }
    }
}
