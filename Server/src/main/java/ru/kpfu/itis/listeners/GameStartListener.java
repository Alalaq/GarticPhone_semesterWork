package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.util.List;

public class GameStartListener extends AbstractServerEventListener {
    public GameStartListener(){
        super(Constants.START_GAME);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Message gameStart = new Message(Constants.GAME_STARTED);
        server.sendMulticastMessage(connection.getPlayer().getRoom(), gameStart);
    }
}
