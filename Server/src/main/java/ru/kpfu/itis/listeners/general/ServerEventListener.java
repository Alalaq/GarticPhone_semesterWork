package ru.kpfu.itis.listeners.general;

import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.Connection;
import ru.kpfu.itis.server.Server;

public interface ServerEventListener {
    public void init(Server server);
    public void handle(Connection connection, Message message);
    public byte getType();
}
