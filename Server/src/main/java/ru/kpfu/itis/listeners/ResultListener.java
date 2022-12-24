package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.general.DrawingCode;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultListener extends AbstractServerEventListener {
    private static int branchesSendingIteration;

    public ResultListener() {
        super(Constants.REQUIRE_NEW_BRANCH);
        branchesSendingIteration = 0;
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room room = player.getRoom();
        List<Player> players = room.getPlayers();
        DrawingCode code;
        Map<DrawingCode, byte[]> drawingsToSend;
        List<Drawing> drawings = new ArrayList<>();
        Message branchSent;
        Long playersId;

        for (int i = 1; i < players.size() - 1; i++) {
            if (i == 1) {
                playersId = (long) branchesSendingIteration;
            } else {
                playersId = GameListener.drawingsSentTo.get((long) branchesSendingIteration).get(i);
            }

            code = DrawingCode.builder()
                    .round(i)
                    .playerId(playersId)
                    .isUsed(true)
                    .build();

            drawingsToSend = room.getDrawings();
            drawings.add(new Drawing(drawingsToSend.get(code), players.get(branchesSendingIteration).getNickname()));
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);

            out.writeObject(drawings);

            branchSent = new Message(Constants.SENDED_ONE_GAME_BRANCH, baos.toByteArray());
            server.sendMessage(connection, branchSent);
        } catch (IOException ignored) {
        }


    }
}
