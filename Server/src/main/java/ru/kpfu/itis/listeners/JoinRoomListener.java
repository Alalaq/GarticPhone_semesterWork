package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.general.helpers.parsers.TextParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

import java.nio.charset.StandardCharsets;

public class JoinRoomListener extends AbstractServerEventListener {
    public JoinRoomListener() {
        super(Constants.JOIN_ROOM);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Server server = connection.getServer();
        Room joinedRoom = server.getRoom();
        Player player = connection.getPlayer();
        String isJoinAllowed = "";
        if (player.inRoom()) {
            isJoinAllowed = "Player already in room";
        }
        if (!isCorrectNickname(player.getNickname(), joinedRoom)) {
            isJoinAllowed += "\nThat nickname is already in use in that room";
        }
        if (!(isTherePlace(joinedRoom))) {
            isJoinAllowed += "\nRoom is full";
        }

        if (isJoinAllowed.equals("")) {
            joinedRoom.addPlayer(player);
            player.setRoom(joinedRoom);

            player.setIsAdmin(joinedRoom.getPlayers().size() == 1);

            player.setReadiness(false);

            Message allowJoin = new Message(Constants.ALLOW_JOIN, PlayerParser.serializeObject(player));
            Message usersChanged = new Message(Constants.USERS_CHANGED, joinedRoom.getPlayersNicknames().toString().getBytes(StandardCharsets.UTF_8));

            server.sendMulticastMessage(joinedRoom, allowJoin);
            server.sendMulticastMessage(joinedRoom, usersChanged);
        } else {
            Message joinDenied = new Message(Constants.DENY_JOIN, TextParser.serializeMessage(isJoinAllowed));
            server.sendMessage(connection, joinDenied);
        }
    }

    private boolean isCorrectNickname(String nickname, Room joinedRoom) {
        for (String playersNickname : joinedRoom.getPlayersNicknames()) {
            if (nickname.equals(playersNickname)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTherePlace(Room joinedRoom) {
        return (joinedRoom.getPlayers().size() < Constants.MAXIMUM_PLAYERS);
    }
}
