package ru.kpfu.itis.exceptions;

public class IllegalProtocolVersionException extends RuntimeException {
    public IllegalProtocolVersionException() {
        super();
    }

    public IllegalProtocolVersionException(String message) {
        super(message);
    }

    public IllegalProtocolVersionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalProtocolVersionException(Throwable cause) {
        super(cause);
    }

    protected IllegalProtocolVersionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
