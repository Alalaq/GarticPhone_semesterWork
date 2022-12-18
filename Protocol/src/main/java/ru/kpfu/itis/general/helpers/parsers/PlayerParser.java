package ru.kpfu.itis.general.helpers.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.kpfu.itis.general.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerParser {
    private final TextParser textParser;
    private final Gson gson;

    public PlayerParser(){
        textParser = new TextParser();
        gson = new Gson();
    }

    public byte[] serializeObject(Player player) {
        return textParser.serializeMessage(gson.toJson(player));
    }

    public byte[] serializeObject(List<Player> players) {
        return textParser.serializeMessage(gson.toJson(players));
    }

    public Player deserializeObject(byte[] player) {
        String text = textParser.deserializeMessage(player);

        return gson.fromJson(text, Player.class);
    }

    public List<Player> deserializeObjects(byte[] players) {
        List<Player> resultPlayers = new ArrayList<>();
        String text = textParser.deserializeMessage(players);

        JsonArray array = gson.fromJson(text, JsonArray.class);

        for (JsonElement jsonElement: array){
            Player player = gson.fromJson(jsonElement, Player.class);
            resultPlayers.add(player);
        }

        return resultPlayers;
    }
}
