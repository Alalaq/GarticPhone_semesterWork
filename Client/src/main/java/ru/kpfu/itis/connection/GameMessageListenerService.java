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
    private Stage stage;
    private Canvas drawCanvas;
    private GraphicsContext gc;
    private Connection connection;
    private Button button;
    private GameController gameController;
    public GameMessageListenerService(Connection connection, Stage stage, Canvas drawCanvas, Button button, GameController gameController) {
        this.socket = connection.getSocket();
        this.in = connection.getInputStream();
        this.stage = stage;
        this.drawCanvas = drawCanvas;
        gc = drawCanvas.getGraphicsContext2D();
        this.connection = connection;
        this.button = button;
        this.gameController = gameController;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected()) {
                    Message message = in.readMessage();
                    switch (message.getType()) {
                        case Constants.NEXT_ROUND -> Platform.runLater(() -> {
                            clearCanvas();
                            button.setText("I'm ready!");
                            gameController.setReady(false);
                            new Alert(Alert.AlertType.INFORMATION, "Новый раунд, Ура !!!").show();
                            drawNewImage(message.getBody());
                        });
                        case Constants.GAME_ENDED -> {
                            System.out.println("end");
                            Platform.runLater(()->{
                                stage.setScene(ScenesManager.getResultScene(connection,stage));
                            });
                        }
                    }
                }
                return null;
            }
        };
    }

    private void drawNewImage(byte[] drawing) {
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(drawing));
            gc.drawImage(SwingFXUtils.toFXImage(image,null),0,0,drawCanvas.getWidth(),drawCanvas.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearCanvas() {
        gc = drawCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
    }
}
