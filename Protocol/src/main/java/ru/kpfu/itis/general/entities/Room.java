package ru.kpfu.itis.general.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Room {
    //Todo make some logic of starting room
    public static final int MAX_PLAYERS = 8;
    public static final int MIN_PLAYERS = 3;
    protected boolean ifStarted;

    protected List<Player> players;
    protected int currentRound;

    public Room() {
        ifStarted = false;
        players = new ArrayList<>();
        currentRound = 1;
    }

    public boolean addPlayer(Player player) {
        if (players.size() < MAX_PLAYERS) {
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
        if (players.size() != MAX_PLAYERS) {
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