package ru.kpfu.itis.test;

import ru.kpfu.itis.general.helpers.parsers.TextParser;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        ServerSocket server = new ServerSocket(6006);
//             Socket socket = new Socket("localhost", 6006)
        try (MessageOutputStream out = new MessageOutputStream((new FileOutputStream("C:\\Users\\muzik\\Desktop\\java\\GarticPhone_semesterWork\\Protocol\\src\\main\\resources\\test.txt")));
             MessageInputStream in = new MessageInputStream(new MessageInputStream(new FileInputStream("C:\\Users\\muzik\\Desktop\\java\\GarticPhone_semesterWork\\Protocol\\src\\main\\resources\\test.txt")))) {

            Message message = new Message(Constants.ERROR, "testaaaaaaaaaaaaaaaa".getBytes(StandardCharsets.UTF_16));

            out.writeMessage(message);
            out.flush();

            Message msg = in.readMessage();
            System.out.println((TextParser.deserializeMessage(msg.getBody())).substring(1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
