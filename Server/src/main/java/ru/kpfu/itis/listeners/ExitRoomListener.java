package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

import java.nio.charset.StandardCharsets;

public class ExitRoomListener extends AbstractServerEventListener {
    public ExitRoomListener() {
        super(Constants.EXIT_ROOM);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room playerRoom = player.getRoom();
        Server server = connection.getServer();

        playerRoom.deletePlayer(player);
        player.exitRoom();

        Message successfulExit = new Message(Constants.USERS_CHANGED,
                playerRoom.getPlayersNicknames().toString().getBytes(StandardCharsets.UTF_8));

        server.removeConnection(connection);
        Connection newAdminConnection = server.getAllConnections().get(0);

        server.sendMulticastMessage(playerRoom, successfulExit);
        server.sendMessage(newAdminConnection, new Message(Constants.GIVE_ADMIN_PERMISSION));
    }
}
