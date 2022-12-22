package ru.kpfu.itis.general.entities;

import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.protocol.Constants;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Room {
    //Todo make some logic of starting room
    protected boolean ifStarted;

    protected List<Player> players;
    protected int currentRound;

    public Room() {
        ifStarted = false;
        players = new ArrayList<>();
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
        if (players.size() != Constants.MAXIMUM_PLAYERS) {
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