package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

public class JoinRoomListener extends AbstractServerEventListener {
    public JoinRoomListener(){
        super(Constants.JOIN_ROOM);
        //todo: parser
        //playerParser = new PlayerParser();
    }

    @Override
    public void handle(Connection connection, Message message) {
        boolean joined = false;
        Room joinedRoom = null;
        Player player = connection.getPlayer();

        for (Room room: Server.getAllRooms()){
            if (!joined && (room.getNumberOfPlayers() < Room.MAX_PLAYERS)){
                room.addPlayer(player);
                player.setRoom(room);

                if (room.getNumberOfPlayers() == Room.MAX_PLAYERS){
                    Server.sendMulticastMessage(room, new Message(Constants.REDINESS));
                }

                joined = true;
                joinedRoom = room;
            }
        }

        if (!joined){
            joinedRoom = Server.createRoom();
            joinedRoom.addPlayer(player);
            player.setRoom(joinedRoom);
        }
        //todo: parser
        Message toClient = new Message(Constants.JOIN_ROOM);
                //playerParser.serializeObject(joinedRoom.getPlayers()));

        Server.sendMulticastMessage(joinedRoom, toClient);
    }
}
