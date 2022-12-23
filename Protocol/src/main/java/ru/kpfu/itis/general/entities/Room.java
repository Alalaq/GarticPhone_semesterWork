package ru.kpfu.itis.general.entities;

import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.general.helpers.general.DrawingCode;
import ru.kpfu.itis.protocol.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Room {
    //Todo make some logic of starting room
    protected boolean ifStarted;

    protected List<Player> players;
    //Логика шикарная: байты - картинка, а DrawingCode состоит из трёх частей:
    // первая - номер раунда, вторая - айди игрока
    //нарисовавшего картинку, а третья - true или false, где true означает, что это картинка была уже отправлена кому-то
    //все три части являются одной цифрой и т.к. ни раунд, ни айди не может быть двузначным числом, это работает отлично
    protected Map<DrawingCode, Byte[]> drawings;
    protected int currentRound;

    public Room() {
        ifStarted = false;
        players = new ArrayList<>();
        drawings = new HashMap<>();
        currentRound = 1;
    }

    public boolean addPlayer(Player player) {
        if (players.size() < Constants.MAXIMUM_PLAYERS) {
            players.add(player);
            return true;
        } else {
            return false;
        }
    }

    public void deletePlayer(Player player) {
        players.remove(player);
    }

    public int getNumberOfPlayers() {
        return players.size();
    }

    public boolean isAllReady() {
        if (players.size() < Constants.MIN_PLAYERS) {
            return false;
        }

        for (Player player : players) {
            if (!player.getReadiness()) {
                return false;
            }
        }

        return true;
    }

    public boolean allResults(int numberOfResults) {
        if (numberOfResults == players.size()) {
            return true;
        }
        return false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<String> getPlayersNicknames(){
        List<String> stringPlayers= new ArrayList<>();
        for (Player player : players){
            stringPlayers.add(player.nickname);
        }
        return stringPlayers;
    }
}