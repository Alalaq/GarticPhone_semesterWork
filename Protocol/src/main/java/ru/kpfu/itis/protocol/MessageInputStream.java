package ru.kpfu.itis.protocol;

import ru.kpfu.itis.exceptions.IllegalMessageTypeException;
import ru.kpfu.itis.exceptions.IllegalProtocolVersionException;

import java.io.IOException;
import java.io.InputStream;

import static ru.kpfu.itis.general.helpers.TypesHelper.ifThereSuchBytes;

//InputStream for reading messages and throwing exceptions if there's some error in type of message/ver of protocol etc.

public class MessageInputStream extends InputStream {
    private final InputStream inputStream;

    public byte firstByte;
    public byte secondByte;

    public MessageInputStream(InputStream inputStream) {
        this.firstByte = (byte) Math.floor(Constants.VERSION);
        this.secondByte = (byte) (Constants.VERSION * 10 % 10);
        this.inputStream = inputStream;
    }

    // the first 2 bytes are a digit before the dot in the protocol version and a digit after,
    // to check the correctness of the message
    // third byte is a type
    // 4, 5 - body length

    public Message readMessage() throws IOException, IllegalMessageTypeException {
        int firstByte = inputStream.read();

        if (firstByte == -1) {
            return null;
        }

        byte firstInputByte = (byte) firstByte;
        byte secondInputByte = (byte) inputStream.read();

        if ((firstInputByte != firstByte) || (secondInputByte != secondByte)) {
            throw new IllegalProtocolVersionException("Error in version of protocol");
        }

        byte type = (byte) inputStream.read();

        if (!ifThereSuchBytes(type)) {
            throw new IllegalMessageTypeException("Error in type of message");
        }

        int length = inputStream.read() << 8 | inputStream.read();

        byte[] data = new byte[length];

        for (int i = 0; i < length; i++) {
            data[i] = (byte) inputStream.read();
        }

        return new Message(type, data);
    }

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
    public void mark(int readLimit) {
        inputStream.mark(readLimit);
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
