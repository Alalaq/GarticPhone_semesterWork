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

import java.net.URL;
import java.util.ResourceBundle;

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

    private Stage stage;
    private Color currentColor;
    private GraphicsContext gc;
    private Connection connection;

    public void initialize() {
        gc = drawCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,drawCanvas.getWidth(),drawCanvas.getHeight());


        Button[] buttons = {redButton, orangeButton, yellowBtn, greenBtn, aquaButton, blueButton, blueVioletButton, blackButton, whiteButton, brownButton};
        for (int i =0; i < colors.length;i++){
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
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke();
                });

        drawCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                event -> {
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                });

        drawCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                event -> {

                });

    }
    public void setStage(Stage stage){
        this.stage = stage;
        initialize();
        stage.setResizable(false);
    }

    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public void testDraw(ActionEvent actionEvent) {
        gc.setFill(Color.TRANSPARENT);
        gc.fillRect(0,0,drawCanvas.getWidth(),drawCanvas.getHeight());

        WritableImage image = new WritableImage((int) drawCanvas.getWidth(),(int) drawCanvas.getHeight());
        drawCanvas.snapshot(null,image);
        byte[] buffer = new byte[(int) (drawCanvas.getWidth() * drawCanvas.getHeight() * 4)];
        image.getPixelReader().getPixels(0,
                0,
                (int) drawCanvas.getWidth(),
                (int)drawCanvas.getHeight(),
                PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                (int) drawCanvas.getWidth()*4);
//        ImageIO.write(SwingFXUtils.fromFXImage(image,null),"png",new File(""));
//        Image image1 = new Image(new ByteArrayInputStream(buffer));
//        System.out.println(image1.getWidth());
//        gc.drawImage(image1,0,0,image1.getWidth(),image1.getHeight());

        WritableImage writableImage = new WritableImage((int) drawCanvas.getWidth(),(int) drawCanvas.getHeight());

        writableImage.getPixelWriter().setPixels(0,
                0,
                (int) drawCanvas.getWidth(),
                (int) drawCanvas.getHeight(),
                PixelFormat.getByteBgraInstance(),
                buffer,
                0,
                (int)drawCanvas.getWidth()*4);
        System.out.println(writableImage.getWidth());
        gc.drawImage(writableImage,0,0, (int) drawCanvas.getWidth(), (int) drawCanvas.getHeight());
    }
}
