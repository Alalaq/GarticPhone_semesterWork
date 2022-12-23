package ru.kpfu.itis.general.helpers.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.kpfu.itis.general.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerParser {
    private static final Gson gson = new Gson();

    public PlayerParser() {
    }

    public static byte[] serializeObject(Player player) {
        return TextParser.serializeMessage(gson.toJson(player));
    }

    public static byte[] serializeObject(List<Player> players) {
        return TextParser.serializeMessage(gson.toJson(players));
    }

    public static Player deserializeObject(byte[] player) {
        String text = TextParser.deserializeMessage(player);

        return gson.fromJson(text, Player.class);
    }

    public static List<Player> deserializeObjects(byte[] players) {
        List<Player> resultPlayers = new ArrayList<>();
        String text = TextParser.deserializeMessage(players);

        JsonArray array = gson.fromJson(text, JsonArray.class);

        for (JsonElement jsonElement : array) {
            Player player = gson.fromJson(jsonElement, Player.class);
            resultPlayers.add(player);
        }

        return resultPlayers;
    }
}
