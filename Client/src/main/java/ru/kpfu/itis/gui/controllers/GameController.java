package ru.kpfu.itis.gui.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.connection.GameMessageListenerService;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class GameController {
    private final Color[] colors = {Color.RED, Color.DARKORANGE, Color.YELLOW, Color.GREEN, Color.AQUA, Color.BLUE, Color.BLUEVIOLET, Color.BLACK, Color.WHITE, Color.BROWN};

    public Canvas drawCanvas;
    public Button redButton;
    public Button orangeButton;
    public Button yellowBtn;
    public Button greenBtn;
    public Button aquaButton;
    public Button blueButton;
    public Button blueVioletButton;
    public Button blackButton;
    public Button whiteButton;
    public Button brownButton;
    public Button readyButton;

    private Player player;
    private Boolean ready = false;
    private Stage stage;
    private GraphicsContext gc;
    private Connection connection;

    public void initialize() {
        gc = drawCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

        initButtons();
        initCanvasListeners();
    }

    private void initButtons() {
        Button[] buttons = {redButton, orangeButton, yellowBtn, greenBtn, aquaButton, blueButton, blueVioletButton, blackButton, whiteButton, brownButton};
        for (int i = 0; i < colors.length; i++) {
            buttons[i].setBackground(new Background(new BackgroundFill(colors[i], CornerRadii.EMPTY, Insets.EMPTY)));
            int finalI = i;
            buttons[i].setOnMouseClicked(event -> {
                        gc.setStroke(colors[finalI]);
                    }
            );
        }
    }

    private void initCanvasListeners() {
        drawCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                event -> {
                    if (!ready) {
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.stroke();
                    }
                });

        drawCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    if (!ready) {
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();
                    }
                });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        initialize();
        stage.setResizable(false);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        new GameMessageListenerService(connection, stage, drawCanvas,readyButton,this, player).start();
    }

    @FXML
    private void changeReady() {
        ready = !ready;
        readyButton.setText(ready ? "I'm not ready :(" : "I'm ready!");
        connection.sendMessage(new Message(Constants.READINESS, getDrawingFromCanvas()));
    }
    public void setReady(boolean ready){
        this.ready = ready;
    }

    private byte[] getDrawingFromCanvas() {
        try {
            WritableImage image = new WritableImage((int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());
            drawCanvas.snapshot(null, image);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000000);
            ImageIO.write(bufferedImage, "png", out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    public void setPlayer(Player player) {
        this.player = player;
    }
}
