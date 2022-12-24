package ru.kpfu.itis.exceptions;

public class CanvasImageException extends RuntimeException
{
    public CanvasImageException() {
        super();
    }

    public CanvasImageException(String message) {
        super(message);
    }

    public CanvasImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanvasImageException(Throwable cause) {
        super(cause);
    }

    protected CanvasImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
