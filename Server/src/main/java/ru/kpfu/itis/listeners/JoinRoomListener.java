package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

import java.nio.charset.StandardCharsets;

public class JoinRoomListener extends AbstractServerEventListener {
    public JoinRoomListener(){
        super(Constants.JOIN_ROOM);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Room joinedRoom = connection.getServer().getRoom();
        Player player = connection.getPlayer();
        Server server = connection.getServer();
        boolean joined = player.inRoom() && joinedRoom.getPlayers().contains(player);
        Message giveAdminRights = new Message(Constants.GIVE_ADMIN_PERMISSION);

        if (!joined){
            joinedRoom.addPlayer(player);
            player.setRoom(joinedRoom);
            if (joinedRoom.getPlayers().size() == 1){
                player.setIsAdmin(true);
                server.sendMessage(connection, giveAdminRights);
            }
            player.setReadiness(false);
        }

        Message allowJoin = new Message(Constants.ALLOW_JOIN, PlayerParser.serializeObject(player));
        Message usersChanged = new Message(Constants.USERS_CHANGED, joinedRoom.getPlayersNicknames().toString().getBytes(StandardCharsets.UTF_8));

        server.sendMulticastMessage(joinedRoom, allowJoin);
        server.sendMulticastMessage(joinedRoom, usersChanged);
    }
}
