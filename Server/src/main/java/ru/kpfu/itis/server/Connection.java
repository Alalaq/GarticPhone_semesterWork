package ru.kpfu.itis.server;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.exceptions.IllegalMessageTypeException;
import ru.kpfu.itis.exceptions.IllegalProtocolVersionException;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.TextParser;
import ru.kpfu.itis.listeners.ReadinessListener;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.listeners.general.ServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.IOException;
import java.net.Socket;

@Getter
@Setter
public class Connection implements Runnable {
    private static int count = 0;
    protected long id;

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
                    if (player != null || message.getType() == Constants.ENTRANCE) {

                        if (message.getType() == Constants.START_SENDING_MESSAGE) {
                            ReadinessListener.message_sending = true;
                            continue;
                        }

                        if (message.getType() == Constants.READINESS && ReadinessListener.message_sending) {
                            ReadinessListener.messages_count++;
                        }

                        listener.handle(this, message);
                    }
                }
            } catch (IllegalProtocolVersionException e) {
                message = new Message(Constants.WRONG_PROTOCOL_VERSION, e.getMessage().getBytes());
                outputStream.writeMessage(message);
            } catch (IllegalMessageTypeException exc) {
                message = new Message(Constants.ILLEGAL_MESSAGE_TYPE, exc.getMessage().getBytes());
                outputStream.writeMessage(message);
            }
        } catch (IOException e) {
            this.server.removeConnection(this);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
