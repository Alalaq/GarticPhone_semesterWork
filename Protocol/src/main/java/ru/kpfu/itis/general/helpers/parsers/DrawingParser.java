package ru.kpfu.itis.general.helpers.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.tools.javac.util.ArrayUtils;
import ru.kpfu.itis.general.entities.Drawing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DrawingParser {

    private static final Gson gson = new Gson();


    public static byte[] serializeObject(Drawing drawing) {
        return gson.toJson(drawing).getBytes(StandardCharsets.UTF_8);
    }

    public static Drawing deserializeObject(byte[] drawing) {
        return gson.fromJson(new String(drawing, StandardCharsets.UTF_8), Drawing.class);
    }

    public static byte[] serializeObjects(List<Drawing> drawing) {
        return gson.toJson(drawing).getBytes(StandardCharsets.UTF_8);
    }

    public static List<Drawing> deserializeObjects(byte[] drawings) {
        List<Drawing> drawingsList = new ArrayList<>();
        JsonArray array = gson.fromJson(new String(drawings,StandardCharsets.UTF_8),JsonArray.class);

        for (JsonElement jsonElement : array){
            drawingsList.add(gson.fromJson(jsonElement,Drawing.class));
        }
        return drawingsList;
    }
}
