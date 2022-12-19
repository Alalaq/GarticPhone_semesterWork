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
    PlayerParser playerParser;
    public JoinRoomListener(){
        super(Constants.JOIN_ROOM);
        playerParser = new PlayerParser();
    }

    @Override
    public void handle(Connection connection, Message message) {
        boolean joined = false;
        Room joinedRoom = null;
        Player player = connection.getPlayer();


        if (!joined){
            joinedRoom = Server.createRoom();
            joinedRoom.addPlayer(player);
            player.setRoom(joinedRoom);
            if (joinedRoom.getPlayers().size() == 1){
                player.setIsAdmin(true);
            }
            else {
                player.setIsAdmin(false);
            }
            player.setReadiness(false);
        }

        Message allowJoin = new Message(Constants.ALLOW_JOIN, PlayerParser.serializeObject(player));
        Message usersChanged = new Message(Constants.USERS_CHANGED, joinedRoom.getPlayersNicknames().toString().getBytes(StandardCharsets.UTF_8));
        Message giveAdminRights = new Message(Constants.GIVE_ADMIN_PERMISSION);

        Server.sendMulticastMessage(joinedRoom, allowJoin);
        Server.sendMulticastMessage(joinedRoom, usersChanged);
        Server.sendMulticastMessage(joinedRoom, giveAdminRights);
    }
}
