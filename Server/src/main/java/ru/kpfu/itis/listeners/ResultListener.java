package ru.kpfu.itis.listeners;

import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.general.DrawingCode;
import ru.kpfu.itis.general.helpers.parsers.DrawingParser;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.listeners.general.AbstractServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultListener extends AbstractServerEventListener {
    private static int branchesSendingIteration = -1;


    public ResultListener() {
        super(Constants.REQUIRE_NEW_BRANCH);
        branchesSendingIteration++;
    }

    @Override
    public void handle(Connection connection, Message message) {
        Player player = connection.getPlayer();
        Room room = player.getRoom();
        List<Player> players = room.getPlayers();
        if (branchesSendingIteration < players.size()) {
            DrawingCode code;
            Map<DrawingCode, byte[]> drawingsToSend;
            List<Drawing> drawings = new ArrayList<>();
            Message branchSent;
            Long playersId;

            for (int i = 0; i < players.size(); i++) {
                if (i == 0) {
                    playersId = (long) branchesSendingIteration;
                } else {
                    playersId = room.getDrawingsSentTo().get((long) branchesSendingIteration).get(i - 1);
                }

                code = DrawingCode.builder()
                        .round(i + 1)
                        .playerId(playersId)
                        .isUsed(true)
                        .build();

                drawingsToSend = room.getDrawings();

                if (!drawingsToSend.containsKey(code)) {
                    code.setUsed(false);
                }
                drawings.add(new Drawing(drawingsToSend.get(code), players.get(Math.toIntExact(playersId)).getNickname(), players.get(Math.toIntExact(playersId)).getId()));
            }
            for (Drawing drawing : drawings) {
                branchSent = new Message(Constants.SENDED_ONE_GAME_BRANCH, DrawingParser.serializeObject(drawing));
                server.sendMulticastMessage(room, branchSent);
            }
        } else {
            Message sendWinner = new Message(Constants.USER_WINNER, PlayerParser.serializeObject(room.getWinner()));
            server.sendMulticastMessage(room, sendWinner);
            server.sendMulticastMessage(room, new Message(Constants.GAME_ENDED));
        }
    }
}
