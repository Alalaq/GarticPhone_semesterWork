package ru.kpfu.itis.protocol;

import java.io.IOException;
import java.io.OutputStream;


//OutputStream for sending messages

public class MessageOutputStream extends OutputStream {
    private final OutputStream outputStream;
    public byte firstByte;
    public byte secondByte;

    public MessageOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    //TODO: implement method
    //First and second bytes represent the version of protocol
    //Third for message type
    //4 and 5 for file's body length
//    public void writeMessage(Message message) throws IOException {
//    }

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
