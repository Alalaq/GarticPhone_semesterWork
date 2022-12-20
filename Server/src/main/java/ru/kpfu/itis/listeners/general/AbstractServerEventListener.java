package ru.kpfu.itis.listeners.general;

import ru.kpfu.itis.listeners.EntranceListener;
import ru.kpfu.itis.listeners.ExitRoomListener;
import ru.kpfu.itis.listeners.JoinRoomListener;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

import static ru.kpfu.itis.protocol.Constants.*;

public abstract class AbstractServerEventListener implements ServerEventListener {
    protected boolean init;
    protected Server server;

    //тип сообщения, которое прослушивает листенер
    protected static byte type;

    //параметры, по которым определённому connection отправляется данное сообщение
    protected Connection connection;
    protected Message message;

    public AbstractServerEventListener(byte type) {
        AbstractServerEventListener.type = type;
    }

    public static ServerEventListener getEventListener(byte type) {
        switch (type) {
            case JOIN_ROOM:
                return new JoinRoomListener();
            case EXIT_ROOM:
                return new ExitRoomListener();
            case ENTRANCE:
               return new EntranceListener();
            default:
                throw new IllegalArgumentException("Illegal type of listener");
        }
    }

    @Override
    public void init(Server server) {
        this.init = true;
        this.server = server;
    }

    @Override
    public byte getType() {
        return type;
    }
}
