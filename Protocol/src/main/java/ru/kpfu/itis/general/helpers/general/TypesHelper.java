package ru.kpfu.itis.general.helpers.general;

import lombok.Setter;
import ru.kpfu.itis.protocol.Constants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Setter
public class TypesHelper {
    //todo solve the problem with bytes array properly
    public static List<Byte> getBytesArrayOfTypes() {
        List<Byte> array = new ArrayList<>();
        for (Field field : Constants.class.getDeclaredFields()) {
            if (field.getType().equals(byte.class)) {
                try {
                    array.add(field.getByte(field));
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        return array;
    }

    public static boolean ifThereSuchBytes(byte bytes) {
        return getBytesArrayOfTypes().contains(bytes);
    }
}
