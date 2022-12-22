package ru.kpfu.itis.connection;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.helpers.parsers.DrawingParser;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.protocol.MessageInputStream;

import java.net.Socket;

public class GameMessageListenerService extends Service<Void> {

    private Socket socket;
    private MessageInputStream in;
    private Stage stage;
    private Canvas drawCanvas;
    private GraphicsContext gc;

    public GameMessageListenerService(Connection connection, Stage stage, Canvas drawCanvas) {
        this.socket =connection.getSocket();
        this.in = connection.getInputStream();
        this.stage = stage;
        this.drawCanvas = drawCanvas;
        gc = drawCanvas.getGraphicsContext2D();
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (socket.isConnected()){
                    Message message = in.readMessage();
                    switch (message.getType()){
                        case Constants.NEXT_ROUND -> Platform.runLater(()->{
                            clearCanvas();
                            drawNewImage(DrawingParser.deserializeObject(message.getBody()));
                        });
                        case Constants.GAME_ENDED -> {
                            System.out.println("END"); //TODO : show end
                        }
                    }
                }
                return null;
            }
        };
    }

    private void drawNewImage(Drawing drawing) {
        WritableImage writableImage = new WritableImage((int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());

        writableImage.getPixelWriter().setPixels(0,
                0,
                (int) drawCanvas.getWidth(),
                (int) drawCanvas.getHeight(),
                PixelFormat.getByteBgraInstance(),
                drawing.getImage(),
                0,
                (int) drawCanvas.getWidth() * 4);
        gc.drawImage(writableImage, 0, 0, (int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());
    }

    private void clearCanvas() {
        gc = drawCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,drawCanvas.getWidth(), drawCanvas.getHeight());
    }
}
