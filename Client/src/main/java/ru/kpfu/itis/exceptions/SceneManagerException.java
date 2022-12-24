package ru.kpfu.itis.exceptions;

public class SceneManagerException extends RuntimeException {
    public SceneManagerException() {
        super();
    }

    public SceneManagerException(String message) {
        super(message);
    }

    public SceneManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SceneManagerException(Throwable cause) {
        super(cause);
    }

    protected SceneManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
