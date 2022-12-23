package ru.kpfu.itis.listeners;

import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.ArrayUtils;
import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.DrawingParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.util.*;

public class ReadinessListener extends AbstractServerEventListener {
    public static boolean message_sending;

    public static int messages_count;

    protected static List<Byte[]> drawingParts;

    public ReadinessListener(boolean message_sending) {
        super(Constants.READINESS);
        ReadinessListener.message_sending = message_sending;
        ReadinessListener.messages_count = -1;
        ReadinessListener.drawingParts = new ArrayList<>();
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room room = player.getRoom();
        Map<Long, Byte[]> drawings = room.getDrawings();

        if (ReadinessListener.message_sending) {
            ReadinessListener.drawingParts.add(ArrayUtils.toObject(message.getBody()));
        } else {
            if (player.getReadiness()) {
                player.setReadiness(false);
                drawings.remove((player.getId()));
            } else {
                player.setReadiness(true);
                try {
                    drawings.put((player.getId()), ReadinessListener.drawingParts.toArray(new Byte[0]));
                } catch (NumberFormatException | JsonSyntaxException exc) {
                    System.out.println(Arrays.toString(message.getBody()));
                }
            }

            if (room.isAllReady()) {
                beginRound(room);
            }
        }
    }

    protected void beginRound(Room room) {
        List<Connection> connectionList = server.getAllConnections();
        Map<Long, Byte[]> drawings = room.getDrawings();
        Message message;
        for (Connection connection : connectionList) {
            Player player = connection.getPlayer();
            if (player.getRoom().equals(room)) {
                for (int i = 0; i < drawings.size(); i++) {
                    if (player.getId() != i) {
                        message = new Message(Constants.NEXT_ROUND, ArrayUtils.toPrimitive(drawings.get((long) i)));
                        server.sendMessage(connection, message);
                        drawings.remove((long) i);
                        player.setReadiness(false);
                    }
                }
            }
        }
    }
}
