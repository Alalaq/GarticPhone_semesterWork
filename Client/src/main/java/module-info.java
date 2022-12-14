module ru.kpfu.itis.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires ru.kpfu.itis.protocol;

    exports ru.kpfu.itis;
    exports ru.kpfu.itis.gui.controllers;

    opens ru.kpfu.itis to javafx.fxml;
    opens ru.kpfu.itis.gui.controllers to javafx.fxml;
}