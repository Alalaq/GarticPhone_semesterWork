package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.DrawingParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.util.List;
import java.util.Map;

public class ReadinessListener extends AbstractServerEventListener {
    public ReadinessListener(){
        super(Constants.READINESS);
    }
    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room room = player.getRoom();
        Map<Long, Drawing> drawings = room.getDrawings();

        if (player.getReadiness()){
            player.setReadiness(false);
            drawings.remove((player.getId()));
        }
        else{
            player.setReadiness(true);
            drawings.put((player.getId()), DrawingParser.deserializeObject(message.getBody()));
        }

        if (room.isAllReady()){
            beginRound(room);
        }
    }

    protected void beginRound(Room room){
        List<Connection> connectionList = server.getAllConnections();
        Map<Long, Drawing> drawings = room.getDrawings();
        Message message;
        for (Connection connection : connectionList){
            Player player = connection.getPlayer();
            if (player.getRoom().equals(room)) {
                for (int i = 0; i < drawings.size(); i++) {
                    if (player.getId() != i) {
                        message = new Message(Constants.NEXT_ROUND, DrawingParser.serializeObject(drawings.get((long) i)));
                        server.sendMessage(connection, message);
                        drawings.remove((long) i);
                        player.setReadiness(false);
                    }
                }
            }
        }
    }
}
