package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

public class VoteListener extends AbstractServerEventListener {
    private static int voteCount = 1;
    public VoteListener(){
        super(Constants.VOTED);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room room = player.getRoom();
        if (voteCount < room.getPlayers().size()) {
            if (!player.getStatus()) {
                player.setStatus(true);
                voteCount++;
            }

            player.setVote(PlayerParser.deserializeObject(message.getBody()));

        } else {
            voteCount = 1;
            setStatusesFalse(room);
            Message voteForBranchIsOver = new Message(Constants.BRANCH_VOTE_OVER);
            server.sendMessage(connection, voteForBranchIsOver);
        }
    }

    private void setStatusesFalse(Room room){
        for (Player player : room.getPlayers()){
            player.setStatus(false);
        }
    }
}
