package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

public class VoteListener extends AbstractServerEventListener {
    public VoteListener(){
        super(Constants.VOTED);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();

        if (!player.getStatus()) {
            player.setStatus(true);
        }

        player.setVote(PlayerParser.deserializeObject(message.getBody()));
    }
}
