package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.TextParser;
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
        Room room = connection.getPlayer().getRoom();
        Message gameStart;
        if (room.getPlayers().size() < Constants.MIN_PLAYERS){
            gameStart = new Message(Constants.GAME_START_DENIED, TextParser.serializeMessage("There must be at least 3 players in a room."));
            server.sendMessage(connection, gameStart);
        }
        else {
            gameStart = new Message(Constants.GAME_STARTED);
            server.sendMulticastMessage(connection.getPlayer().getRoom(), gameStart);
        }
    }
}
