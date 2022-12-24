package ru.kpfu.itis.general.helpers.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.kpfu.itis.general.entities.Drawing;

import java.util.ArrayList;
import java.util.List;

public class DrawingParser {

    private static final Gson gson = new Gson();

    public DrawingParser() {
    }

    public static byte[] serializeObject(Drawing drawing) {
        return TextParser.serializeMessage(gson.toJson(drawing));
    }

    public static Drawing deserializeObject(byte[] drawing) {
        String text = TextParser.deserializeMessage(drawing);

        return gson.fromJson(text, Drawing.class);
    }

    public static byte[] serializeObjects(List<Drawing> drawing) {
        return TextParser.serializeMessage(gson.toJson(drawing));
    }

    public static List<Drawing> deserializeObjects(byte[] drawings) {
        List<Drawing> resultDrawings = new ArrayList<>();
        String text = TextParser.deserializeMessage(drawings);

        JsonArray array = gson.fromJson(text, JsonArray.class);

        for (JsonElement jsonElement : array) {
            Drawing drawing = gson.fromJson(jsonElement, Drawing.class);
            resultDrawings.add(drawing);
        }

        return resultDrawings;
    }
}
