package ru.kpfu.itis.gui.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ru.kpfu.itis.ClientApp;

import java.io.IOException;

public class ScenesManager {
    private static FXMLLoader loader;
    private static Parent root;

    public static Scene getLobbyScene(){
        loader = new FXMLLoader(ClientApp.class.getResource("lobby.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(root);
    }
}
