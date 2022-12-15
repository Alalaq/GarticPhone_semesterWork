package ru.kpfu.itis.general.helpers;

import lombok.Setter;
import ru.kpfu.itis.protocol.TypesOfMessages;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class TypesHelper {
    private static TypesOfMessages types;


    //todo solve the problem with bytes array properly
    public static List<Byte> getBytesArrayOfTypes(){
        return (Arrays.stream(types.getDeclaringClass().getEnumConstants()).map(type -> (byte) type.toString().getBytes().length).collect(Collectors.toList()));
    }

    public static boolean ifThereSuchBytes(byte bytes){
        return getBytesArrayOfTypes().contains(bytes);
    }
}
