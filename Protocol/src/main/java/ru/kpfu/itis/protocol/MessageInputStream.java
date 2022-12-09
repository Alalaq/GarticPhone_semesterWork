package ru.kpfu.itis.protocol;

import ru.kpfu.itis.exceptions.IllegalProtocolVersionException;

import java.io.IOException;
import java.io.InputStream;

//InputStream for reading messages and throwing exceptions if there's some error in type of message/ver of protocol etc.

public class MessageInputStream extends InputStream {
    private final InputStream inputStream;

    public MessageInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    // the first 2 bytes are a digit before the dot in the protocol version and a digit after,
    // to check the correctness of the message
    // third byte is a type
    // 4, 5 - body length
    //TODO: implement method
//    public Message readMessage() {
//
//    }

    @Override
    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }


    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
        inputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    public int read() throws IOException {
        return inputStream.read();
    }
}
