package ru.kpfu.itis.general.helpers.parsers;

import com.google.common.primitives.Longs;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NewDrawingParser {

    public NewDrawingParser() {
    }

    public static byte[] serializeObjects(List<Drawing> drawings){
        List<Byte> list = new ArrayList<>();
        list.add((byte) drawings.size());
        for (int i = 0; i < drawings.size();i++){
            list.add((byte) serializeObject(drawings.get(i)).length);
            for (Byte by: serializeObject(drawings.get(i))){
                list.add(by);
            }
        }
        byte[] bytes = new byte[list.size()];
        for (int i =0; i < list.size();i++){
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static List<Drawing> deserializeObjects(byte[] drawings){
        int count = drawings[0] & 0xFF;
        List<Drawing> drawingsList = new ArrayList<>(count);
        int beginIndex = 1;
        for (int i = 0; i < count; i++){
            int len = drawings[beginIndex] & 0xFF;
            beginIndex++;
            drawingsList.add(deserializeObject(Arrays.copyOfRange(drawings,beginIndex,beginIndex+len)));
            beginIndex += len;
        }
        return drawingsList;
    }

    public static byte[] serializeObject(Drawing drawing) {
        List<Byte> list = new ArrayList<>();
        //image
        list.add((byte) drawing.getImage().length);
        for (Byte bit : drawing.getImage()) {
            list.add(bit);
        }
        //authorName
        byte[] nameAsBytes = drawing.getAuthorName().getBytes(StandardCharsets.UTF_8);
        list.add((byte) nameAsBytes.length);
        for (Byte bit : nameAsBytes) {
            list.add(bit);
        }
        //id
        byte[] id = Longs.toByteArray(drawing.getAuthorId());
                for (Byte bit : id) {
            list.add(bit);
        }
        byte[] bytes = new byte[list.size()];
        for (int i =0; i < list.size();i++){
            bytes[i] = list.get(i);
        }
        return bytes;
    }

    public static Drawing deserializeObject(byte[] drawing) {
        //image
        int len = drawing[0] & 0xFF;
        byte[] newImage = Arrays.copyOfRange(drawing, 1, len+1);
        //authorName
        int lenString = drawing[len+1] & 0xFF;
        byte[] name = Arrays.copyOfRange(drawing,len+2,len+2+lenString);
        String newName = new String(name,StandardCharsets.UTF_8);
        //id
        byte[] longBytes = Arrays.copyOfRange(drawing,len+2+lenString,drawing.length);
        return new Drawing(newImage,newName, Longs.fromByteArray(longBytes));
    }

    public static void main(String[] args) {
    }
}
