package ru.kpfu.itis.exceptions;

public class ServerAlreadyStartException extends RuntimeException {
    public ServerAlreadyStartException(String s) {
    }

    public ServerAlreadyStartException() {
        super();
    }

    public ServerAlreadyStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerAlreadyStartException(Throwable cause) {
        super(cause);
    }

    protected ServerAlreadyStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
