package ru.kpfu.itis.connection;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.gui.controllers.GameController;
import ru.kpfu.itis.gui.helpers.ScenesManager;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class GameMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private GameController gameController;
    private Connection connection;
    private boolean flag = true;


    public GameMessageListenerService(Connection connection, GameController gameController) {
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.connection = connection;
        this.gameController = gameController;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected() && flag) {
                    Message message = in.readMessage();
                    switch (message.getType()) {
                        case Constants.NEXT_ROUND -> Platform.runLater(() -> {
                            gameController.newRound(message.getBody());
                        });
                        case Constants.GAME_ENDED -> {
                            flag = false;
                            Platform.runLater(()->{
                                gameController.gameEnded();
                            });
                        }
                    }
                }
                return null;
            }
        };
    }
}
