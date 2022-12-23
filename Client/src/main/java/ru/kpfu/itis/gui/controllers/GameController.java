package ru.kpfu.itis.gui.controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.DrawingParser;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;

import java.math.BigInteger;
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
    private Color currentColor;
    private GraphicsContext gc;
    private Connection connection;

    public void initialize() {
        gc = drawCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());


        Button[] buttons = {redButton, orangeButton, yellowBtn, greenBtn, aquaButton, blueButton, blueVioletButton, blackButton, whiteButton, brownButton};
        for (int i = 0; i < colors.length; i++) {
            buttons[i].setBackground(new Background(new BackgroundFill(colors[i], CornerRadii.EMPTY, Insets.EMPTY)));
            int finalI = i;
            buttons[i].setOnMouseClicked(event -> {
                currentColor = colors[finalI];
                System.out.println(currentColor);
                gc.setStroke(colors[finalI]);
            });
        }

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

        drawCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                event -> {

                });

    }

    public void setStage(Stage stage) {
        this.stage = stage;
        initialize();
        stage.setResizable(false);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void changeReady() {
        ready = !ready;
        readyButton.setText(ready ? "I'm not ready :(" : "I'm ready!");
        if (!ready) {
            connection.sendMessage(new Message(Constants.READINESS));
        } else {
            connection.sendMessage(new Message(Constants.START_SENDING_MESSAGE));
            byte[] image = getDrawingFromCanvas().getImage();
            for (int i = 0; i < 1000; i++) {
//                connection.sendMessage(new Message(Constants.READINESS, Arrays.copyOfRange(image, i * image.length / 1000, (i + 1) * image.length / 10 - 1)));
                System.out.println(i);
                connection.sendMessage(new Message(Constants.READINESS, new byte[(int) (drawCanvas.getWidth() * drawCanvas.getHeight() * 4/1000)]));
            }
        }
    }

    public Drawing getDrawingFromCanvas(){
        WritableImage image = new WritableImage((int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());
        drawCanvas.snapshot(null, image);
        byte[] buffer = new byte[(int) (drawCanvas.getWidth() * drawCanvas.getHeight() * 4 )];
        image.getPixelReader().getPixels(0,
                0,
                (int) drawCanvas.getWidth(),
                (int) drawCanvas.getHeight(),
                PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                (int) drawCanvas.getWidth() * 4);
        return new Drawing(buffer, player.getNickname());
    }

    public void testDraw(ActionEvent actionEvent) {
        WritableImage image = new WritableImage((int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());
        drawCanvas.snapshot(null, image);
        byte[] buffer = new byte[(int) (drawCanvas.getWidth() * drawCanvas.getHeight() * 4)];
        image.getPixelReader().getPixels(0,
                0,
                (int) drawCanvas.getWidth(),
                (int) drawCanvas.getHeight(),
                PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                (int) drawCanvas.getWidth() * 4);
        WritableImage writableImage = new WritableImage((int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());

        writableImage.getPixelWriter().setPixels(0,
                0,
                (int) drawCanvas.getWidth(),
                (int) drawCanvas.getHeight(),
                PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                (int) drawCanvas.getWidth() * 4);
        System.out.println(writableImage.getWidth());
        gc.drawImage(writableImage, 0, 0, (int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
