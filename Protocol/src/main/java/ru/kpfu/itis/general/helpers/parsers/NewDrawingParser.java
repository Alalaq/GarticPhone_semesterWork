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

    public static Byte[] serializeObjects(List<Drawing> drawings){
        List<Byte> list = new ArrayList<>();
        list.add((byte) drawings.size());
        for (int i = 0; i < drawings.size();i++){
            list.add((byte) serializeObject(drawings.get(i)).length);
            for (Byte by: serializeObject(drawings.get(i))){
                list.add(by);
            }
        }
        return list.toArray(new Byte[0]);
    }

    public static List<Drawing> deserializeObjects(Byte[] drawings){
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

    public static Byte[] serializeObject(Drawing drawing) {
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
        return list.toArray(new Byte[0]);
    }

    public static Drawing deserializeObject(Byte[] drawing) {
        //image
        int len = drawing[0] & 0xFF;
        Byte[] image = Arrays.copyOfRange(drawing, 1, len+1);
        byte[] newImage = fromByte(image);
        //authorName
        int lenString = drawing[len+1] & 0xFF;
        Byte[] name = Arrays.copyOfRange(drawing,len+2,len+2+lenString);
        String newName = new String(fromByte(name),StandardCharsets.UTF_8);
        //id
        byte[] longBytes = fromByte(Arrays.copyOfRange(drawing,len+2+lenString,drawing.length));
        return new Drawing(newImage,newName, Longs.fromByteArray(longBytes));
    }
    private static byte[] fromByte(Byte[] arr){
        byte[] bytes = new byte[arr.length];
        for (int i =0; i < arr.length;i++){
            bytes[i] = arr[i];
        }
        return bytes;
    }

    public static void main(String[] args) {
        List<Drawing> first = new ArrayList<>();
        first.add(new Drawing(new byte[10],"122",12l));
        first.add(new Drawing(new byte[9],"112",32l));
        first.add(new Drawing(new byte[3],"125",16l));
        System.out.println(first);

        System.out.println(deserializeObjects(serializeObjects(first)));
    }
}
