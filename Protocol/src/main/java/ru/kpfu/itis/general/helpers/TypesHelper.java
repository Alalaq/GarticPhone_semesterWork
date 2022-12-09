package ru.kpfu.itis.general.helpers;

import lombok.Setter;
import ru.kpfu.itis.protocol.TypesOfMessages;

import java.util.List;

@Setter
public class TypesHelper {
    private static TypesOfMessages types;

    public static List<TypesOfMessages> getBytesArrayOfTypes(){
        return List.of(types.getDeclaringClass().getEnumConstants());
    }

    public static boolean ifThereSuchBytes(byte bytes){
        return getBytesArrayOfTypes().contains(bytes);
    }
}
