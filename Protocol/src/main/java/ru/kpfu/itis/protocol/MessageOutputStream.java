package ru.kpfu.itis.protocol;

import java.io.IOException;
import java.io.OutputStream;
import ru.kpfu.itis.general.entities.*;


//OutputStream for sending messages

public class MessageOutputStream extends OutputStream {
    private final OutputStream outputStream;
    public final byte firstByte;
    public final byte secondByte;

    public MessageOutputStream(OutputStream outputStream) {
        this.firstByte = (byte) Math.floor(Constants.VERSION);
        this.secondByte = (byte) (Constants.VERSION * 10 % 10);
        this.outputStream = outputStream;
    }

    //First and second bytes represent the version of protocol
    //Third for message type
    //4 and 5 for file's body length

    public void writeMessage(Message message) throws IOException {
        outputStream.write(firstByte);
        outputStream.write(secondByte);

        byte messageType = message.getType();
        byte[] messageBody = message.getBody();

        outputStream.write(messageType);

        int length = messageBody.length;
        outputStream.write((byte) (length >> 8));
        outputStream.write((byte) length);

        for (byte b : messageBody) {
            outputStream.write(b);
        }
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
