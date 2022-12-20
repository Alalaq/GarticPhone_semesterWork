package ru.kpfu.itis.server;

import lombok.Data;
import ru.kpfu.itis.exceptions.ServerAlreadyStartException;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.entities.Room;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.listeners.general.ServerEventListener;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Server {
    protected int port;
    protected boolean started;

    protected List<ServerEventListener> listeners;

    protected ServerSocket server;
    protected List<Connection> connections;

    protected Room room;

    public Server(int port) {
        this.port = port;
        started = false;
        listeners = new ArrayList<>();
        connections = new ArrayList<>();
        room = null;
    }

    public void start() throws ServerException {
        try {
            server = new ServerSocket(this.port);
            started = true;

            while (true) {
                Socket s = server.accept();
                handleConnection(s);
            }
        } catch (IOException ex) {
            throw new ServerException("Problem with server starting.", ex);
        }
    }

    protected void handleConnection(Socket socket) throws ServerException {
        try {
            Connection connection = new Connection(this, socket);
            connections.add(connection);

            if (connections.size() == 1) {
                room = createRoom();
            }

            new Thread(connection).start();
        } catch (IOException ex) {
            throw new ServerException("Problem with handling connection.", ex);
        }
    }


    public void registerListener(ServerEventListener listener) {
        if (started) {
            throw new ServerAlreadyStartException("Server has been started already. Can't register listener.");
        }

        listener.init(this);
        this.listeners.add(listener);
    }

    public void registerListener(List<ServerEventListener> listeners) {
        if (started) {
            throw new ServerAlreadyStartException("Server has been started already. Can't register listener.");
        }

        for (ServerEventListener listener : listeners) {
            listener.init(this);
        }
        this.listeners.addAll(listeners);
    }


    public void sendMessage(Connection connection, Message message) {
        try {
            connection.getOutputStream().writeMessage(message);
        } catch (IOException e) {
            removeConnection(connection);
        }
    }

    public void sendMulticastMessage(Room room, Message message) {
        List<Player> players = room.getPlayers();

        for (Connection connection : connections) {
            if (players.contains(connection.getPlayer())) {
                sendMessage(connection, message);
            }
        }
    }

    public Connection getConnectionById(Integer id) {
        for (Connection connection : connections) {
            if (connection.getId() == id) {
                return connection;
            }
        }

        return null;
    }


    public List<Connection> getAllConnections() {
        return connections;
    }


    public void removeConnection(Connection connection) {
        Iterator<Connection> iterator = connections.iterator();

        while (iterator.hasNext()) {
            Connection conn = iterator.next();

            if (connection.getId() == conn.getId()) {
                handleRemovePlayer(connection.getPlayer());

                iterator.remove();
            }
        }
    }


    protected void handleRemovePlayer(Player player) {
        if (player != null && player.inRoom()) {
            Room room = player.getRoom();
            player.exitRoom();

            Message message = new Message(Constants.EXIT_ROOM,
                    PlayerParser.serializeObject(room.getPlayers()));

            sendMulticastMessage(room, message);
        }
    }


    public static Room createRoom() {
        return new Room();
    }
}
