package ru.kpfu.itis.gui.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.connection.Connection;
import ru.kpfu.itis.connection.ResultMessageListenerService;
import ru.kpfu.itis.general.entities.Drawing;
import ru.kpfu.itis.general.entities.Player;
import ru.kpfu.itis.general.helpers.parsers.PlayerParser;
import ru.kpfu.itis.protocol.Constants;
import ru.kpfu.itis.protocol.Message;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    public Button startGameButton;
    public ListView<String> usersList;
    public ScrollPane scrollPane;
    public VBox vbox;
    public Connection connection;
    private Player player;
    private int userAmount;
    private List<Drawing> drawings = new ArrayList<>();
    private int count = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vbox.prefWidthProperty().bind(scrollPane.widthProperty());
        vbox.prefHeightProperty().bind(scrollPane.heightProperty());
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        new ResultMessageListenerService(this, connection).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (player.getIsAdmin()) {
            connection.sendMessage(new Message(Constants.REQUIRE_NEW_BRANCH));
        }
    }

    public void updateUsers(String[] users) {
        userAmount = users.length;
        usersList.getItems().clear();
        for (String user : users) {
            usersList.getItems().add(user.trim());
        }
    }

    public void showOneGameBranch(List<Drawing> images) {
        vbox.getChildren().clear();
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.setPrefHeight(200);
        flowPane.setPrefWidth(200);

        for (Drawing drawing : images) {
            try {
                Label label = new Label("Author:" + drawing.getAuthorName());

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(drawing.getImage()));
                image = resize(image, 300, 300);
                ImageView imageView = new ImageView(SwingFXUtils.toFXImage(image, null));

                Button button = new Button(drawing.getAuthorName());
                button.setOnMouseClicked(event -> {
                    System.out.println("voted");
                    connection.sendMessage(new Message(Constants.VOTED, PlayerParser.serializeObject(player)));
                });

                vbox.getChildren().add(label);
                vbox.getChildren().add(imageView);
                flowPane.getChildren().add(button);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        vbox.getChildren().add(flowPane);
    }

    public void showUserWinnerAlert(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations to " + name);
        alert.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        alert.close();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void dataCome(Drawing drawing) {
        drawings.add(drawing);
        count++;
        if (count % userAmount == 0){
            List<Drawing> newDrawings = new ArrayList<>();
            for (int i = count - userAmount; i < count; i++){
                newDrawings.add(drawings.get(i));
            }
            showOneGameBranch(newDrawings);
        }
    }
}
