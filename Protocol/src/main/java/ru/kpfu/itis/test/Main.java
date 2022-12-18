package ru.kpfu.itis.test;

import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;
import ru.kpfu.itis.protocol.MessageOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        ServerSocket server = new ServerSocket(6006);
//             Socket socket = new Socket("localhost", 6006)
        try (MessageOutputStream out = new MessageOutputStream((new FileOutputStream("C:\\Users\\muzik\\Desktop\\java\\GarticPhone_semesterWork\\Protocol\\src\\main\\resources\\test.txt")));
             MessageInputStream in = new MessageInputStream(new MessageInputStream(new FileInputStream("C:\\Users\\muzik\\Desktop\\java\\GarticPhone_semesterWork\\Protocol\\src\\main\\resources\\test.txt")))) {

            Message message = new Message();

            byte[] bytes = new byte[]{0, 1, 2, 6, 6, 6};
            message.setType((byte) 2);
            message.setBody(bytes);

            out.writeMessage(message);
            out.flush();

            Message msg = in.readMessage();
            System.out.println(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
