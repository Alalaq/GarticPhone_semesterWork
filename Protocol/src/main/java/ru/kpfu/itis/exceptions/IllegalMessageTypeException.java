package ru.kpfu.itis.exceptions;

public class IllegalMessageTypeException extends RuntimeException {
    public IllegalMessageTypeException(String message) {
        super(message);
    }

    public IllegalMessageTypeException() {
        super();
    }

    public IllegalMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMessageTypeException(Throwable cause) {
        super(cause);
    }

    protected IllegalMessageTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
