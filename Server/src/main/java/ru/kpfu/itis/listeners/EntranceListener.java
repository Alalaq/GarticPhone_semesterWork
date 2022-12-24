package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.TextParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

public class EntranceListener extends AbstractServerEventListener {
    public EntranceListener() {
        super(Constants.ENTRANCE);
    }

    @Override
    public void handle(Connection connection, Message message) {
        String name = TextParser.deserializeMessage(message.getBody()).substring(1);
        Message newMessage;

        Player player = Player.builder()
                .status(false)
                .nickname(name)
                .readiness(false)
                .isAdmin(false)
                .build();

        connection.setPlayer(player);

        newMessage = new Message(Constants.ALLOW_ENTRANCE);
        server.sendMessage(connection, newMessage);
    }
}
