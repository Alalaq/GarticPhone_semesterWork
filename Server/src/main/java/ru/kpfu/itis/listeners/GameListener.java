package ru.kpfu.itis.listeners;

import com.google.gson.JsonSyntaxException;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.general.DrawingCode;
import ru.kpfu.itis.general.helpers.parsers.TextParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameListener extends AbstractServerEventListener {
    protected static Map<Long, List<Long>> drawingsSentTo;
    public GameListener() {
        super(Constants.READINESS);
        drawingsSentTo = new HashMap<>();
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

        int currentRound = room.getCurrentRound();

        int playersAmount = room.getPlayers().size();

        for (Connection connection : connectionList) {
            Player player = connection.getPlayer();
            if (player.getRoom().equals(room)) {
                if (player.getReadiness()) {
                    Long id = player.getId();
                    long idDrawingFrom;

                    if (id + currentRound >= playersAmount) {
                        idDrawingFrom = id + currentRound - playersAmount;
                    } else {
                        idDrawingFrom = id + currentRound;
                    }

                    code = DrawingCode.builder()
                            .round(1)
                            .playerId(idDrawingFrom)
                            .isUsed(false)
                            .build();

                    if (!drawingsSentTo.containsKey(idDrawingFrom)){
                        List<Long> list = new ArrayList<>(20);
                        drawingsSentTo.put(idDrawingFrom, list);
                    }

                    if (drawings.containsKey(code)) {
                        byte[] drawingBytes = (drawings.get(code));
                        message = new Message(Constants.NEXT_ROUND, drawingBytes);
                        server.sendMessage(connection, message);
                        //тут индекс - раунд, на котором получена картинка, айди - чел, который получил её
                        drawingsSentTo.get(idDrawingFrom).add(id);
                        drawings.remove(code);

                        code.setUsed(true);
                        drawings.put(code, drawingBytes);
                        player.setReadiness(false);
                    }
                }
            }
        }
        if (currentRound == playersAmount - 1) {
            message = new Message(Constants.GAME_ENDED);
            server.sendMulticastMessage(room, message);
            message = new Message(Constants.USERS_CHANGED, (room.getPlayersNicknames().toString().getBytes(StandardCharsets.UTF_8)));
            server.sendMulticastMessage(room, message);
        } else {
            room.setCurrentRound(currentRound + 1);
        }
    }
}
