package ru.kpfu.itis.listeners;

import com.google.gson.JsonSyntaxException;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.general.DrawingCode;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.util.List;
import java.util.Map;

public class ReadinessListener extends AbstractServerEventListener {
    public ReadinessListener() {
        super(Constants.READINESS);
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room room = player.getRoom();
        Map<DrawingCode, byte[]> drawings = room.getDrawings();

        DrawingCode code = DrawingCode.builder()
                .round(room.getCurrentRound())
                .isUsed(false)
                .playerId(player.getId())
                .build();

        if (player.getReadiness()) {
            player.setReadiness(false);
            drawings.remove(code);
        } else {
            player.setReadiness(true);
            try {
                drawings.put(code, (message.getBody()));
            } catch (NumberFormatException | JsonSyntaxException exc) {
                System.out.println("Something went wrong");
            }
        }
        if (room.isAllReady()) {
            beginRound(room);
        }
    }

    protected void beginRound(Room room) {
        List<Connection> connectionList = server.getAllConnections();
        Map<DrawingCode, byte[]> drawings = room.getDrawings();
        DrawingCode code;
        Message message;

        for (Connection connection : connectionList) {
            Player player = connection.getPlayer();
            if (player.getRoom().equals(room)) {
                    if (player.getReadiness()) {
                        if (!player.getId().equals((long) room.getPlayers().size() - 1)) {
                            code = DrawingCode.builder()
                                    .round(room.getCurrentRound())
                                    .playerId(player.getId() + 1L)
                                    .isUsed(false)
                                    .build();
                        } else {
                            code = DrawingCode.builder()
                                    .round(room.getCurrentRound())
                                    .playerId(0L)
                                    .isUsed(false)
                                    .build();
                        }
                        if (drawings.containsKey(code)) {
                            byte[] drawingBytes = (drawings.get(code));
                            message = new Message(Constants.NEXT_ROUND, drawingBytes);
                            server.sendMessage(connection, message);
                            drawings.remove(code);

                            code.setUsed(true);
                            drawings.put(code, drawingBytes);
                            player.setReadiness(false);
                        }
                    }
            }
        }
        room.setCurrentRound(room.getCurrentRound() + 1);
    }
}
